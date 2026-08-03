public class MainApp{
public static void main (String[] args){
	Student student1 = new Student(1,"John","Willy",'M');
	Student student2 = new Student(2,"Clinton","Williams",'M');
	Student student3 = new Student(3,"Lucy","Ben",'F');
	Student student4 = new Student(4,"Joy","Martins",'F');
	Student student5 = new Student(5,"Johnny","Will",'M');
	
	student1.displayStudentInfo();
	System.out.println("\n=====================");
	
	student2.displayStudentInfo();
	System.out.println("\n=====================");
	
	student3.displayStudentInfo();
	System.out.println("\n=====================");
	
	student4.displayStudentInfo();
	System.out.println("\n=====================");
	
	student5.displayStudentInfo(); 
	System.out.println("\n=====================");
	
	}
}
