@echo off
echo creating Triggers...
FOR %%I in (..\SQL\Triggers\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

