USE [EXAMINATION]
GO

ALTER TABLE [dbo].[COURSES] DROP CONSTRAINT [FK_COURSES_TEACHES_TEACHERS]
GO

ALTER TABLE [dbo].[COURSES]  WITH CHECK ADD  CONSTRAINT [FK_COURSES_TEACHES_TEACHERS] FOREIGN KEY([TID])
REFERENCES [dbo].[TEACHERS] ([TID]) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[COURSES] CHECK CONSTRAINT [FK_COURSES_TEACHES_TEACHERS]
GO


