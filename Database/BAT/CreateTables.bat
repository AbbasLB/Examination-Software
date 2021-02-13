@echo off
echo creating tables...
FOR %%I in (..\SQL\Tables\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

