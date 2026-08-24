
package assignment;


public class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        double newBalance = (balance + amount);
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited Amount: $" + amount);
            System.out.println("Deposited New Balance: $" + newBalance);

        } else {
            System.out.println("Deposit amount must be positive.");

        }
    }
    

    public void withdraw(double amount) {
        double newBalance = (balance - amount);

        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn Amount: $" + amount);
            System.out.println(" New Withdrawn Amount: " + newBalance);

        } else {
            System.out.println("Withdrawal failed: Insufficient funds or invalid amount.");
        }
    }
    
        public void displayAccountDetails() {
            System.out.println("Account Holder: " + accountHolder);
            System.out.println("Account Number: " + accountNumber);
            System.out.println("Account Balance: " + balance);
       
            
    }
}
