@echo off
echo creating indexes...
FOR %%I in (..\SQL\Indexes\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

