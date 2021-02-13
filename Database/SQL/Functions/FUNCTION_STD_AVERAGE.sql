USE [EXAMINATION];  
GO  

CREATE FUNCTION std_avg(@sid int)  
RETURNS float
AS   
-- Returns the total number of available courses for a student.  
BEGIN  
    DECLARE @res float;
	DECLARE @total_sum float;
	DECLARE @total_credits float;
	DECLARE @cid float;
	DECLARE @credits float;

	SET @total_sum=0;
	SET @total_credits=0;
	
	
	DECLARE student_courses CURSOR FOR  
	SELECT dbo.COURSES.CID,dbo.COURSES.CREDITS
	FROM dbo.ENROLLMENT INNER JOIN dbo.COURSES ON dbo.ENROLLMENT.CID = dbo.COURSES.CID
	where dbo.ENROLLMENT.SID=@sid

	OPEN student_courses; 
	FETCH NEXT FROM student_courses
	INTO @cid, @credits; 

	WHILE @@FETCH_STATUS = 0  
	BEGIN   
		SET @total_sum=@total_sum+(dbo.std_course_grade(@sid,@cid)*@credits);
		SET @total_credits=@total_credits+@credits;
		
		FETCH NEXT FROM student_courses
		INTO @cid, @credits;  
	END  
  
	CLOSE student_courses;  
	DEALLOCATE student_courses; 

     IF (@total_credits = 0)   
	 BEGIN
        SET @res = 0;  
		RETURN @res;  
	 END
	 return @total_sum/@total_credits;
END; 