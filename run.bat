@echo off

IF EXIST bin (
    echo project compilation
    javac -d bin App.java core/*.java widgets/*.java
)
IF "%~1"=="-c" (
    echo project compilation
    javac -d bin App.java core/*.java widgets/*.java
)
echo app launch
java -cp bin App