"""Functional tests for the Python email-slicer CLI (origin baseline).

These tests pipe a single line into ``python3 emailSlicer.py`` and capture
the actual stdout / exit code so we can record the origin baseline. Note that
the Python original does NOT validate empty halves (``@x.com`` and ``x@``)
nor does it ever exit non-zero — this is the source of intentional spec
divergences in the Java target.
"""
from __future__ import annotations

import subprocess
from pathlib import Path

import pytest

REPO_ROOT = Path("/l2l/workspace/emailslicer-yhizki")
SCRIPT_PATH = REPO_ROOT / "emailSlicer.py"

PROMPT_LINE = "Please enter your Email Id:"
INVALID_LINE = "Please enter a valid Email Id."


def _run_cli(stdin_input: str) -> subprocess.CompletedProcess:
    assert SCRIPT_PATH.is_file()
    return subprocess.run(
        ["python3", str(SCRIPT_PATH)],
        input=stdin_input,
        capture_output=True,
        text=True,
        timeout=30,
        cwd=str(REPO_ROOT),
    )


@pytest.fixture(autouse=True, scope="module")
def _ensure_script_present():
    if not SCRIPT_PATH.is_file():
        pytest.fail(f"Origin script missing at {SCRIPT_PATH}")


class TestHappyPath:
    def test_canonical_email(self):
        result = _run_cli("avimax37@gmail.com\n")
        expected = (
            f"{PROMPT_LINE}\n"
            "Your username is:  avimax37\n"
            "Your domain is:  gmail.com\n"
        )
        assert result.stdout == expected
        assert result.returncode == 0


class TestWhitespaceStripping:
    def test_leading_and_trailing_whitespace_stripped(self):
        result = _run_cli("  user@example.com  \n")
        expected = (
            f"{PROMPT_LINE}\n"
            "Your username is:  user\n"
            "Your domain is:  example.com\n"
        )
        assert result.stdout == expected
        assert result.returncode == 0


class TestMultipleAtSign:
    def test_first_at_splits(self):
        result = _run_cli("a@b@c\n")
        expected = (
            f"{PROMPT_LINE}\n"
            "Your username is:  a\n"
            "Your domain is:  b@c\n"
        )
        assert result.stdout == expected
        assert result.returncode == 0


class TestMissingAtSign:
    def test_no_at_sign(self):
        """Origin always exits 0 regardless of validity."""
        result = _run_cli("not-an-email\n")
        expected = f"{PROMPT_LINE}\n{INVALID_LINE}\n"
        assert result.stdout == expected
        assert result.returncode == 0
