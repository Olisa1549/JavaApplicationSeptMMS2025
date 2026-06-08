public class LocalVariable {
	public static void main (String[] args){
		int age = 35;
		if (age >= 18){
			String name = "John Doe";
			System.out.printf("Your name is %s and you are %d years old%n",name,age);
		}
		System.out.printf("I am printing the name %d here%n",age);
	}
}