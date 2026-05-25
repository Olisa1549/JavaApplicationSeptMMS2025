import java.util.Scanner;

public class SwitchCaseStatement{
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
		
		if (score > 100){
			System.out.println("Score can not be greater than 100");
		}
		else{
		
		switch(score/10){
			case 0:
			case 1:
			case 2:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: F");	
			break;
			
			case 3:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: E");
			break;
			
			case 4:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: D");
			break;
			
			case 5:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: C");
			break;
			
			case 6:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: B");
			break;
			
			case 7:
			case 8:
			case 9:
			case 10:
				System.out.printf("Full name: %s%n",name);
				System.out.printf("Subject: %s%n",subject);
				System.out.printf("Score: %d%n",score);
				System.out.println("Grade: A");
			break;
			default:
				System.out.println("Invalid Input");
		}
		}
	}
}