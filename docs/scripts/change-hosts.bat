@echo off

:: 检查管理员权限
NET SESSION >nul 2>&1
if %errorLevel% == 0 (
    echo 管理员权限已确认
) else (
    echo 此脚本需要管理员权限，请右键点击脚本并选择 "以管理员身份运行"
    pause
    exit /b
)

:: 打开 hosts 文件
set "hostsFile=%windir%\System32\drivers\etc\hosts"

:: 检查 hosts 文件是否存在
if not exist "%hostsFile%" (
    echo [XVOID]hosts文件不存在
    pause
    exit /b
)

:: 获取新的 IP 地址
set /p "newIP=请输入新的 IP 地址: "

:: 更新 hosts 文件
findstr /v /c:"%newIP%" "%hostsFile%" >"%temp%\tmp_hosts"
echo %newIP% >>"%temp%\tmp_hosts"
copy /y "%temp%\tmp_hosts" "%hostsFile%" >nul

echo hosts 文件已更新，新的 IP 地址为 %newIP%
pause