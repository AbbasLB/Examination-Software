use [EXAMINATION]
go

CREATE FUNCTION std_num_of_credits(@sid int)  
RETURNS int
AS   
-- Returns the total credits of a student.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = SUM(dbo.COURSES.CREDITS)
	FROM  dbo.ENROLLMENT INNER JOIN 
	dbo.COURSES ON dbo.ENROLLMENT.CID = dbo.COURSES.CID
	where dbo.ENROLLMENT.SID=@sid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 