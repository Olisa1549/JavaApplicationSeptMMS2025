import java.util.Scanner;

public class ClassWork5{
    public static void main(String[] args) {
		Scanner Input = new Scanner(System.in);
		int num1;
		int num2;
		int num3;
		int num4;
		int num5;
		int num6;
		
		
		System.out.print("Enter first number: ");
		num1 = Input.nextInt();
		
		System.out.print("Enter second number:");
		num2 = Input.nextInt();
		
		System.out.print("Enter third number:");
		num3 = Input.nextInt();
		
		int sum = num1+num2+num3;
		System.out.printf("the sum is %d%n",sum);
		
		
		if(sum%2==0 && sum%10==0){
			System.out.println("Successful");
		     
			System.out.print("Enter first number:");
			num4 = Input.nextInt();
		
			System.out.print("Enter second number:");
			num5 = Input.nextInt();
		
			System.out.print("Enter third number:");
			num6 = Input.nextInt();
			
			int product = num4*num5*num6;
			if (product>200){
				System.out.printf("The product is %d%n",product);
				System.out.println("Hurray!!!");
			}
			else{
				System.out.println("Ongoing....");
			}
		}
		else{
			System.out.println("I must solve this program on my own!");
		}
		
		
		
	}
}