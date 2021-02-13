use [EXAMINATION]
go

CREATE FUNCTION tchr_num_of_credits(@tid int)  
RETURNS int
AS   
-- Returns the total number of credits of a teacher.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = SUM(dbo.COURSES.CREDITS)
	FROM  dbo.COURSES 
	where dbo.COURSES.TID=@tid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 