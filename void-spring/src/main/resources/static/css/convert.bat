@echo off
for /r %%a in (*.scss) do ( sass -s compressed %%~na.scss %%~na.css )