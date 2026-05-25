import java.util.Scanner;

public class WhileLoop{
	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int num, sum = 0, i = 1;
		
		while (i <= 10){
			System.out.printf("Enter number%d: ",i);
			num = scan.nextInt();
			
			sum += num;
			i++;
		}
		System.out.printf("The sum is %d%n",sum);
		if(sum%2 ==0){
			System.out.printf("%d is an even number",sum);
		}
		else{
			System.out.printf("%d is an odd number",sum);
		}
	}
	
}