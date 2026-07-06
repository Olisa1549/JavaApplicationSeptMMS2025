public class Test4 {
	public static void main(String[] args) {
		
	/*	String name = "Spongebob";
		char firstLetter = 'S';
		int age = 30;
		double height = 60.5;
		boolean isEmployed = true;
	
		System.out.printf("Hello %s%n",name);
		System.out.printf("Your name starts with an %c%n",firstLetter);
		System.out.printf("You are %d years old %n",age);
		System.out.printf("You are %.1f cm tall %n",height);
		System.out.printf("Are you employed? %b%n",isEmployed);
		
		System.out.printf("%s is %d years old %n",name,age); */
		
		
		boolean isStudent = true;
		boolean isSenior = false;
		double price = 9.99;
		
		if(isStudent) {
			if(isSenior){
				System.out.println("You get a student discount of 10%");
				System.out.println("You get a senior discount of 20%");	
				price *= 0.7;
			}
			else{
				System.out.println("You get a student discount of 10%");
				price *= 0.9;
			}
			
		}
		else{
			if(isSenior){
				System.out.println("You get a student discount of 20%");
				price *= 0.8;
			}
			else{
			price *= 1;
			
			}
		}
		System.out.printf("The price of a ticket is: $ %.2f",price);
	}
}
		