import java.util.Scanner; 

public class Looping {
	public static void main (String[] args) {
		Scanner scan = new Scanner (System.in);
	
	System.out.print("Enter a letter: ");
	String a = scan.nextLine(); 
	
		if (a == "Forex") {
			System.out.printf("Do you want to start %d now?",a);
		}
		else {
			System.out.printf("Try inputing the right word");
		}
	}
}
	
		