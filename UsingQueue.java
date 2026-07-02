import java.util.Queue;
import java.util.LinkedList;


public class UsingQueue{
	public static void main(String[] args) {
	Queue<String> names = new LinkedList<>();
	
	names.add("Henry");
	names.add("Young");
	names.add("John");
	names.offer("Peter");
	
	System.out.println(names);
	System.out.println(names.poll());
	System.out.println(names);
	System.out.println(names.peek());
	System.out.println(names);
	System.out.printf("Is my queue empty? %b%n",names.isEmpty());
	names.clear();
	System.out.println(names);
	
	names.add("Henry");
	names.add("Young");
	names.add("John");
	names.offer("Peter");
	System.out.println(names);
	}
}