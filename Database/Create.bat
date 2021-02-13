@echo off
set "SERV=-S SERVER_NAME -U YOUR_USERNAME -P YOUR_PASSWORD"

if not exist LOGS (
  mkdir LOGS 
) 

cd BAT

echo.
echo. > ..\LOGS\All.log 2>&1

call CreateDB.bat "%SERV%" > ..\LOGS\CreateDB.log 2>&1
type ..\LOGS\CreateDB.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateDB.log

call CreateTables.bat "%SERV%" > ..\LOGS\CreateTables.log 2>&1
type ..\LOGS\CreateTables.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateTables.log

call CreateIndexes.bat "%SERV%" > ..\LOGS\CreateIndexes.log 2>&1
type ..\LOGS\CreateIndexes.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateIndexes.log

call CreateTriggers.bat "%SERV%" > ..\LOGS\CreateTriggers.log 2>&1
type ..\LOGS\CreateTriggers.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateTriggers.log

call createOn_delete_cascade.bat "%SERV%" > ..\LOGS\CreateOn_delete_cascade.log 2>&1
type ..\LOGS\CreateOn_delete_cascade.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateOn_delete_cascade.log

call createViews.bat "%SERV%" > ..\LOGS\CreateViews.log 2>&1
type ..\LOGS\CreateViews.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\CreateViews.log

call createRoles_And_Users.bat "%SERV%" > ..\LOGS\createRoles_And_User.log 2>&1
type ..\LOGS\createRoles_And_User.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\createRoles_And_User.log

call createStored_Procedures.bat "%SERV%" > ..\LOGS\createStored_Procedures.log 2>&1
type ..\LOGS\createStored_Procedures.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\createStored_Procedures.log

call createFunctions.bat "%SERV%" > ..\LOGS\createFunctions.log 2>&1
type ..\LOGS\createFunctions.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\createFunctions.log

call insertData.bat "%SERV%" > ..\LOGS\insertData.log 2>&1
type ..\LOGS\insertData.log >> ..\LOGS\All.log 2>&1
type ..\LOGS\insertData.log

echo Finished...
echo end > ..\LOGS\All.log 2>&1
echo.
echo. > ..\LOGS\All.log 2>&1


pause 