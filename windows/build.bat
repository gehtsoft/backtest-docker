@echo off
SETLOCAL

SET "THIS_DIR=%~dp0"
CD "%THIS_DIR%"

@REM Download all required components and prepare docker build context
call prepare.bat || exit /b 1

@REM Build docker image
docker rmi indicore-backtest-windows:latest --force
docker build -t indicore-backtest-windows:latest -f %THIS_DIR%\Dockerfile %THIS_DIR%\image || exit /b 1

exit /b 0
