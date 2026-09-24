package hospital.models;

public class DashboardStats {

    private int patientCount;
    private int doctorCount;
    private int todayAppointmentCount;
    private double pendingBillsAmount;

    public DashboardStats() {
    }

    public int getPatientCount() {
        return patientCount;
    }

    public void setPatientCount(int patientCount) {
        this.patientCount = patientCount;
    }

    public int getDoctorCount() {
        return doctorCount;
    }

    public void setDoctorCount(int doctorCount) {
        this.doctorCount = doctorCount;
    }

    public int getTodayAppointmentCount() {
        return todayAppointmentCount;
    }

    public void setTodayAppointmentCount(int todayAppointmentCount) {
        this.todayAppointmentCount = todayAppointmentCount;
    }

    public double getPendingBillsAmount() {
        return pendingBillsAmount;
    }

    public void setPendingBillsAmount(double pendingBillsAmount) {
        this.pendingBillsAmount = pendingBillsAmount;
    }
}