use [EXAMINATION]
go

CREATE FUNCTION std_course_grade(@sid int,@cid int)  
RETURNS float
AS   
-- Returns the grade of a course for a specific student.  
BEGIN  
    DECLARE @res float;  
	DECLARE @num_of_exams int;
	DECLARE @sum_of_grades float;


	select @num_of_exams=count(eid) from EXAMS where cid=@cid;
	IF (@num_of_exams = 0)   
	BEGIN
       SET @res = 0;  
		RETURN @res 
	END
	SELECT  @sum_of_grades=sum(grade)      
	FROM dbo.GRADES INNER JOIN dbo.EXAMS ON dbo.GRADES.EID = dbo.EXAMS.EID INNER JOIN dbo.COURSES ON dbo.EXAMS.CID = dbo.COURSES.CID
	where sid=@sid and dbo.COURSES.CID=@cid
	IF (@sum_of_grades IS NULL)   
	BEGIN
        SET @res = 0;  
	    RETURN @res 
	END
	RETURN  @sum_of_grades/@num_of_exams;
END; 