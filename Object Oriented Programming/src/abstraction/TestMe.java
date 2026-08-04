
package abstraction;


public class TestMe {
    public static void main(String[] args){
        Student student1 = new Student("John", "Young", "09087654321",
                "No 1 Ali Avenue, Victoria lane, Port Harcourt", 101);
        
        student1.read();
        System.out.println("============================\n");
        student1.study();
    }
}
