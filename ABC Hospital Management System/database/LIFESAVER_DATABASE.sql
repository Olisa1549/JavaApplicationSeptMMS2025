/* LifeSaver Hospital Management System - standalone database
   This is separate from the legacy ABC database. Run in SSMS on DESKTOP-SIE694J\MEME. */
USE master;
GO
IF DB_ID(N'LifeSaverHospitalDatabase') IS NULL CREATE DATABASE LifeSaverHospitalDatabase;
GO
USE LifeSaverHospitalDatabase;
GO

IF OBJECT_ID(N'dbo.Person', N'U') IS NULL
CREATE TABLE dbo.Person (
 PersonId INT IDENTITY PRIMARY KEY, FirstName NVARCHAR(100) NOT NULL, LastName NVARCHAR(100) NOT NULL,
 Gender NVARCHAR(20) NULL, DateOfBirth DATE NULL, Phone NVARCHAR(40) NULL, Email NVARCHAR(255) NULL,
 Street NVARCHAR(200) NULL, City NVARCHAR(100) NULL, Country NVARCHAR(100) NULL
);
GO
/* Upgrade the earlier partial bootstrap if it already exists. */
IF COL_LENGTH(N'dbo.Person', N'Street') IS NULL ALTER TABLE dbo.Person ADD Street NVARCHAR(200) NULL;
IF COL_LENGTH(N'dbo.Person', N'City') IS NULL ALTER TABLE dbo.Person ADD City NVARCHAR(100) NULL;
IF COL_LENGTH(N'dbo.Person', N'Country') IS NULL ALTER TABLE dbo.Person ADD Country NVARCHAR(100) NULL;
GO
IF OBJECT_ID(N'dbo.Users', N'U') IS NULL
CREATE TABLE dbo.Users (
 UserId INT IDENTITY PRIMARY KEY, Username NVARCHAR(100) NOT NULL UNIQUE, PasswordHash NVARCHAR(255) NOT NULL,
 Role NVARCHAR(50) NOT NULL, StaffId INT NULL, IsActive BIT NOT NULL DEFAULT 1
);
GO
IF OBJECT_ID(N'dbo.Department', N'U') IS NULL
CREATE TABLE dbo.Department (
 DepartmentId INT IDENTITY PRIMARY KEY, Name NVARCHAR(120) NOT NULL UNIQUE, Description NVARCHAR(500) NULL
);
GO
IF OBJECT_ID(N'dbo.Staff', N'U') IS NULL
CREATE TABLE dbo.Staff (
 StaffId INT IDENTITY PRIMARY KEY, StaffRole NVARCHAR(60) NOT NULL DEFAULT N'STAFF', EmploymentDate DATE NULL, Salary DECIMAL(18,2) NULL, DepartmentId INT NULL, PersonId INT NOT NULL
);
GO
IF COL_LENGTH(N'dbo.Staff', N'StaffRole') IS NULL ALTER TABLE dbo.Staff ADD StaffRole NVARCHAR(60) NOT NULL CONSTRAINT DF_Staff_StaffRole DEFAULT N'STAFF';
IF COL_LENGTH(N'dbo.Staff', N'EmploymentDate') IS NULL ALTER TABLE dbo.Staff ADD EmploymentDate DATE NULL;
IF COL_LENGTH(N'dbo.Staff', N'Salary') IS NULL ALTER TABLE dbo.Staff ADD Salary DECIMAL(18,2) NULL;
IF COL_LENGTH(N'dbo.Staff', N'DepartmentId') IS NULL ALTER TABLE dbo.Staff ADD DepartmentId INT NULL;
IF COL_LENGTH(N'dbo.Staff', N'PersonId') IS NULL ALTER TABLE dbo.Staff ADD PersonId INT NULL;
GO
IF OBJECT_ID(N'dbo.Patient', N'U') IS NULL
CREATE TABLE dbo.Patient (
 PatientId INT IDENTITY PRIMARY KEY, BloodGroup NVARCHAR(10) NULL, Genotype NVARCHAR(10) NULL, Allergies NVARCHAR(500) NULL,
 EmergencyContact NVARCHAR(150) NULL, EmergencyPhone NVARCHAR(40) NULL, PersonId INT NOT NULL
);
GO
IF OBJECT_ID(N'dbo.Doctor', N'U') IS NULL
CREATE TABLE dbo.Doctor (
 DoctorId INT IDENTITY PRIMARY KEY, StaffId INT NOT NULL, Specialization NVARCHAR(150) NULL, LicenseNumber NVARCHAR(100) NULL
);
GO
IF OBJECT_ID(N'dbo.Appointment', N'U') IS NULL
CREATE TABLE dbo.Appointment (
 AppointmentID INT IDENTITY PRIMARY KEY, PatientID INT NOT NULL, DoctorID INT NOT NULL, AppointmentDate DATETIME2 NOT NULL,
 Reason NVARCHAR(500) NULL, Status NVARCHAR(40) NOT NULL DEFAULT 'Scheduled', Notes NVARCHAR(1000) NULL
);
GO
IF OBJECT_ID(N'dbo.Invoice', N'U') IS NULL
CREATE TABLE dbo.Invoice (
 InvoiceId INT IDENTITY PRIMARY KEY, PatientId INT NULL, TotalAmount DECIMAL(18,2) NOT NULL DEFAULT 0,
 Status NVARCHAR(40) NOT NULL DEFAULT 'Pending', InvoiceDate DATETIME2 NOT NULL DEFAULT SYSDATETIME()
);
GO
IF OBJECT_ID(N'dbo.Nurse', N'U') IS NULL CREATE TABLE dbo.Nurse (NurseId INT IDENTITY PRIMARY KEY, StaffId INT NOT NULL, NursingLicense NVARCHAR(100) NULL, Qualification NVARCHAR(200) NULL);
IF OBJECT_ID(N'dbo.Pharmacist', N'U') IS NULL CREATE TABLE dbo.Pharmacist (PharmacistId INT IDENTITY PRIMARY KEY, StaffId INT NOT NULL, Qualification NVARCHAR(200) NULL, LicenseNumber NVARCHAR(100) NULL);
IF OBJECT_ID(N'dbo.LaboratoryTechnician', N'U') IS NULL CREATE TABLE dbo.LaboratoryTechnician (LaboratoryTechnicianId INT IDENTITY PRIMARY KEY, StaffId INT NOT NULL, Qualification NVARCHAR(200) NULL, LicenseNumber NVARCHAR(100) NULL);
IF OBJECT_ID(N'dbo.Ward', N'U') IS NULL CREATE TABLE dbo.Ward (WardId INT IDENTITY PRIMARY KEY, Name NVARCHAR(120) NOT NULL, WardType NVARCHAR(100) NULL, Capacity INT NULL);
IF OBJECT_ID(N'dbo.Room', N'U') IS NULL CREATE TABLE dbo.Room (RoomId INT IDENTITY PRIMARY KEY, RoomNumber NVARCHAR(50) NOT NULL, WardId INT NULL, RoomType NVARCHAR(100) NULL, Capacity INT NULL);
IF OBJECT_ID(N'dbo.Bed', N'U') IS NULL CREATE TABLE dbo.Bed (BedId INT IDENTITY PRIMARY KEY, BedNumber NVARCHAR(50) NOT NULL, RoomId INT NULL, IsOccupied BIT NOT NULL DEFAULT 0);
IF OBJECT_ID(N'dbo.Admission', N'U') IS NULL CREATE TABLE dbo.Admission (AdmissionId INT IDENTITY PRIMARY KEY, PatientId INT NOT NULL, BedId INT NULL, AdmissionDate DATE NULL, DischargeDate DATE NULL, Reason NVARCHAR(500) NULL, Status NVARCHAR(50) NULL);
IF OBJECT_ID(N'dbo.NurseAssignment', N'U') IS NULL CREATE TABLE dbo.NurseAssignment (NurseAssignmentId INT IDENTITY PRIMARY KEY, NurseStaffId INT NULL, PatientId INT NULL, AdmissionId INT NULL, AssignmentDate DATETIME2 NULL, EndDate DATETIME2 NULL, Shift NVARCHAR(40) NULL, Status NVARCHAR(50) NULL, Notes NVARCHAR(1000) NULL);
IF OBJECT_ID(N'dbo.MedicalRecord', N'U') IS NULL CREATE TABLE dbo.MedicalRecord (MedicalRecordId INT IDENTITY PRIMARY KEY, PatientId INT NOT NULL, CreatedDate DATE NULL);
IF OBJECT_ID(N'dbo.Diagnose', N'U') IS NULL CREATE TABLE dbo.Diagnose (DiagnosisId INT IDENTITY PRIMARY KEY, PatientId INT NULL, DoctorId INT NULL, DiagnosisDate DATE NULL, Condition NVARCHAR(300) NULL, Description NVARCHAR(1000) NULL, Notes NVARCHAR(1000) NULL);
IF OBJECT_ID(N'dbo.Treatment', N'U') IS NULL CREATE TABLE dbo.Treatment (TreatmentId INT IDENTITY PRIMARY KEY, PatientId INT NULL, DoctorId INT NULL, DiagnosisId INT NULL, TreatmentDate DATE NULL, TreatmentName NVARCHAR(300) NULL, Description NVARCHAR(1000) NULL, Notes NVARCHAR(1000) NULL, Status NVARCHAR(50) NULL);
IF OBJECT_ID(N'dbo.Medication', N'U') IS NULL CREATE TABLE dbo.Medication (MedicationId INT IDENTITY PRIMARY KEY, Name NVARCHAR(200) NOT NULL, Description NVARCHAR(1000) NULL, DosageForm NVARCHAR(100) NULL, Price DECIMAL(18,2) NULL, QuantityInStock INT NULL);
IF OBJECT_ID(N'dbo.Prescription', N'U') IS NULL CREATE TABLE dbo.Prescription (PrescriptionId INT IDENTITY PRIMARY KEY, PatientId INT NULL, DoctorId INT NULL, PrescriptionDate DATE NULL);
IF OBJECT_ID(N'dbo.PrescriptionItem', N'U') IS NULL CREATE TABLE dbo.PrescriptionItem (PrescriptionItemId INT IDENTITY PRIMARY KEY, PrescriptionId INT NULL, MedicationId INT NULL, Dosage NVARCHAR(200) NULL, Frequency NVARCHAR(100) NULL, Duration INT NULL, DurationUnit NVARCHAR(50) NULL, Instructions NVARCHAR(1000) NULL);
IF OBJECT_ID(N'dbo.MedicationDispensing', N'U') IS NULL CREATE TABLE dbo.MedicationDispensing (MedicationDispensingId INT IDENTITY PRIMARY KEY, PrescriptionId INT NULL, PrescriptionItemId INT NULL, PharmacistStaffId INT NULL, PatientId INT NULL, DispensingDate DATETIME2 NULL, Quantity INT NULL, Status NVARCHAR(50) NULL, Notes NVARCHAR(1000) NULL);
IF OBJECT_ID(N'dbo.LaboratoryTest', N'U') IS NULL CREATE TABLE dbo.LaboratoryTest (LaboratoryTestId INT IDENTITY PRIMARY KEY, PatientId INT NULL, TechnicianStaffId INT NULL, TestName NVARCHAR(200) NULL, TestDate DATETIME2 NULL, Result NVARCHAR(2000) NULL, ReferenceRange NVARCHAR(300) NULL, Status NVARCHAR(50) NULL);
IF OBJECT_ID(N'dbo.InvoiceItem', N'U') IS NULL CREATE TABLE dbo.InvoiceItem (InvoiceItemId INT IDENTITY PRIMARY KEY, InvoiceId INT NULL, Description NVARCHAR(500) NULL, Quantity INT NULL, UnitPrice DECIMAL(18,2) NULL, Amount DECIMAL(18,2) NULL);
IF OBJECT_ID(N'dbo.Payment', N'U') IS NULL CREATE TABLE dbo.Payment (PaymentId INT IDENTITY PRIMARY KEY, InvoiceId INT NULL, Amount DECIMAL(18,2) NULL, PaymentDate DATE NULL, PaymentMethod NVARCHAR(80) NULL);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_Patient_PersonId' AND object_id = OBJECT_ID(N'dbo.Patient')) CREATE INDEX IX_Patient_PersonId ON dbo.Patient(PersonId);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_Appointment_Date' AND object_id = OBJECT_ID(N'dbo.Appointment')) CREATE INDEX IX_Appointment_Date ON dbo.Appointment(AppointmentDate);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = N'IX_Invoice_Status' AND object_id = OBJECT_ID(N'dbo.Invoice')) CREATE INDEX IX_Invoice_Status ON dbo.Invoice(Status);
GO

