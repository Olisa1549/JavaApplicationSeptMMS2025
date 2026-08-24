
package assignment;


public class MainEncapsulation {
    

    public static void main(String[] args){
    BankAccount bankaccount = new BankAccount( "mom","2462",1245.0);
    
    System.out.println("=========FOR WITHDRAWAL=========");
    bankaccount.withdraw(20);
    
     System.out.println("\n=========FOR DEPOSIT=========");
    bankaccount.deposit(100);
    
     System.out.println("\n=========FOR DETAILS=========");
    bankaccount.displayAccountDetails();
    }
}
