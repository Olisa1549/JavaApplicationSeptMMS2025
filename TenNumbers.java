import java.util.Scanner;
	
public class TenNumbers{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter the first number: ");
		int first_num = input.nextInt();
		
		System.out.print("Enter the second number: ");
		int second_num = input.nextInt();
		
		System.out.print("Enter the third number: ");
		int third_num = input.nextInt();
		
		System.out.print("Enter the fourth number: ");
		int fourth_num = input.nextInt();
		
		System.out.print("Enter the fifth number: ");
		int fifth_num = input.nextInt();
		
		System.out.print("Enter the sixth number: ");
		int sixth_num = input.nextInt();
		
		System.out.print("Enter the seventh number: ");
		int seventh_num = input.nextInt();
		
		System.out.print("Enter the eigth number: ");
		int eighth_num = input.nextInt();
		
		System.out.print("Enter the ninth number: ");
		int ninth_num = input.nextInt();
		
		System.out.print("Enter the tenth number: ");
		int tenth_num = input.nextInt();
		
		int addition = first_num + fifth_num + tenth_num;
		int sum = third_num + eighth_num + second_num;
		int multiplication = addition * sum;
		
		System.out.printf("The sum of the first, fifth and tenth number is: %d%n",addition);
		System.out.println("====================================================");
		System.out.printf("The sum of the third, eigth and second number is: %d%n",sum);
		System.out.println("====================================================");
		System.out.printf("The multiplication of the number added above is: %d%n",multiplication);
		System.out.println("====================================================");
	
		if (multiplication >= 100){
		System.out.println("Hurray i did it");
		}
		
		else{
		System.out.println("I still need to learn more in java");
		}
		
	}

}
	
		