IF NOT EXISTS (SELECT 1 FROM dbo.Department WHERE Name = N'Emergency Care') INSERT dbo.Department(Name,Description) VALUES (N'Emergency Care',N'Acute and urgent care services');
IF NOT EXISTS (SELECT 1 FROM dbo.Department WHERE Name = N'Internal Medicine') INSERT dbo.Department(Name,Description) VALUES (N'Internal Medicine',N'General adult medicine');

IF NOT EXISTS (SELECT 1 FROM dbo.Person WHERE Email = N'adebayo.patient@lifesaver.local')
BEGIN
 DECLARE @PersonId INT, @PatientId INT;
 INSERT dbo.Person(FirstName,LastName,Gender,DateOfBirth,Phone,Email,Street,City,Country)
 VALUES(N'Amina',N'Adebayo',N'F','1992-04-18',N'+234 800 100 2000',N'adebayo.patient@lifesaver.local',N'12 Unity Crescent',N'Lagos',N'Nigeria');
 SET @PersonId = SCOPE_IDENTITY();
 INSERT dbo.Patient(BloodGroup,Genotype,Allergies,EmergencyContact,EmergencyPhone,PersonId)
 VALUES(N'O+',N'AA',N'None reported',N'K. Adebayo',N'+234 800 100 2001',@PersonId);
 SET @PatientId = SCOPE_IDENTITY();
 INSERT dbo.Invoice(PatientId,TotalAmount,Status) VALUES(@PatientId,45000,N'Pending');
