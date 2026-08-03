
package inheritance;


public class Student extends Person {
    private String course;
    private String level;

    public String getcourse() {
        return course;
    }

    public void setcourse(String Course) {
        this.course = Course;
    }

    public String getlevel() {
        return level;
    }

    public void setlevel(String level) {
        this.level = level;
    }
    
    @Override
    public void displayStudentInfo(){
        super.displayStudentInfo();
        System.out.println("Course" + course);
        System.out.println("level" + level);
    
}

    public Student(String course, String level, String name, int age) {
        super(name, age);
        this.course = course;
        this.level = level;
    }
}
