use [EXAMINATION]
go

CREATE FUNCTION std_enrolled_in_course(@cid int)  
RETURNS int
AS   
-- Returns the total number of students enrolled in a course.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = COUNT(dbo.ENROLLMENT.SID)
	FROM  dbo.ENROLLMENT 
	where dbo.ENROLLMENT.CID=@cid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 