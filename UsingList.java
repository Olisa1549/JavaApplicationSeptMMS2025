import java.util.List;
import java.util.ArrayList;

public class UsingList{
	public static void main(String[] args) {
		List<String> cars = new ArrayList<>();
		
		cars.add("Toyota");
		cars.add("BWM");
		cars.add("Mercedes Benz");
		cars.add("Toyota");
		cars.add(1,"Ford");
		
		System.out.println(cars.size());
		
		// Set the value of an element
		cars.set(1,"Dodge");
		
		// removing an element from the list
		cars.remove(0);
		
		
		System.out.printf("The size: %d%n%n",cars.size());
		
		// Get element using the indexing 
		System.out.printf("%s%n",cars.get(1));
		
		for(String car : cars){
			System.out.println(car);
		}
		
		System.out.println(cars);
		
	}
}