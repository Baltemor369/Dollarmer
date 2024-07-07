@echo off

echo compilation
javac -d bin App.java src/*.java widgets/*.java
echo launch app
java -cp bin App