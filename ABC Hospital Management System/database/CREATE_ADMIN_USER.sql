/* LifeSaver administrator bootstrap
   Current Java authentication compares PasswordHash to the entered password
   directly, so PasswordHash must contain the login password as plain text until
   the authentication layer is upgraded to one-way password hashing.

   Login after running:
     Username: admin
     Password: LifeSaver@123
*/
USE LifeSaverHospitalDatabase;
GO

IF COL_LENGTH(N'dbo.Users', N'StaffId') IS NULL
BEGIN
    ALTER TABLE dbo.Users ADD StaffId INT NULL;
END
GO

IF EXISTS (SELECT 1 FROM dbo.Users WHERE Username = N'admin')
BEGIN
    UPDATE dbo.Users
       SET PasswordHash = N'LifeSaver@123',
           Role = N'ADMIN',
           StaffId = NULL,
           IsActive = 1
     WHERE Username = N'admin';
END
ELSE
BEGIN
    INSERT INTO dbo.Users (Username, PasswordHash, Role, StaffId, IsActive)
    VALUES (N'admin', N'LifeSaver@123', N'ADMIN', NULL, 1);
END
GO

SELECT UserId, Username, Role, StaffId, IsActive
FROM dbo.Users
WHERE Username = N'admin';
GO
