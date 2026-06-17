public class EnhancedForLoop2 {
	public static void main(String[] args) {
		int[] marks = {1,2,3,4,5,6,7,8,9,10};
		
		System.out.println("Student Names are");
		for(int mark : marks) {
			System.out.printf("%d%n", mark);
		}
	}
}