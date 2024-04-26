@echo off

cd %~dp0

javac Part2\MainMenu.java

if %errorlevel% == 0 (
    java Part2.MainMenu
) else (
    echo Compilation failed. Exiting...
)
