public class EnhancedForLoop{
	public static void main(String[] args){
		String[] names = {"John","Jack","Mary","James","Lucy"};
		
		System.out.println("Student Names are");
		for(String name : names) {
			System.out.printf("%s%n", name);
		}
	}
}
			