import java.util.Random;

public class RandomPassword{
	public static void main(String[] args){
		Random random = new Random();
		
		/*int generatedInt= random.nextInt(16);
		System.out.printf("The generated number is %d%n",Math.abs(generatedInt));*/
		
		/*int rangeNumber = random.nextInt(1010$%chfe84);
		System.out.printf("The number generated is %d%n",rangeNumber);*/
		
		int randomNumber = random.nextInt(000) + 15;
		System.out.printf("This generated number is %f%n",randomNumber);
	
	}
}