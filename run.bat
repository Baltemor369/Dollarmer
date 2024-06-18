@echo off

javac -d bin App.java core/*.java widgets/*.java

java -cp bin App
