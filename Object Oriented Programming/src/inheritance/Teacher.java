
package inheritance;


public class Teacher {

    public String getdepartment() {
        return department;
    }

    public void setdepartment(String department) {
        this.department = department;
    }

    public String getsalary() {
        return salary;
    }

    public void setsalary(String salary) {
        this.salary = salary;
    }

    public Teacher(String department, String salary) {
        this.department = department;
        this.salary = salary;
    }
    private String department;
    private String salary;
}
