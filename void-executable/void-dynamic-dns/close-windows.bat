@echo off
setlocal

set PROCESS_NAME=void-ddns-fat-jar-with-dependencies.jar

for /f "tokens=1" %%i in ('jps -l ^| find "%PROCESS_NAME%"') do (
    set PID=%%i
)

if defined PID (
    taskkill /F /PID %PID%
    echo PROCESS %PROCESS_NAME% CLOSED。
) else (
    echo NOT FOUND PROCESS %PROCESS_NAME%。
)