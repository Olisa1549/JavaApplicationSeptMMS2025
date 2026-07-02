import java.util.Arrays;

public class UsingArraysClass{
	public static void main (String[] args){
		int[] numbers = {5,8,2,3,9,4,1,6,7,10};
		int[] a = {7,9,6};
		int [] b = {7,9,6};
		
		int[] c = {7,9,9};
		int [] d = {7,9,6};
		
		
		int [] myFillArr = new int[10];
		Arrays.fill(myFillArr,5);
		
		
		
		Arrays.sort(numbers);
		System.out.println("The element of the array are");
		
		for(int number : numbers) {
			System.out.printf("%d%n",number);
		}
		System.out.println("Binary Search");
		int index = Arrays.binarySearch(numbers,9);
		System.out.printf("The index number of 9 is %d%n",index);
		
		int isCompare = Arrays.compare(c,d);
		System.out.printf("The result is %d%n", isCompare);
		
		int isCompare2 = Arrays.compare(d,c);
		System.out.printf("The result is %d%n", isCompare2);
		
		int isCompare3 = Arrays.compare(c,c);
		System.out.printf("The result is %d%n", isCompare3);
		
		int isCompare4 = Arrays.compare(d,d);
		System.out.printf("The result is %d%n", isCompare4);
		
		System.out.println("My fill arrays are:");
		for(int number : myFillArr){
			System.out.printf("%d%n",number);
		}
		
		int[] evenNumbers = {2,4,6,8,10,12,14,16,18,20};
		int[] copyArr = Arrays.copyOf(evenNumbers,5);
		System.out.println("The elements of the array are:");
		for(int arr : copyArr) {
			System.out.printf("%d%n",arr);
		}
		System.out.println(Arrays.toString(evenNumbers));
		
		int[][] multi = {
			{9,8,6},
			{6,7,4},
			{3,2,9}
		};
		System.out.println(Arrays.deepToString(multi));
	}
}