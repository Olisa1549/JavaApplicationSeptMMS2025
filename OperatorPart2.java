public class OperatorPart2{
	public static void main (String[] args) {
		//Logical Operator(&&, ||, !)
		int num1 = 15;
		int num2 = 30;
		int num3 = 18;
		
		boolean andOperator = (num1 > num2) && (num1 > num3);
		System.out.printf("Is (%d > %d) && (%d > %d): %b%n",num1,num2,num1,num3,andOperator);
	
		boolean orOperator = (num1 > num2) || (num1 > num3);
		System.out.printf("Is (%d > %d) || (%d > %d): %b%n",num1,num2,num1,num3,orOperator);
		
		boolean notOperator = !((num1 > num2) || (num1 > num3));
		System.out.printf("Is (%d > %d) ! (%d > %d): %b%n",num1,num2,num1,num3,notOperator);
		
		//Unary Operator(++, --)
		int x = 5;
		int y = 10;
		
		System.out.printf("The value of x is %d%n", ++x);
		System.out.printf("The value of y is %d%n",y++);
		System.out.printf("The value of y is %d%n",y);
		
		System.out.printf("The value of x is %d%n", --x);
		System.out.printf("The value of y is %d%n",y--);
		System.out.printf("The value of y is %d%n",y);
		
	}
}