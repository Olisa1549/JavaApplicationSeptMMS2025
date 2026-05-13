public class OperatorPrecedence{
	public static void main(String[] args) {
		// 2x2*2y-(2x2+3y)*2x2
		
		int x = 2;
		int y = 3;
		int equation = 2*2*2*2*3 - (2*2*2+3*3)*2*2*2;
		System.out.printf("The result of the equation is %d%n",equation);
	
	}
}