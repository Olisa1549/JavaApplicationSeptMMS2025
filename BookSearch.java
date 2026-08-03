import java.util.Scanner;

public class BookSearch{
    public static void main(String[] args) {
        String[] books = {
            "Java Programming",
            "Introduction to Algorithms",
            "Data Structures",
            "Computer Networks",
            "Operating Systems",
			"Dork Diaries",
			"Diary of a Wimpy kid",
			"Wings of fire"
        };

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the book title to search: ");
        String searchTitle = scanner.nextLine();

        boolean found = false;

        for (String book : books) {
            if (book.equalsIgnoreCase(searchTitle)) {
                found = true;
            }
        }

        if (found == true) {
            System.out.println("Book found: " + searchTitle);
        } else {
            System.out.println("Book not found.");
        }
    }
}