@echo off
echo Inserting data...
echo.
echo ..\DATA\INSERT_TEACHERS_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_TEACHERS_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_STUDENTS_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_STUDENTS_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_COURSES_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_COURSES_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_ENROLLMENT_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_ENROLLMENT_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_EXAMS_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_EXAMS_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_QUESTIONS_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_QUESTIONS_DATA.sql
echo.
echo.
echo ..\DATA\INSERT_GRADES_DATA.sql
sqlcmd %~1 -i ..\DATA\INSERT_GRADES_DATA.sql
echo.
echo --------------------------------------------------- 

