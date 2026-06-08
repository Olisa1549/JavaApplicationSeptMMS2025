public class FacebookGrowth {
    public static void main(String[] args) {
        double users = 1_000_000_000.0;
        int months = 0;

        // Time to reach 1.5 billion
        while (users < 1_500_000_000.0) {
            users *= 1.04;
            months++;
        }

        System.out.println("Months to reach 1.5 billion users: " + months);

        // Continue to 2 billion
        while (users < 2_000_000_000.0) {
            users *= 1.04;
            months++;
        }

        System.out.println("Months to reach 2 billion users: " + months);
    }
}