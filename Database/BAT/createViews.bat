@echo off
echo Creating Veiws...
FOR %%I in (..\SQL\Views\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

