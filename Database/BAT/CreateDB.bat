@echo off
echo Beginning...
echo --------------------------------------------------- 
echo Creating DB...
echo.
FOR %%I in (..\SQL\DB\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 