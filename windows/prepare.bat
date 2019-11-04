@echo off
SETLOCAL

set "THIS_DIR=%~dp0"
cd "%THIS_DIR%"

set JAVA_VERSION=1.8.0.191-1
set JAVA_ZIP_FILENAME=java-1.8.0-openjdk-1.8.0.191-1.b12.ojdkbuild.windows.x86_64
set JAVA_DOWNLOAD_URL=https://github.com/ojdkbuild/ojdkbuild/releases/download/%JAVA_VERSION%/%JAVA_ZIP_FILENAME%.zip

if "%BACKTEST_DOWNLOAD_URL%" == "" (
  set "BACKTEST_DOWNLOAD_URL=http://fxcodebase.com/bin/products/IndicoreSDK/3.4.0/IndicoreBacktestUtils-1.1-Win64.exe"
)

if "%BACKTEST_TOMCAT_VERSION%" == "" (
  set "BACKTEST_TOMCAT_VERSION=8.5.42"
)

if "%BACKTEST_REST_DOWNLOAD_URL%" == "" (
  set "BACKTEST_REST_DOWNLOAD_URL=https://github.com/adukhovskoy/backtest-docker/raw/master/REST/api/rest/target/ROOT.war"
)

@REM Prepare openjdk
if exist %THIS_DIR%\backtest-tmp\ rd /s /q %THIS_DIR%\backtest-tmp\
mkdir %THIS_DIR%\backtest-tmp\
powershell Invoke-WebRequest -Uri %JAVA_DOWNLOAD_URL% -OutFile %THIS_DIR%\backtest-tmp\openjdk.zip || exit /b 1
powershell Expand-Archive -Path %THIS_DIR%\backtest-tmp\openjdk.zip -DestinationPath %THIS_DIR%\backtest-tmp\ || exit /b 1
if exist %THIS_DIR%\image\java rd /s /q %THIS_DIR%\image\java
mkdir %THIS_DIR%\image\java
xcopy /e /y %THIS_DIR%\backtest-tmp\%JAVA_ZIP_FILENAME%\* %THIS_DIR%\image\java\* || exit /b 1
set "JAVA_HOME=%THIS_DIR%\image\java\"
set "PATH=%JAVA_HOME%\bin;%PATH%"

@REM Prepare tomcat
if exist %THIS_DIR%\image\apache-tomcat rd /s /q %THIS_DIR%\image\apache-tomcat
powershell Invoke-WebRequest -Uri  https://archive.apache.org/dist/tomcat/tomcat-%BACKTEST_TOMCAT_VERSION:~0,1%/v%BACKTEST_TOMCAT_VERSION%/bin/apache-tomcat-%BACKTEST_TOMCAT_VERSION%.zip -OutFile %THIS_DIR%\backtest-tmp\tomcat.zip || exit /b 1
powershell Expand-Archive -Force -Path %THIS_DIR%\backtest-tmp\tomcat.zip -DestinationPath %THIS_DIR%\image\ || exit /b 1
move /y %THIS_DIR%\image\apache-tomcat-%BACKTEST_TOMCAT_VERSION% %THIS_DIR%\image\apache-tomcat || exit /b 1
powershell -Command "(Get-Content %THIS_DIR%\image\apache-tomcat\conf\server.xml).replace('8080', '80') | Set-Content %THIS_DIR%\image\apache-tomcat\conf\server.xml" || exit /b 1
if exist %THIS_DIR%\image\apache-tomcat\webapps rd /s /q %THIS_DIR%\image\apache-tomcat\webapps
mkdir %THIS_DIR%\image\apache-tomcat\webapps

@REM Download or build REST servlet with maven and copy into tomcat 
if "%1" == "build-rest" (
  call mvn -f %THIS_DIR%\..\REST\api\rest\pom.xml install || exit /b 1
  copy /y %THIS_DIR%\..\REST\api\rest\target\ROOT.war %THIS_DIR%\image\apache-tomcat\webapps || exit /b 1
) else (
  powershell Invoke-WebRequest -Uri %BACKTEST_REST_DOWNLOAD_URL% -OutFile %THIS_DIR%\image\apache-tomcat\webapps\ROOT.war || exit /b 1
)

@REM Prepare BacktestUtils
if exist %THIS_DIR%\image\IndicoreBacktestUtils rd /s /q %THIS_DIR%\image\IndicoreBacktestUtils
powershell Invoke-WebRequest -Uri %BACKTEST_DOWNLOAD_URL% -OutFile %THIS_DIR%\backtest-tmp\IndicoreBacktestUtils.exe || exit /b 1
start /wait "" %THIS_DIR%\backtest-tmp\IndicoreBacktestUtils.exe /S /D=%THIS_DIR%image\IndicoreBacktestUtils || exit /b 1
cd %THIS_DIR%\image\IndicoreBacktestUtils
del /s /q IndicatorRunner.exe uninst.exe jsMigration.lua *.chi *.chm
rd /s /q JSIndicators\ JSStrategies\ samples\
rd /s /q %THIS_DIR%\backtest-tmp\
exit /b 0
