public class DemoStaticMethod{
	
	public static int add(int num1, int num2, int num3){
		
		int sum = num1 + num2 + num3;
		return sum;
	}
	
	public static void details(int age, String name){
		System.out.printf("Your name is %s%n", name);
		System.out.printf("You are %d years old%n",age);
		
	}
}
