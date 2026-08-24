package inheritance;

public class Teacher extends Person {
    private String department;
    private double salary;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Teacher(String department, double salary, String name, int age) {
        super(name, age);
        this.department = department;
        this.salary = salary;
    }
    @Override
    public void displayPersonInfo(){
    System.out.println("*************************");
    super.displayPersonInfo();
    System.out.println("Teachers Department: " +department);
    System.out.println("Teachers Salary: " +salary);
    System.out.println("*************************");
    }
    
}