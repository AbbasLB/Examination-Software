use [EXAMINATION]
go
CREATE PROCEDURE SelectExam @eid int
AS
SELECT * FROM Exams WHERE eid = @eid;
