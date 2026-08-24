
package classwork6;


public class MainBank {
    public static void main (String[] args) {
        
        SavingsAccount savings = new SavingsAccount("7253928451","Samuel Ekpong",10000.10);
        
        CurrentAccount current = new CurrentAccount("8145739260","Jude Chan",15000.73);
        
        System.out.println("========Savings Account========");
        savings.withdrawal(130);
        savings.calculateInterest(2);
        
        System.out.println("\n========Current Account========");
        current.withdrawal(240);
        current.calculateInterest(2);
        
                
    }
}
