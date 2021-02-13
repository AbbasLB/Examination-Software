/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     1/13/2021 7:11:17 PM                         */
/*==============================================================*/


use [EXAMINATION]
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('COURSES')
            and   name  = 'TEACHES_FK'
            and   indid > 0
            and   indid < 255)
   drop index COURSES.TEACHES_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ENROLLMENT')
            and   name  = 'ENROLLMENT2_FK'
            and   indid > 0
            and   indid < 255)
   drop index ENROLLMENT.ENROLLMENT2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('ENROLLMENT')
            and   name  = 'ENROLLMENT_FK'
            and   indid > 0
            and   indid < 255)
   drop index ENROLLMENT.ENROLLMENT_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('EXAMS')
            and   name  = 'HAS_FK'
            and   indid > 0
            and   indid < 255)
   drop index EXAMS.HAS_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GRADES')
            and   name  = 'GRADES2_FK'
            and   indid > 0
            and   indid < 255)
   drop index GRADES.GRADES2_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('GRADES')
            and   name  = 'GRADES_FK'
            and   indid > 0
            and   indid < 255)
   drop index GRADES.GRADES_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('QUESTIONS')
            and   name  = 'CONTAINS_FK'
            and   indid > 0
            and   indid < 255)
   drop index QUESTIONS.CONTAINS_FK
go

/*==============================================================*/
/* Index: TEACHES_FK                                            */
/*==============================================================*/




create nonclustered index TEACHES_FK on COURSES (TID ASC)
go

/*==============================================================*/
/* Index: ENROLLMENT_FK                                         */
/*==============================================================*/




create nonclustered index ENROLLMENT_FK on ENROLLMENT (SID ASC)
go

/*==============================================================*/
/* Index: ENROLLMENT2_FK                                        */
/*==============================================================*/




create nonclustered index ENROLLMENT2_FK on ENROLLMENT (CID ASC)
go

/*==============================================================*/
/* Index: HAS_FK                                                */
/*==============================================================*/




create nonclustered index HAS_FK on EXAMS (CID ASC)
go

/*==============================================================*/
/* Index: GRADES_FK                                             */
/*==============================================================*/




create nonclustered index GRADES_FK on GRADES (SID ASC)
go

/*==============================================================*/
/* Index: GRADES2_FK                                            */
/*==============================================================*/




create nonclustered index GRADES2_FK on GRADES (EID ASC)
go

/*==============================================================*/
/* Index: CONTAINS_FK                                           */
/*==============================================================*/




create nonclustered index CONTAINS_FK on QUESTIONS (EID ASC)
go

