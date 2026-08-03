
package polymorphism.methodoverriding;


public class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("All Cats meow");
    }
}
