
package classwork6;


public abstract class BankAccount {
    String accountNumber;
    String accountHolder;
    double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }
    
    void deposit(double amount){
        System.out.println("Deposit: " + amount); 
    }  
    
    void displayBalance(){
        System.out.println("Balance: " + balance);
    }
    
    abstract void withdrawal(double amount);
    abstract void calculateInterest(int time);
    
}
