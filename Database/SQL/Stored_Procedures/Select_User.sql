use [EXAMINATION]
go
CREATE PROCEDURE SelectUser @uid int
AS
SELECT * FROM Users WHERE UserId = @uid;
