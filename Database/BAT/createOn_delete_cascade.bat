@echo off
echo Apply On Delete Cascade...
FOR %%I in (..\SQL\On_delete_cascade\*.sql) DO (
echo.
echo %%I 
sqlcmd %~1 -i %%I
echo.
)
echo --------------------------------------------------- 

