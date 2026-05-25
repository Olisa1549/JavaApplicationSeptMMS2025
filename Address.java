import java.util.Scanner;

public class Address{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
	System.out.print("Enter your name: ");
	String name = input.nextLine();
		
	System.out.print("Enter your address: ");
	String address1 = input.nextLine();
		
	System.out.print("Enter your age: ");
	byte age = input.nextByte();
	
	System.out.printf("%nHi %s%n",name);
	System.out.printf("You are %d%nyears old",age);
	System.out.printf("and you live at %s%n",address1);

    }
	
}