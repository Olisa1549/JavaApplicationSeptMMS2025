public class WorldPopulationGrowth {

    public static void main(String[] args) {

        long population = 8_100_000_000L; // current world population
        double growthRate = 0.0085;       // 0.85% annual growth

        long currentPopulation = population;

        System.out.println("Year\tPopulation\t\tIncrease");

        int yearWhenDoubled = -1;

        for (int year = 1; year <= 75; year++) {

            long newPopulation = (long)(currentPopulation * (1 + growthRate));
            long increase = newPopulation - currentPopulation;

            System.out.printf("%d\t%d\t\t%d%n",
                    year, newPopulation, increase);

            currentPopulation = newPopulation;

            // check doubling condition
            if (yearWhenDoubled == -1 &&
                currentPopulation >= 2 * population) {
                yearWhenDoubled = year;
            }
        }

        System.out.println();

        if (yearWhenDoubled != -1) {
            System.out.println("Population doubles in year: " + yearWhenDoubled);
        } else {
            System.out.println("Population does not double within 75 years.");
        }
    }
}
