import java.util.Random;

public class Test2{
	public static void main(String[] args){
		Random rand = new Random();
		
		/* boolean isNHeads;
		
		isNHeads = rand.nextBoolean();
		
		if(isNHeads){
			System.out.println("HEADS");
		}
		else{
			System.out.println("TAILS"); 
		} */
		
		// System.out.println(Math.PI);
		// System.out.println(Math.E);
		
		/* double result;
		result = Math.abs(-5);
		result = Math.pow(2,3);
		result = Math.sqrt(16);
		result = M     ath.round(3.14);
		result = Math.ceil(6.22);
		result = Math.floor(4.7);
		result = Math.max(10, 10.5);
		result = Math.min(14, 79);
		System.out.println(result); */
		
		int num1 = rand.nextInt(7,199);
		System.out.println("You are " + num1 + " years old");
	}
}