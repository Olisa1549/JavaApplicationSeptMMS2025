
package classwork6;


public class SavingsAccount extends BankAccount {

    public SavingsAccount(String accountNumber, String accountHolder, double balance) {
        super(accountNumber, accountHolder, balance);
    }

  @Override
  void withdrawal(double amount) {
      balance -= amount;
      System.out.println(balance);
  }

    @Override
    void calculateInterest(int time) {
     double interest = balance * 0.05 * time;
     double amount = interest + balance;
     System.out.printf("The amount %.2f has the simple intrest of %.2f", amount,amount);
     
    }
}
