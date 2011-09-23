@ECHO OFF

SETLOCAL

    set BACKUP_DIR=d:\brzfdc\

    set BACKUP_DIR_TMP=%BACKUP_DIR%\backup_db

    del %BACKUP_DIR_TMP%\*.dmp

    echo.
    echo ********** 开始备份 **********
    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2%

    exp brzfdc/brzfdc@orcl file=%BACKUP_DIR_TMP%\brzfdc_db_%date:~0,4%%date:~5,2%%date:~8,2%.dmp log=%BACKUP_DIR_TMP%\db_backup.log direct=y buffer=4096000

    if ERRORLEVEL 1 goto errmsg

    echo.
    echo ********** 备份完成!**********
    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2%
    echo.
    echo ********** 开始上传!**********
    echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2%
    echo.
    goto end

:errmsg
   echo.
   echo.
   echo ********** 备份错误!**********
   echo  日期:%date:~0,4%-%date:~5,2%-%date:~8,2% 时间:%time:~0,2%:%time:~3,2%:%time:~6,2%
   echo.
   echo.

:end
ENDLOCAL