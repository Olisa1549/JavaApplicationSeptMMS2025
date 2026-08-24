
package abstraction;


public class MainInterface {
    public static void main(String[] args) {
        SmartPhone smartPhone = new SmartPhone();
        
        Nokia3310 nokia = new Nokia3310();
        
        System.out.println("\nSmartPhone interface");
        smartPhone.makeCall();
        smartPhone.playGame();
        smartPhone.playMusic();
        smartPhone.playMovie();
        smartPhone.takePicture();
        smartPhone.connectToWiFi();
        
        System.out.println("\nNokia3310 Interface");
        nokia.makeCall();
        nokia.playGame();
        nokia.playMusic();
    }
}
