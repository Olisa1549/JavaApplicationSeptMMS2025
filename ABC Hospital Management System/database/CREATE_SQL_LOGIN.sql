/* LifeSaver application connection bootstrap
   Run this script in SSMS while connected to the correct SQL Server instance
   as a sysadmin or securityadmin/dbcreator. */

USE master;
GO

IF NOT EXISTS (SELECT 1 FROM sys.server_principals WHERE name = N'mainhospital')
BEGIN
    CREATE LOGIN [mainhospital]
    WITH PASSWORD = N'MainHospital@12345',
         CHECK_POLICY = ON,
         CHECK_EXPIRATION = OFF;
END
ELSE
BEGIN
    ALTER LOGIN [mainhospital] ENABLE;
    ALTER LOGIN [mainhospital] WITH PASSWORD = N'MainHospital@12345';
END
GO

IF DB_ID(N'LifeSaverHospitalDatabase') IS NULL
BEGIN
    THROW 51000, 'LifeSaverHospitalDatabase does not exist. Run DATABASE_SETUP.sql first.', 1;
END
GO

USE LifeSaverHospitalDatabase;
GO

IF NOT EXISTS (SELECT 1 FROM sys.database_principals WHERE name = N'mainhospital')
BEGIN
    CREATE USER [mainhospital] FOR LOGIN [mainhospital];
END
GO

/* db_owner matches the existing application, which performs CRUD across its
   clinical tables. Restrict this role before production deployment if possible. */
ALTER ROLE [db_owner] ADD MEMBER [mainhospital];
GO

SELECT sp.name AS ServerLogin,
       sp.is_disabled AS IsDisabled,
       dp.name AS DatabaseUser,
       DB_NAME() AS DatabaseName
FROM sys.server_principals sp
LEFT JOIN sys.database_principals dp ON dp.sid = sp.sid
WHERE sp.name = N'mainhospital';
GO
