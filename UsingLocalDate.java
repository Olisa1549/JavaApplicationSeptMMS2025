import java.time.LocalDate;

public class UsingLocalDate{
	public static void main (String[] args){
		LocalDate todayDate = LocalDate.now();
		LocalDate myBirthDate = LocalDate.of(2000,5,25);
		LocalDate resumptionDate = LocalDate.parse("2028-10-15");
		
		boolean isLeapYear = resumptionDate.isLeapYear();
		boolean isEqual = resumptionDate.equals(myBirthDate);
		
		System.out.printf("Todays date is %s%n",todayDate);
		System.out.printf("My birth date is %s%n",myBirthDate);
		System.out.printf("The resumption date is %s%n",resumptionDate);
		System.out.printf("The resumption year is %s%n",resumptionDate.getYear());
		System.out.printf("The resumption month is %s%n",resumptionDate.getMonth());
		System.out.printf("The resumption day is %s%n",resumptionDate.getDayOfMonth());
		System.out.printf("The meeting date is %s%n",resumptionDate.plusDays(10));
		System.out.printf("The party date will be %s%n",resumptionDate.plusMonths(5));
		System.out.printf("Is the resumption date a leap year? %b%n",isLeapYear);
		System.out.printf("Is %s the same as %s? %b%n",resumptionDate,myBirthDate,isEqual);
	}
}