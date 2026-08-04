
package abstraction;


public abstract class Person {
    String firstName;
    String lastName;
    String phone;
    String address;
    
    public Person(){
        
    }
    
    public Person(String firstName, String lastName,
            String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }
    
    void read (){
        System.out.println("The person is reading");
    }
    
    abstract void study();
}
