import java.time.LocalDateTime;

public class UsingLocalDateTime {
    public static void main(String[] args) {
        LocalDateTime todaysDateTime = LocalDateTime.now();
        LocalDateTime meetingDateTime = LocalDateTime.of(2021, 05, 15, 06, 30, 45);
        LocalDateTime classDateTime = LocalDateTime.of(2020, 04, 17, 07, 25, 45);

        boolean isBefore = meetingDateTime.isBefore(classDateTime);
        boolean isAfter = meetingDateTime.isAfter(classDateTime);

        //LocalDateTime classDateTime = LocalDateTime.parse("2020,06:14:07:30:08");

        System.out.printf("The current date and time is %s%n", todaysDateTime);
        System.out.printf("The meeting date and time is %s%n", meetingDateTime);
        //System.out.printf("The class date and time is %s%n", classDateTime);
        System.out.printf("Resumption year is %s%n", meetingDateTime.getYear());
        System.out.printf("Resumption month is %s%n", meetingDateTime.getMonth());
        System.out.printf("Resumption day is %s%n", meetingDateTime.getDayOfMonth());
        System.out.printf("The meeting date is %s%n", meetingDateTime.plusDays(10));
        System.out.printf("The party date will be is %s%n", meetingDateTime.plusMonths(5));
        System.out.printf("Is %s before %s %b%n", meetingDateTime, classDateTime, isBefore);
        System.out.printf("Is %s after %s %b%n", meetingDateTime, classDateTime, isAfter);
    }
}