
package abstraction;


public class SmartPhone implements Callable,Camera,Movie,Music,WiFi,Game {

    @Override
    public void makeCall() {
        System.out.println("John is calling.....");
    }
    
    @Override
    public void takePicture() {
        System.out.println("Picture has been taken.....");
    }

    @Override
    public void playMovie() {
        System.out.println("Movie is playing.....");
    }

    @Override
    public void playMusic() {
       System.out.println("Music is playing.....");
    }

    @Override
    public void connectToWiFi() {
        System.out.println("Connected to WiFi.....");
    }

    @Override
    public void playGame() {
        System.out.println("The game is playing.......");
    }
    
    
}