# LifeSaver database dependency audit

The audit searched all DAO SQL statements for `FROM`, `JOIN`, `INSERT INTO`, `UPDATE`, and `DELETE FROM` references. The standalone script now accounts for the following application tables:

| Domain | Tables | Status |
|---|---|---|
| Core | Person, Users, Department, Staff | Included |
| Patients | Patient | Included |
| Medical staff | Doctor, Nurse, Pharmacist, LaboratoryTechnician | Included |
| Clinical operations | Appointment, NurseAssignment, Admission, Bed, Room, Ward, MedicalRecord, Diagnose, Treatment | Included |
| Pharmacy | Medication, Prescription, PrescriptionItem, MedicationDispensing | Included |
| Laboratory | LaboratoryTest | Included |
| Finance | Invoice, InvoiceItem, Payment | Included |

The Java authorization model remains code-based: `ADMIN` receives all enum permissions, while other roles receive their defined sets in `Authorization`. No unsupported database permission tables were invented.

The script intentionally uses nullable relationship columns for the extension tables because the supplied DAOs do not expose one authoritative schema migration or consistent foreign-key naming across every module. Before production deployment, review and tighten foreign keys, deletion behavior, and least-privilege SQL permissions against the organization’s approved clinical data model.
