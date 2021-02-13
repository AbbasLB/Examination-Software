
use [EXAMINATION]
go
CREATE PROCEDURE SelectCourse @cid int
AS
SELECT * FROM Courses WHERE cid = @cid;