public class FactorialCalculator {
    public static void main(String[] args) {
        long factorial = 1;

        System.out.printf("%-5s | %-20s%n", "n", "n!");
        System.out.println("-----------------------------");

        for (int i = 1; i <= 20; i++) {
            factorial *= i;
            
            System.out.printf("%-5d | %-20d%n", i, factorial);
        }
    }
}