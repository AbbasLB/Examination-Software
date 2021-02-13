USE [EXAMINATION]
GO

ALTER TABLE [dbo].[QUESTIONS] DROP CONSTRAINT [FK_QUESTION_CONTAINS_EXAMS]
GO

ALTER TABLE [dbo].[QUESTIONS]  WITH CHECK ADD  CONSTRAINT [FK_QUESTION_CONTAINS_EXAMS] FOREIGN KEY([EID])
REFERENCES [dbo].[EXAMS] ([EID]) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[QUESTIONS] CHECK CONSTRAINT [FK_QUESTION_CONTAINS_EXAMS]
GO

