
package polymorphism.methodoverriding;


public class Dog extends Animal {
    @Override
    void sound() {
        System.out.println("All dogs Bark");
    }
    
}
