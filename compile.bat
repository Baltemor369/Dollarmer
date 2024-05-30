@echo off

cd src

javac -d ../bin App.java core/*.java widgets/*.java

cd ..

java -cp bin App