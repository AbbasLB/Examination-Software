
use [EXAMINATION]
go
CREATE PROCEDURE SelectStudent @sid int
AS
SELECT * FROM Students WHERE SID = @sid;