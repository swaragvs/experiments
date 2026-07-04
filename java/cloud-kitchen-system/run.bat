@echo off
REM run.bat - compile Java sources in src/ to out/ and run main.Main
REM Place this file in the project root (the folder that contains `src`)

SETLOCAL ENABLEDELAYEDEXPANSION

REM Create output directory if missing
if not exist out mkdir out

REM Temporary sources file
REM Build a space-separated, quoted list of source files (handles spaces in paths)
del /q sources.txt 2>nul >nul
set "FILES="
for /r "%~dp0src" %%f in (*.java) do (
  REM append quoted filename to FILES using delayed expansion
  set "FILES=!FILES! ""%%~f"""
)

REM Compile using javac with the expanded list
echo Compiling Java sources...
javac -d out %FILES%
if errorlevel 1 (
  echo.
  echo Compilation failed. See errors above.
  del sources.txt >nul 2>nul
  pause
  exit /b 1
)

del sources.txt >nul 2>nul

echo Compilation successful.

echo Launching application: main.Main
java -cp out main.Main

ENDLOCAL
pause
