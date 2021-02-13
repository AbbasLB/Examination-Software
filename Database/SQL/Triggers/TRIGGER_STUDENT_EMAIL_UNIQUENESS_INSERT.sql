use [EXAMINATION]
go

create trigger TRIGGER_ON_INSERT_STUDENTS on STUDENTS for insert as
begin
    declare
       @numrows  int,
       @numnull  int,
       @ErrorSeverity    int,
	   @ErrorState    int,
       @errmsg   varchar(255)

    select  @numrows = @@rowcount
    if @numrows = 0
       return

    /*  Parent "STUDENTS" must exist when inserting a child in "GRADES"  */
    if update(SID)
    begin
       if (select count(*)
           from   TEACHERS t1, inserted t2
           where  t1.TEMAIL = t2.SEMAIL) > 0
          begin
             select @errmsg = 'Email exists in TEACHERS TABLE'
             goto error
          end
    end

    return

/*  Errors handling  */
error:
	select @ErrorSeverity =16 ,
		   @ErrorState= 1
			
    raiserror (@errmsg ,@ErrorSeverity,@ErrorState)
    rollback  transaction
end
go