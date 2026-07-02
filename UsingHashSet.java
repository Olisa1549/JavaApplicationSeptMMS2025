import java.util.Set;
import java.util.HashSet;


public class UsingHashSet{
	public static void main(String[] args) {
	Set<String> emailAddress = new HashSet<>();
	
	emailAddress.add("cob4@gmail.com");
	emailAddress.add("amadu@gmail.com");
	emailAddress.add("debbie@gmail.com");
	emailAddress.add("vici@gmail.com");
	emailAddress.add("fred@gmail.com");
	emailAddress.add("tanu@gmail.com");
	emailAddress.add("reve8@gmail.com");
	emailAddress.add("uvig@gmail.com");
	emailAddress.add("koby@gmail.com");
	emailAddress.add("saka@gmail.com");
	
	for(String emailAddres : emailAddress) {
			System.out.println(emailAddres);
		}	
	// System.out.println(emailAddress);
	
	}
}