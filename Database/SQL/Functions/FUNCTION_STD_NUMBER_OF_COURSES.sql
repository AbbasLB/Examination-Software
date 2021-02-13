use [EXAMINATION]
go

CREATE FUNCTION std_num_of_courses(@sid int)  
RETURNS int
AS   
-- Returns the total number of courses of a student.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = COUNT(dbo.ENROLLMENT.CID)
	FROM  dbo.ENROLLMENT 
	where dbo.ENROLLMENT.SID=@sid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 