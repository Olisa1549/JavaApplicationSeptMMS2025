public class StackFrame{
	public static void main(String[] args){
		readMe("The story is about a little girl who left home and was lost in the park");
		
	}
	public static void readMe(String story){
		System.out.println("====Story====");
		System.out.printf("%s%n",story);	
		
		checkAge(20);
	}
	
	public static void checkAge(int age){
		if(age >= 18){
			System.out.printf("Your age %d, you are an adult%n",age);
		}
		else{
			System.out.printf("Your age %d, you are still a minor%n",age);
		}
	}
}