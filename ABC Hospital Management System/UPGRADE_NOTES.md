# LifeSaver Hospital Management System — Build Upgrade

This archive contains a compiled Java 19 Swing build of the LifeSaver Hospital Management System.

## Included in this upgrade

- Unified **All Staff** live directory backed by SQL Server.
- New **Hospital Operations Center** exposing the existing backend domains through the GUI:
  - Medical Records
  - Admissions
  - Laboratory
  - Pharmacy / Prescriptions
  - Billing / Invoices
  - Payments
  - Medication Inventory
  - Departments
  - Beds / Ward infrastructure
- Live overview cards for the operational data domains.
- Database-backed operational refresh.
- CSV operations snapshot export.
- Dashboard navigation wired to Appointments and the new Operations Center instead of placeholder screens.
- Existing Patient, Doctor, Nurse, Pharmacist, Laboratory Technician, Nurse Assignment and Staff Account screens preserved.
- Project compiled successfully with Java source/target 19.

## Database

The project continues to use the existing `properties.properties` configuration and SQL Server/JDBC DAO layer. Run the supplied LifeSaver database setup scripts on the target SQL Server before using database-backed screens.

## Build verification

The project was rebuilt with Ant using Java source/target 19 and completed with `BUILD SUCCESSFUL`.
