
package encapsulation;

public class BankAccount {
    private int accountNumber;
    private String accountHolder;
    private double balance;

//    public BankAccount(int accountNumber, String accountHolder, double balance) {
//        this.accountNumber = accountNumber;
//        this.accountHolder = accountHolder;
//        this.balance = balance;
//    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }
    public void deposit(double balance){
        balance = balance + balance;
        System.out.println("This is your current account balance after deposit");
    }
    public void withdraw (double balance){
        balance = balance - balance;
        System.out.println("This is your current account balance after withdrawing");
    }
    public void AccountDetails() {
        System.out.println("Account number" + accountNumber);
    }
}
