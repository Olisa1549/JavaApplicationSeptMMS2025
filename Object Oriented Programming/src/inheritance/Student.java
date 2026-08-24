package inheritance;

public class Student extends Person {
    private String course;
    private int level;

    public Student(String name, int age, String course, int level) {
        super(name, age);
        this.course = course;
        this.level = level;
    }
    


    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int Level) {
        this.level = Level;
    }
    
    @Override
    public void displayPersonInfo(){
    System.out.println("*************************");
    super.displayPersonInfo();
    System.out.println("Student Course: " +course);
    System.out.println("Student Level: " +level);
    System.out.println("*************************");
    }
    }