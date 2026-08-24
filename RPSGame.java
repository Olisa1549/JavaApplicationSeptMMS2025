
import java.util.Random;
import java.util.Scanner;


public class RPSGame {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        
        String[] choices = {"rock", "paper", "scissors"};
        String playerChoice;
        String computerChoice;
        String playAgain = "yes";
        
        System.out.print("Enter your move (rock, paper, scissors): ");
        playerChoice = scanner.nextLine().toLowerCase();
        
        if(playerChoice == "rock" || playerChoice == "paper" ||
                playerChoice == "scissors"){
            System.out.println("TRY AGAIN");
        }
    }
}
