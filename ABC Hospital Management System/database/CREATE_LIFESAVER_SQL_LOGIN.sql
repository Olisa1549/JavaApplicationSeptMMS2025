/* LifeSaver-specific SQL Server connection login
   This does not modify or remove the existing mainhospital login.
   Run in SSMS on DESKTOP-SIE694J\MEME as a sysadmin. */

USE master;
GO

IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = N'lifesaver_app')
BEGIN
    CREATE LOGIN [lifesaver_app]
    WITH PASSWORD = N'LifeSaverDb@12345',
         CHECK_POLICY = OFF,
         CHECK_EXPIRATION = OFF;
END
ELSE
BEGIN
    ALTER LOGIN [lifesaver_app] ENABLE;
    ALTER LOGIN [lifesaver_app] WITH PASSWORD = N'LifeSaverDb@12345';
END
GO

IF DB_ID(N'LifeSaverHospitalDatabase') IS NULL
BEGIN
    THROW 51000, 'LifeSaverHospitalDatabase does not exist. Run DATABASE_SETUP.sql first.', 1;
END
GO

USE LifeSaverHospitalDatabase;
GO

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = N'lifesaver_app')
BEGIN
    CREATE USER [lifesaver_app] FOR LOGIN [lifesaver_app];
END
GO

/* The existing application performs CRUD across many clinical tables. */
ALTER ROLE [db_owner] ADD MEMBER [lifesaver_app];
GO

SELECT sp.name AS ServerLogin,
       sp.is_disabled AS IsDisabled,
       dp.name AS DatabaseUser,
       DB_NAME() AS DatabaseName
FROM sys.server_principals sp
LEFT JOIN sys.database_principals dp ON dp.sid = sp.sid
WHERE sp.name = N'lifesaver_app';
GO
