/* LifeSaver Hospital Management System - SQL Server bootstrap
   Run in SSMS as an administrator. Safe to re-run. */
IF DB_ID(N'LifeSaverHospitalDatabase') IS NULL
BEGIN
    CREATE DATABASE LifeSaverHospitalDatabase;
END
GO
USE LifeSaverHospitalDatabase;
GO
IF OBJECT_ID(N'dbo.Person', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Person (
        PersonId INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Person PRIMARY KEY,
        FirstName NVARCHAR(100) NOT NULL,
        LastName NVARCHAR(100) NOT NULL,
        Gender NVARCHAR(30) NULL,
        DateOfBirth DATE NULL,
        Phone NVARCHAR(40) NULL,
        Email NVARCHAR(255) NULL,
        Address NVARCHAR(500) NULL,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_Person_CreatedAt DEFAULT SYSUTCDATETIME()
    );
END
GO
IF OBJECT_ID(N'dbo.Users', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Users (
        UserId INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Users PRIMARY KEY,
        Username NVARCHAR(100) NOT NULL CONSTRAINT UQ_Users_Username UNIQUE,
        PasswordHash NVARCHAR(255) NOT NULL,
        Role NVARCHAR(50) NOT NULL,
        StaffId INT NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_Users_IsActive DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL CONSTRAINT DF_Users_CreatedAt DEFAULT SYSUTCDATETIME()
    );
END
GO
IF OBJECT_ID(N'dbo.Staff', N'U') IS NULL
BEGIN
    CREATE TABLE dbo.Staff (
        StaffId INT IDENTITY(1,1) NOT NULL CONSTRAINT PK_Staff PRIMARY KEY,
        PersonId INT NOT NULL,
        StaffRole NVARCHAR(60) NOT NULL,
        Department NVARCHAR(120) NULL,
        HireDate DATE NULL,
        IsActive BIT NOT NULL CONSTRAINT DF_Staff_IsActive DEFAULT 1,
        CONSTRAINT FK_Staff_Person FOREIGN KEY (PersonId) REFERENCES dbo.Person(PersonId)
    );
END
GO
/* Add the remaining approved clinical schema objects and seed authorized users separately. */
