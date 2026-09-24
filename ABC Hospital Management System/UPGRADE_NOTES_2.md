# LifeSaver Hospital Management System — Completion Pass 2

## What was fixed
- Added a real **Settings** screen with five selectable themes.
- Theme selection is now applied immediately and persisted between application launches.
- Fixed the SQL Server schema/DAO mismatches reported by the application:
  - Admission uses `BedId` (not `BedNumber`).
  - LaboratoryTest uses `TechnicianStaffId` (not `LaboratoryTechnicianId`).
  - Department uses the actual `DepartmentId, Name, Description` schema (no `Location`).
  - Bed uses `IsOccupied` (not `Occupied`).
- Added **Add / Edit / Delete** to the All Staff directory.
- Added a reusable database CRUD manager to the operational modules. The `MANAGE RECORDS` action is available on operational tabs and supports Add/Edit/Delete/Refresh for supported tables.
- Fixed the Dashboard quick Appointment action so it opens the real Appointment screen.

## Build verification
The project was rebuilt from a clean state with Ant using Java 19 source/target compatibility.

Result: BUILD SUCCESSFUL.

## Important
Live SQL Server connectivity still has to be tested on the target Windows machine because this environment cannot connect to the user's local SQL Server instance.
