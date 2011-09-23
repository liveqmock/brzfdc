@ECHO OFF

SETLOCAL

    set BACKUP_DIR=d:\brzfdc\

    set BACKUP_DIR_TMP=%BACKUP_DIR%\backup_db

    del %BACKUP_DIR_TMP%\*.dmp

    echo.
    echo ********** ��ʼ���� **********
    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2%

    exp brzfdc/brzfdc@orcl file=%BACKUP_DIR_TMP%\brzfdc_db_%date:~0,4%%date:~5,2%%date:~8,2%.dmp log=%BACKUP_DIR_TMP%\db_backup.log direct=y buffer=4096000

    if ERRORLEVEL 1 goto errmsg

    echo.
    echo ********** �������!**********
    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2%
    echo.
    echo ********** ��ʼ�ϴ�!**********
    echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2%
    echo.
    goto end

:errmsg
   echo.
   echo.
   echo ********** ���ݴ���!**********
   echo  ����:%date:~0,4%-%date:~5,2%-%date:~8,2% ʱ��:%time:~0,2%:%time:~3,2%:%time:~6,2%
   echo.
   echo.

:end
ENDLOCAL