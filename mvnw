#!/bin/sh
# Maven wrapper script - automatically uses Maven from PATH or fallback location
if command -v mvn >/dev/null 2>&1; then
    exec mvn "$@"
elif [ -x /tmp/apache-maven-3.9.5/bin/mvn ]; then
    exec /tmp/apache-maven-3.9.5/bin/mvn "$@"
else
    echo "Error: Maven not found. Please install Maven or set up maven wrapper." >&2
    exit 1
fi
