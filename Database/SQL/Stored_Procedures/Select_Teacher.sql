use [EXAMINATION]
go
CREATE PROCEDURE SelectTeacher @tid int
AS
SELECT * FROM Teachers WHERE TID = @tid;