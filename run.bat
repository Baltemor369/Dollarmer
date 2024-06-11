@echo off

<<<<<<< HEAD
javac -d bin App.java core/*.java widgets/*.java

java -cp bin App
=======
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
>>>>>>> ebf6de02353cd0a4632a6904d269e3cd3a411e0f
