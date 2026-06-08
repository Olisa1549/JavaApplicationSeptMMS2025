public class CompoundInterest {
    public static void main(String[] args) {
        double principal = 1000.0; 
        
        for (int rate = 5; rate <= 10; rate++) {
            double r = rate / 100.0; 
            
            System.out.printf("%nResults for %d%% Interest Rate:%n", rate);
            System.out.printf("%4s%20s%n", "Year", "Amount on Deposit");
  
            for (int year = 1; year <= 10; year++) {
                double amount = principal * Math.pow(1.0 + r, year);
                
                System.out.printf("%4d%,20.2f%n", year, amount);
            }
            System.out.println("----------------------------------");
        }
    }
}