
package abstraction;


public class Nokia3310 implements Callable,Music,Game{

    @Override
    public void makeCall() {
        System.out.println("Mom is calling...");
    }

    @Override
    public void playMusic() {
       System.out.println("Music is playing..."); 
    }

    @Override
    public void playGame() {
        System.out.println("Snake game is playing...");
    }
    
    
}
