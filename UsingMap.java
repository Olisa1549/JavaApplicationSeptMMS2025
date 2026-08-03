 import java.util.HashMap;
 import java.util.Map;
 import java.util.Set;

 public class UsingMap{
     public static void main(String[] args) {
         // Create a new HashMap
         Map<Integer, String> map = new HashMap<>();

         // put(K key, V value) - Inserts key-value pairs into the map
         map.put(101, "Frank John");
         map.put(102, "Henry Clinton");
         map.put(103, "Kate Benson");
		 map.put(104, "Lucy Garry");
		 map.put(105, "Johnny Victor");
		 map.put(103, "Kate Benson");
		 map.put(103, "Nathan Zoe");
		 

         // get(Object key) - Returns the value associated with the key
         String value = map.get(102);
         System.out.println("Value for 102: " + value);

         // remove(Object key) - Removes the key-value pair for the specified key
         map.remove(106);

         // containsKey(Object key) - Checks if the map contains the specified key
         boolean hasApple = map.containsKey(106);
         System.out.println("Contains 106: " + hasApple);

         // keySet() - Returns a set view of the keys contained in the map
         Set<Integer> keys = map.keySet();
         System.out.println("Keys in map: " + keys);
     }
 }