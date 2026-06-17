public class Matrix{
	public static void main(String[] args){
		int [][] numbers = {
			{7,8,9,6,4},
			{2,5,0,2,1},
			{5,6,3,9,7}
		};
		System.out.println("The element of the array are: ");
		for (int[] row : numbers) {
			for (int value : row) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
	}
}


// Matrix

/* public void printMatrix(int[][] matrix) {
for (int row = 0; row < matrix.length; row++) {
for (int col = 0; col < matrix[row].length; col++) {
System.out.printf("%4d", matrix[row][col]);
}
System.out.println();
}
} */