END
GO
DECLARE @DoctorPersonId INT, @StaffId INT, @DoctorId INT, @DepartmentId INT, @PatientId INT;
SELECT @DepartmentId = DepartmentId FROM dbo.Department WHERE Name = N'Internal Medicine';
SELECT @DoctorPersonId = PersonId FROM dbo.Person WHERE Email = N'okafor.doctor@lifesaver.local';
IF @DoctorPersonId IS NULL
BEGIN
 INSERT dbo.Person(FirstName,LastName,Gender,DateOfBirth,Phone,Email,Street,City,Country)
 VALUES(N'Chinedu',N'Okafor',N'M','1981-10-02',N'+234 800 300 4000',N'okafor.doctor@lifesaver.local',N'4 Marina Road',N'Lagos',N'Nigeria');
 SET @DoctorPersonId = SCOPE_IDENTITY();
END
SELECT @StaffId = StaffId FROM dbo.Staff WHERE PersonId = @DoctorPersonId;
IF @StaffId IS NULL
BEGIN
 INSERT dbo.Staff(StaffRole,EmploymentDate,Salary,DepartmentId,PersonId) VALUES(N'DOCTOR','2018-01-15',850000,@DepartmentId,@DoctorPersonId);
 SET @StaffId = SCOPE_IDENTITY();
