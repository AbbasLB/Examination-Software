USE [EXAMINATION]
GO

Create View Users as
SELECT        TEMAIL as Email,TID as UserID,'T' as UserType,TNAME as Username, TGENDER as Gender , TPASS as Password, ISADMIN as IsAdmin
FROM            dbo.TEACHERS
union
SELECT        SEMAIL as Email,SID as UserID,'S' as UserType,SNAME as Username, SGENDER as Gender , SPASS as Password,0 as IsAdmin
FROM            dbo.STUDENTS

