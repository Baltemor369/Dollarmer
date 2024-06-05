@echo off
SET branchName=main
SET message=update


IF "%~1"=="" (
    GOTO error
) ELSE (
    :parse
    IF "%~1"=="" GOTO endparse
    IF "%~1"=="-o" (
        SET origin=%~2
        SHIFT
    )
    IF "%~1"=="-b" (
        SET branchName=%~2
        SHIFT
    )
    IF "%~1"=="-m" (
        SET message="%~2"
        SHIFT
    )
    SHIFT
    GOTO parse
)

:error
echo check your argument -o.
exit /b

:endparse
IF "%origin%"=="" GOTO error
git add *
git commit -m %message%
git push -u %origin% %branchName%
echo 'push succeed'
