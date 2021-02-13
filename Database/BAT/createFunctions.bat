@echo off
echo creating functions...
FOR %%I in (..\SQL\Functions\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