END
SELECT @DoctorId = DoctorId FROM dbo.Doctor WHERE StaffId = @StaffId;
IF @DoctorId IS NULL
BEGIN
 INSERT dbo.Doctor(StaffId,Specialization,LicenseNumber) VALUES(@StaffId,N'Internal Medicine',N'LMC-20481');
 SET @DoctorId = SCOPE_IDENTITY();
END
SELECT TOP 1 @PatientId = PatientId FROM dbo.Patient ORDER BY PatientId;
IF @PatientId IS NOT NULL AND NOT EXISTS (SELECT 1 FROM dbo.Appointment WHERE PatientID = @PatientId AND DoctorID = @DoctorId)
 INSERT dbo.Appointment(PatientID,DoctorID,AppointmentDate,Reason,Status,Notes)
 VALUES(@PatientId,@DoctorId,DATEADD(minute,570,CAST(DATEADD(day,1,CONVERT(date,SYSDATETIME())) AS datetime2)),N'Routine review',N'Scheduled',N'Initial LifeSaver demo appointment');
GO
/* Application account. SQL connection login is provisioned separately. */
IF NOT EXISTS (SELECT 1 FROM dbo.Users WHERE Username = N'admin')
 INSERT dbo.Users(Username,PasswordHash,Role,StaffId,IsActive) VALUES(N'admin',N'LifeSaver@123',N'ADMIN',NULL,1);
ELSE
 UPDATE dbo.Users SET PasswordHash=N'LifeSaver@123',Role=N'ADMIN',IsActive=1 WHERE Username=N'admin';
GO
SELECT 'Patients' AS Entity, COUNT(*) AS Total FROM dbo.Patient
UNION ALL SELECT 'Doctors', COUNT(*) FROM dbo.Doctor
UNION ALL SELECT 'Appointments', COUNT(*) FROM dbo.Appointment;
GO
