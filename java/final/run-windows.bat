@echo off
start cmd /c "java.exe -jar final-1.0-SNAPSHOT.jar"
timeout /t 5 /nobreak >nul
start cmd /c "java.exe -jar final-1.0-SNAPSHOT.jar"
