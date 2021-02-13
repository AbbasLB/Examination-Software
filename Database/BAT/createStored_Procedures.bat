@echo off
echo creating Stored Procedures...
FOR %%I in (..\SQL\Stored_Procedures\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

