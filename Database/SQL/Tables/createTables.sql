/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2017                    */
/* Created on:     1/13/2021 7:14:04 PM                         */
/*==============================================================*/

use [EXAMINATION]
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('COURSES') and o.name = 'FK_COURSES_TEACHES_TEACHERS')
alter table COURSES
   drop constraint FK_COURSES_TEACHES_TEACHERS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ENROLLMENT') and o.name = 'FK_ENROLLME_ENROLLMEN_STUDENTS')
alter table ENROLLMENT
   drop constraint FK_ENROLLME_ENROLLMEN_STUDENTS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('ENROLLMENT') and o.name = 'FK_ENROLLME_ENROLLMEN_COURSES')
alter table ENROLLMENT
   drop constraint FK_ENROLLME_ENROLLMEN_COURSES
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('EXAMS') and o.name = 'FK_EXAMS_HAS_COURSES')
alter table EXAMS
   drop constraint FK_EXAMS_HAS_COURSES
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GRADES') and o.name = 'FK_GRADES_GRADES_STUDENTS')
alter table GRADES
   drop constraint FK_GRADES_GRADES_STUDENTS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('GRADES') and o.name = 'FK_GRADES_GRADES2_EXAMS')
alter table GRADES
   drop constraint FK_GRADES_GRADES2_EXAMS
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('QUESTIONS') and o.name = 'FK_QUESTION_CONTAINS_EXAMS')
alter table QUESTIONS
   drop constraint FK_QUESTION_CONTAINS_EXAMS
go

if exists (select 1
            from  sysobjects
           where  id = object_id('COURSES')
            and   type = 'U')
   drop table COURSES
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ENROLLMENT')
            and   type = 'U')
   drop table ENROLLMENT
go

if exists (select 1
            from  sysobjects
           where  id = object_id('EXAMS')
            and   type = 'U')
   drop table EXAMS
go

if exists (select 1
            from  sysobjects
           where  id = object_id('GRADES')
            and   type = 'U')
   drop table GRADES
go

if exists (select 1
            from  sysobjects
           where  id = object_id('QUESTIONS')
            and   type = 'U')
   drop table QUESTIONS
go

if exists (select 1
            from  sysobjects
           where  id = object_id('STUDENTS')
            and   type = 'U')
   drop table STUDENTS
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TEACHERS')
            and   type = 'U')
   drop table TEACHERS
go

/*==============================================================*/
/* Table: COURSES                                               */
/*==============================================================*/
create table COURSES (
   CID                  int                  identity,
   TID                  int                  not null,
   CNAME                varchar(10)          not null,
   CREDITS              int                  not null,
   DESCRIPTION          varchar(50)          not null,
   constraint PK_COURSES primary key (CID),
   constraint AK_IDENTIFIER_2_COURSES unique (CNAME)
)
go

/*==============================================================*/
/* Table: ENROLLMENT                                            */
/*==============================================================*/
create table ENROLLMENT (
   SID                  int                  not null,
   CID                  int                  not null,
   constraint PK_ENROLLMENT primary key (SID, CID)
)
go

/*==============================================================*/
/* Table: EXAMS                                                 */
/*==============================================================*/
create table EXAMS (
   EID                  int                  identity,
   CID                  int                  not null,
   TITLE                varchar(50)          not null,
   constraint PK_EXAMS primary key (EID)
)
go

/*==============================================================*/
/* Table: GRADES                                                */
/*==============================================================*/
create table GRADES (
   SID                  int                  not null,
   EID                  int                  not null,
   GRADE                float                not null,
   constraint PK_GRADES primary key (SID, EID)
)
go

/*==============================================================*/
/* Table: QUESTIONS                                             */
/*==============================================================*/
create table QUESTIONS (
   QID                  int                  identity,
   EID                  int                  not null,
   QUESTION             varchar(200)         not null,
   CHOICEA              varchar(100)         not null,
   CHOICEB              varchar(100)         not null,
   CHOICEC              varchar(100)         null,
   CHOICED              varchar(100)         null,
   CORRECT              char(1)              not null,
   constraint PK_QUESTIONS primary key (QID)
)
go

/*==============================================================*/
/* Table: STUDENTS                                              */
/*==============================================================*/
create table STUDENTS (
   SID                  int                  identity(2,2),
   SNAME                varchar(40)          not null,
   SGENDER              char(1)              not null,
   SEMAIL               varchar(40)          not null,
   SPASS                varchar(65)          not null,
   constraint PK_STUDENTS primary key (SID),
   constraint AK_IDENTIFIER_2_STUDENTS unique (SEMAIL)
)
go

/*==============================================================*/
/* Table: TEACHERS                                              */
/*==============================================================*/
create table TEACHERS (
   TID                  int                  identity(1,2),
   TNAME                varchar(40)          not null,
   TGENDER              char(1)              not null,
   TEMAIL               varchar(40)          not null,
   TPASS                varchar(65)          not null,
   ISADMIN              bit                  not null,
   constraint PK_TEACHERS primary key (TID),
   constraint AK_IDENTIFIER_2_TEACHERS unique (TEMAIL)
)
go

alter table COURSES
   add constraint FK_COURSES_TEACHES_TEACHERS foreign key (TID)
      references TEACHERS (TID)
go

alter table ENROLLMENT
   add constraint FK_ENROLLME_ENROLLMEN_STUDENTS foreign key (SID)
      references STUDENTS (SID)
go

alter table ENROLLMENT
   add constraint FK_ENROLLME_ENROLLMEN_COURSES foreign key (CID)
      references COURSES (CID)
go

alter table EXAMS
   add constraint FK_EXAMS_HAS_COURSES foreign key (CID)
      references COURSES (CID)
go

alter table GRADES
   add constraint FK_GRADES_GRADES_STUDENTS foreign key (SID)
      references STUDENTS (SID)
go

alter table GRADES
   add constraint FK_GRADES_GRADES2_EXAMS foreign key (EID)
      references EXAMS (EID)
go

alter table QUESTIONS
   add constraint FK_QUESTION_CONTAINS_EXAMS foreign key (EID)
      references EXAMS (EID)
go

