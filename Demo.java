public class Demo{
	public static void main (String[] args){
		DemoInstanceMethod instanceMethod = new DemoInstanceMethod();
		
		int sum = instanceMethod.add(28,50,18);
		System.out.printf("The sum of the numbers is %d%n",sum);
		
		instanceMethod.details(40,"Emmanuel Peter");
	
	}
}