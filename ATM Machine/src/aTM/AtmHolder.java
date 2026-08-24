
package aTM;


public class AtmHolder {

   private String AccountName;
   private String AccountType;
   private int pin;

    public AtmHolder(String AccountName, String AccountType, int pin) {
        this.AccountName = AccountName;
        this.AccountType = AccountType;
        this.pin = pin;
    }

   
    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String AccountName) {
        this.AccountName = AccountName;
    }

    public String getAccountType() {
        return AccountType;
    }

    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
   
}
