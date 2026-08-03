
package inheritance;


public class Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public void displayStudentInfo(){
        System.out.println("Student Name: " + name);
        System.out.println("Student age: " + age);
       
    }
    
}
