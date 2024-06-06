@echo off
SET branchName=main
SET message=update
SET remote=origin


IF "%~1"=="" (
    GOTO error
) ELSE (
    :parse
    IF "%~1"=="" GOTO endparse
    IF "%~1"=="-o" (
        SET remote=%~2
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
IF "%remote%"=="" GOTO error
git add *
git commit -m %message%
git push -u %remote% %branchName%
echo push succeed
