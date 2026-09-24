package hospital.dao;

import hospital.database.DatabaseConnection;
import hospital.models.Appointment;
import hospital.models.Doctor;
import hospital.models.Patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    // =========================================================
    // CREATE APPOINTMENT
    // =========================================================

    public boolean addAppointment(Appointment appointment) {

        /*
         * The user selects a Doctor using StaffId.
         *
         * Appointment.DoctorID, however, references
         * Doctor.DoctorId.
         *
         * Therefore we first convert:
         *
         * StaffId -> DoctorId
         */

        String sql = """
                INSERT INTO Appointment
                (
                    PatientID,
                    DoctorID,
                    AppointmentDate,
                    Reason,
                    Status,
                    Notes
                )
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String doctorIdSql = """
                SELECT DoctorId
                FROM Doctor
                WHERE StaffId = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection()) {

            int doctorId;

            // =================================================
            // FIND DOCTOR'S ACTUAL DoctorId
            // =================================================

            try (PreparedStatement doctorStmt =
                         conn.prepareStatement(doctorIdSql)) {

                doctorStmt.setInt(
                        1,
                        appointment.getDoctor().getStaffID()
                );

                try (ResultSet rs =
                             doctorStmt.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println(
                                "Doctor not found."
                        );

                        return false;
                    }

                    doctorId =
                            rs.getInt("DoctorId");
                }
            }

            // =================================================
            // INSERT APPOINTMENT
            // =================================================

            try (PreparedStatement stmt =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                // Patient ID
                stmt.setInt(
                        1,
                        appointment.getPatient().getPatientID()
                );

                // ACTUAL DoctorId
                stmt.setInt(
                        2,
                        doctorId
                );

                // Appointment date
                stmt.setObject(
                        3,
                        appointment.getAppointmentDate()
                );

                // Reason
                stmt.setString(
                        4,
                        appointment.getReason()
                );

                // Status
                stmt.setString(
                        5,
                        appointment.getStatus()
                );

                // Notes
                stmt.setString(
                        6,
                        appointment.getNotes()
                );

                int rowsAffected =
                        stmt.executeUpdate();

                // =================================================
                // GET GENERATED APPOINTMENT ID
                // =================================================

                if (rowsAffected > 0) {

                    try (ResultSet generatedKeys =
                                 stmt.getGeneratedKeys()) {

                        if (generatedKeys.next()) {

                            int appointmentId =
                                    generatedKeys.getInt(1);

                            appointment.setId(
                                    appointmentId
                            );

                            System.out.println(
                                    "Appointment created successfully."
                            );

                            System.out.println(
                                    "Appointment ID: "
                                            + appointmentId
                            );
                        }
                    }

                    return true;
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error creating appointment: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // GET APPOINTMENT BY ID
    // =========================================================

    public Appointment getAppointmentById(
            int appointmentId) {

        String sql = """
                SELECT
                    a.AppointmentID,
                    a.PatientID,
                    a.DoctorID,
                    a.AppointmentDate,
                    a.Reason,
                    a.Status,
                    a.Notes,

                    p.FirstName AS PatientFirstName,
                    p.LastName AS PatientLastName,

                    dp.FirstName AS DoctorFirstName,
                    dp.LastName AS DoctorLastName,

                    d.StaffId AS DoctorStaffId,
                    d.Specialization

                FROM Appointment a

                INNER JOIN Patient pt
                    ON a.PatientID = pt.PatientID

                INNER JOIN Person p
                    ON pt.PersonID = p.PersonID

                INNER JOIN Doctor d
                    ON a.DoctorID = d.DoctorId

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person dp
                    ON s.PersonID = dp.PersonID

                WHERE a.AppointmentID = ?
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    appointmentId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                if (rs.next()) {

                    return mapAppointment(rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving appointment: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // GET ALL APPOINTMENTS
    // =========================================================

    public List<Appointment> getAllAppointments() {

        List<Appointment> appointments =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.AppointmentID,
                    a.PatientID,
                    a.DoctorID,
                    a.AppointmentDate,
                    a.Reason,
                    a.Status,
                    a.Notes,

                    p.FirstName AS PatientFirstName,
                    p.LastName AS PatientLastName,

                    dp.FirstName AS DoctorFirstName,
                    dp.LastName AS DoctorLastName,

                    d.StaffId AS DoctorStaffId,
                    d.Specialization

                FROM Appointment a

                INNER JOIN Patient pt
                    ON a.PatientID = pt.PatientID

                INNER JOIN Person p
                    ON pt.PersonID = p.PersonID

                INNER JOIN Doctor d
                    ON a.DoctorID = d.DoctorId

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person dp
                    ON s.PersonID = dp.PersonID

                ORDER BY a.AppointmentDate
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql);
             ResultSet rs =
                     stmt.executeQuery()) {

            while (rs.next()) {

                appointments.add(
                        mapAppointment(rs)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving appointments: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return appointments;
    }


    // =========================================================
    // UPDATE APPOINTMENT
    // =========================================================

    public boolean updateAppointment(
            Appointment appointment) {

        String doctorIdSql = """
                SELECT DoctorId
                FROM Doctor
                WHERE StaffId = ?
                """;

        String sql = """
                UPDATE Appointment
                SET
                    PatientID = ?,
                    DoctorID = ?,
                    AppointmentDate = ?,
                    Reason = ?,
                    Status = ?,
                    Notes = ?
                WHERE AppointmentID = ?
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection()) {

            int doctorId;

            // =================================================
            // FIND ACTUAL DoctorId
            // =================================================

            try (PreparedStatement doctorStmt =
                         conn.prepareStatement(
                                 doctorIdSql)) {

                doctorStmt.setInt(
                        1,
                        appointment.getDoctor().getStaffID()
                );

                try (ResultSet rs =
                             doctorStmt.executeQuery()) {

                    if (!rs.next()) {

                        System.out.println(
                                "Doctor not found."
                        );

                        return false;
                    }

                    doctorId =
                            rs.getInt("DoctorId");
                }
            }

            // =================================================
            // UPDATE
            // =================================================

            try (PreparedStatement stmt =
                         conn.prepareStatement(sql)) {

                stmt.setInt(
                        1,
                        appointment.getPatient().getPatientID()
                );

                stmt.setInt(
                        2,
                        doctorId
                );

                stmt.setObject(
                        3,
                        appointment.getAppointmentDate()
                );

                stmt.setString(
                        4,
                        appointment.getReason()
                );

                stmt.setString(
                        5,
                        appointment.getStatus()
                );

                stmt.setString(
                        6,
                        appointment.getNotes()
                );

                stmt.setInt(
                        7,
                        appointment.getId()
                );

                return stmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error updating appointment: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // DELETE APPOINTMENT
    // =========================================================

    public boolean deleteAppointment(
            int appointmentId) {

        String sql = """
                DELETE FROM Appointment
                WHERE AppointmentID = ?
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    appointmentId
            );

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error deleting appointment: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return false;
    }


    // =========================================================
    // GET APPOINTMENTS BY PATIENT
    // =========================================================

    public List<Appointment> getAppointmentsByPatient(
            int patientId) {

        List<Appointment> appointments =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.AppointmentID,
                    a.PatientID,
                    a.DoctorID,
                    a.AppointmentDate,
                    a.Reason,
                    a.Status,
                    a.Notes,

                    p.FirstName AS PatientFirstName,
                    p.LastName AS PatientLastName,

                    dp.FirstName AS DoctorFirstName,
                    dp.LastName AS DoctorLastName,

                    d.StaffId AS DoctorStaffId,
                    d.Specialization

                FROM Appointment a

                INNER JOIN Patient pt
                    ON a.PatientID = pt.PatientID

                INNER JOIN Person p
                    ON pt.PersonID = p.PersonID

                INNER JOIN Doctor d
                    ON a.DoctorID = d.DoctorId

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person dp
                    ON s.PersonID = dp.PersonID

                WHERE a.PatientID = ?

                ORDER BY a.AppointmentDate
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    patientId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    appointments.add(
                            mapAppointment(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving patient appointments: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return appointments;
    }


    // =========================================================
    // GET APPOINTMENTS BY DOCTOR
    // =========================================================

    /*
     * The menu currently passes a Staff ID.
     * Therefore we filter using Doctor.StaffId.
     */

    public List<Appointment> getAppointmentsByDoctor(
            int staffId) {

        List<Appointment> appointments =
                new ArrayList<>();

        String sql = """
                SELECT
                    a.AppointmentID,
                    a.PatientID,
                    a.DoctorID,
                    a.AppointmentDate,
                    a.Reason,
                    a.Status,
                    a.Notes,

                    p.FirstName AS PatientFirstName,
                    p.LastName AS PatientLastName,

                    dp.FirstName AS DoctorFirstName,
                    dp.LastName AS DoctorLastName,

                    d.StaffId AS DoctorStaffId,
                    d.Specialization

                FROM Appointment a

                INNER JOIN Patient pt
                    ON a.PatientID = pt.PatientID

                INNER JOIN Person p
                    ON pt.PersonID = p.PersonID

                INNER JOIN Doctor d
                    ON a.DoctorID = d.DoctorId

                INNER JOIN Staff s
                    ON d.StaffId = s.StaffId

                INNER JOIN Person dp
                    ON s.PersonID = dp.PersonID

                WHERE d.StaffId = ?

                ORDER BY a.AppointmentDate
                """;

        try (Connection conn =
                     DatabaseConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setInt(
                    1,
                    staffId
            );

            try (ResultSet rs =
                         stmt.executeQuery()) {

                while (rs.next()) {

                    appointments.add(
                            mapAppointment(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error retrieving doctor appointments: "
                            + e.getMessage()
            );

            e.printStackTrace();
        }

        return appointments;
    }


    // =========================================================
    // MAP RESULTSET TO APPOINTMENT OBJECT
    // =========================================================

    private Appointment mapAppointment(
            ResultSet rs) throws SQLException {

        Appointment appointment =
                new Appointment();


        // =====================================================
        // APPOINTMENT
        // =====================================================

        appointment.setId(
                rs.getInt("AppointmentID")
        );


        // =====================================================
        // PATIENT
        // =====================================================

        Patient patient =
                new Patient();

        patient.setPatientID(
                rs.getInt("PatientID")
        );

        patient.setFirstName(
                rs.getString("PatientFirstName")
        );

        patient.setLastName(
                rs.getString("PatientLastName")
        );


        // =====================================================
        // DOCTOR
        // =====================================================

        Doctor doctor =
                new Doctor();

        /*
         * The application identifies doctors using StaffId.
         * Therefore, when mapping an appointment, we restore
         * StaffId rather than putting DoctorId into StaffId.
         */

        doctor.setStaffID(
                rs.getInt("DoctorStaffId")
        );

        doctor.setFirstName(
                rs.getString("DoctorFirstName")
        );

        doctor.setLastName(
                rs.getString("DoctorLastName")
        );

        doctor.setSpecialization(
                rs.getString("Specialization")
        );


        // =====================================================
        // SET PATIENT AND DOCTOR
        // =====================================================

        appointment.setPatient(
                patient
        );

        appointment.setDoctor(
                doctor
        );


        // =====================================================
        // APPOINTMENT DATE
        // =====================================================

        Timestamp timestamp =
                rs.getTimestamp("AppointmentDate");

        if (timestamp != null) {

            appointment.setAppointmentDate(
                    timestamp.toLocalDateTime()
            );
        }


        // =====================================================
        // REASON
        // =====================================================

        appointment.setReason(
                rs.getString("Reason")
        );


        // =====================================================
        // STATUS
        // =====================================================

        appointment.setStatus(
                rs.getString("Status")
        );


        // =====================================================
        // NOTES
        // =====================================================

        appointment.setNotes(
                rs.getString("Notes")
        );


        return appointment;
    }
}