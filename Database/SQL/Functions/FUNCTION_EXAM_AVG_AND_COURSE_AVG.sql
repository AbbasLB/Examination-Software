use [EXAMINATION]
go

CREATE FUNCTION exam_avg(@eid int)  
RETURNS float
AS   
-- Returns the average of an exam.  
BEGIN  
    DECLARE @res float;  

	SELECT @res = AVG(dbo.GRADES.GRADE)
	FROM  dbo.GRADES 
	where dbo.GRADES.EID=@eid
     IF (@res IS NULL)   
        SET @res = 0;  
    RETURN @res;  
END; 