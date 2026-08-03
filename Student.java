public class Student{
	// Properties of the student class
	private int studentID;
	private String firstName;
	private String lastName;
	private char gender;
	
	// Constructor 
	public Student(int studentID, String firstName, String lastName, char gender){
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}
	
	public void displayStudentInfo(){
	System.out.println("StudentID: " + studentID);
	System.out.println("First Name: " + firstName);
	System.out.println("Last Name: " + lastName);
	System.out.println("Gender: " + gender);
	//System.out.println("StudentID: " + StudentID);
	}
}