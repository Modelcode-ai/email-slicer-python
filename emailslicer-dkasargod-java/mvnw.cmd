@REM Maven Wrapper script for Windows
@REM Based on https://github.com/apache/maven-wrapper

@echo off
setlocal

set "MAVEN_PROJECTBASEDIR=%~dp0"
set "WRAPPER_PROPERTIES=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.properties"

if not exist "%WRAPPER_PROPERTIES%" (
    echo Error: Could not find %WRAPPER_PROPERTIES% >&2
    exit /b 1
)

for /f "tokens=1,* delims==" %%a in ('findstr "distributionUrl" "%WRAPPER_PROPERTIES%"') do set "distributionUrl=%%b"

if "%distributionUrl%"=="" (
    echo Error: Could not read distributionUrl >&2
    exit /b 1
)

set "MAVEN_USER_HOME=%USERPROFILE%\.m2"
set "WRAPPER_DIR=%MAVEN_USER_HOME%\wrapper\dists"

for %%i in ("%distributionUrl%") do set "DIST_NAME=%%~ni"
set "MAVEN_HOME=%WRAPPER_DIR%\%DIST_NAME%"
set "MAVEN_CMD=%MAVEN_HOME%\bin\mvn.cmd"

if not exist "%MAVEN_CMD%" (
    echo Downloading Maven from %distributionUrl% ...
    mkdir "%WRAPPER_DIR%" 2>nul
    powershell -Command "Invoke-WebRequest -Uri '%distributionUrl%' -OutFile '%WRAPPER_DIR%\%DIST_NAME%.zip'"
    powershell -Command "Expand-Archive -Path '%WRAPPER_DIR%\%DIST_NAME%.zip' -DestinationPath '%WRAPPER_DIR%' -Force"
    del "%WRAPPER_DIR%\%DIST_NAME%.zip"
)

"%MAVEN_CMD%" %*
