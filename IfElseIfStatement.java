import java.util.Scanner;

public class IfElseIfStatement{
	public static void main(String[] args){
			Scanner input = new Scanner(System.in);
			int score;
			String name;
			String subject;
			
			System.out.print("Enter your name: ");
			name = input.nextLine();
			
			System.out.print("Enter your subject: ");
			subject = input.nextLine();
			
			System.out.print("Enter your score: ");
			score = input.nextInt();
			
			System.out.println("");
			System.out.println("========================");
			
			if(score >= 70){
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: A");
			}
			else if(score >= 60){
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: B");
			}
			else if(score >= 50){
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: C");
			}
			else if(score >= 40){
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: D");
			}
			else if(score >= 30){
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: E");
			}
			else{
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: F");	
				
			}
	}
}