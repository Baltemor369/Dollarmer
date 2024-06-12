@echo off

SET name=App

IF "%~1"=="-n" (
    SET name=%~2
)

setlocal enabledelayedexpansion
set CLASSPATH=.;%CLASSPATH%
for /R %%f in (*.java) do set JAVAFILES=!JAVAFILES! %%f
javac -d bin !JAVAFILES!
java -cp bin %name%
endlocal
exit /b 0