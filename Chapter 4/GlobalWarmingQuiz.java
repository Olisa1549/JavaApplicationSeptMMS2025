import java.util.Scanner;

public class GlobalWarmingQuiz {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int score = 0;

        System.out.println("Global Warming Facts Quiz");
        System.out.println("Select the best answer (1-4) for each question.\n");

        // Question 1
        System.out.println("1. Which greenhouse gas is most abundant in Earth's atmosphere?");
        System.out.println("1) Carbon Dioxide  2) Methane  3) Water Vapor  4) Nitrous Oxide");
        if (input.nextInt() == 3) score++;

        // Question 2
        System.out.println("\n2. The IPCC identifies the primary driver of recent warming as:");
        System.out.println("1) Solar cycles  2) Human activity  3) Volcanoes  4) Orbital shifts");
        if (input.nextInt() == 2) score++;

        // Question 3
        System.out.println("\n3. A common skeptical argument regarding temperature data is:");
        System.out.println("1) No history  2) Urban Heat Island bias  3) Global cooling  4) CO2 isn't a GHG");
        if (input.nextInt() == 2) score++;

        // Question 4
        System.out.println("\n4. The Medieval Warm Period is cited to suggest:");
        System.out.println("1) Natural variability  2) Ancient industry  3) No Arctic ice  4) High ancient CO2");
        if (input.nextInt() == 1) score++;

        // Question 5
        System.out.println("\n5. Increased CO2 absorption causes oceans to become more:");
        System.out.println("1) Alkaline  2) Acidic  3) Compressed  4) Salty");
        if (input.nextInt() == 2) score++;

        // Final Results
        System.out.printf("%nYour Score: %d/5%n", score);

        if (score == 5) {
            System.out.println("Excellent");
        } else if (score == 4) {
            System.out.println("Very good");
        } else {
            System.out.println("Time to brush up on your knowledge of global warming.");
            System.out.println("Check out these resources:");
            System.out.println("- NASA Vital Signs (climate.nasa.gov)");
            System.out.println("- IPCC Reports (ipcc.ch)");
            System.out.println("- Skeptical Science (skepticalscience.com)");
            System.out.println("- American Enterprise Institute (aei.org)");
        }
        
        input.close();
    }
}