use [EXAMINATION]
go

CREATE FUNCTION tchr_num_of_courses(@tid int)  
RETURNS int
AS   
-- Returns the total number of courses of a student.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = COUNT(dbo.COURSES.CID)
	FROM  dbo.COURSES 
	where dbo.COURSES.TID=@tid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 