use [EXAMINATION]
go

CREATE FUNCTION std_num_of_available_courses(@sid int)  
RETURNS int
AS   
-- Returns the total number of available courses for a student.  
BEGIN  
    DECLARE @res int;  

	SELECT @res = COUNT(t.CID)
	FROM  (select CID from Courses except select CID from StudentsCourses where SID=@sid) as t
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 