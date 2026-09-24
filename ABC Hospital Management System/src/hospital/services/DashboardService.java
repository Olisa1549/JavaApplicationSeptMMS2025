package hospital.services;

import hospital.dao.InvoiceDAO;
import hospital.models.Appointment;
import hospital.models.DashboardStats;
import hospital.models.Invoice;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DashboardService {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final InvoiceDAO invoiceDAO;

    public DashboardService() {

        patientService = new PatientService();
        doctorService = new DoctorService();
        appointmentService = new AppointmentService();
        invoiceDAO = new InvoiceDAO();
    }

    public DashboardStats getDashboardStats() {

        DashboardStats stats = new DashboardStats();

        // ==========================================
        // PATIENTS
        // ==========================================

        try {
            if (patientService.getAllPatients() != null) {
                stats.setPatientCount(
                        patientService.getAllPatients().size()
                );
            }
        } catch (Exception e) {
            System.err.println(
                    "Unable to load patient count: "
                            + e.getMessage()
            );
        }

        // ==========================================
        // DOCTORS
        // ==========================================

        try {
            if (doctorService.getAllDoctors() != null) {
                stats.setDoctorCount(
                        doctorService.getAllDoctors().size()
                );
            }
        } catch (Exception e) {
            System.err.println(
                    "Unable to load doctor count: "
                            + e.getMessage()
            );
        }

        // ==========================================
        // TODAY'S APPOINTMENTS
        // ==========================================

        try {

            List<Appointment> appointments =
                    appointmentService.getAllAppointments();

            int count = 0;

            LocalDate today = LocalDate.now();

            if (appointments != null) {

                for (Appointment appointment : appointments) {

                    if (appointment == null ||
                            appointment.getAppointmentDate() == null) {
                        continue;
                    }

                    if (appointment.getAppointmentDate()
                            .toLocalDate()
                            .equals(today)) {

                        count++;
                    }
                }
            }

            stats.setTodayAppointmentCount(count);

        } catch (Exception e) {

            System.err.println(
                    "Unable to load today's appointments: "
                            + e.getMessage()
            );
        }

        // ==========================================
        // PENDING BILLS
        // ==========================================

        try {

            List<Invoice> invoices =
                    invoiceDAO.findAllInvoices();

            double pendingAmount = 0.0;

            if (invoices != null) {

                for (Invoice invoice : invoices) {

                    if (invoice == null) {
                        continue;
                    }

                    String status = invoice.getStatus();

                    boolean isPaid =
                            status != null &&
                            status.trim().equalsIgnoreCase("Paid");

                    if (!isPaid) {

                        pendingAmount +=
                                invoice.getTotalAmount();
                    }
                }
            }

            stats.setPendingBillsAmount(pendingAmount);

        } catch (Exception e) {

            System.err.println(
                    "Unable to load pending bills: "
                            + e.getMessage()
            );
        }

        return stats;
    }

    public List<Appointment> getRecentAppointments(int limit) {

        List<Appointment> appointments =
                new ArrayList<>();

        try {

            List<Appointment> allAppointments =
                    appointmentService.getAllAppointments();

            if (allAppointments != null) {
                appointments.addAll(allAppointments);
            }

        } catch (Exception e) {

            System.err.println(
                    "Unable to load recent appointments: "
                            + e.getMessage()
            );

            return appointments;
        }

        appointments.sort(
                Comparator.comparing(
                        Appointment::getAppointmentDate,
                        Comparator.nullsLast(
                                Comparator.reverseOrder()
                        )
                )
        );

        if (limit > 0 &&
                appointments.size() > limit) {

            return new ArrayList<>(
                    appointments.subList(0, limit)
            );
        }

        return appointments;
    }
}