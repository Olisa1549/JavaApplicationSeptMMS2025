
package hospital.models;

import java.time.LocalDateTime;


public class Payment {
    private int id;
    private Invoice invoice;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    
    public Payment(){
        
    }

    public int getId() {
        return id;
    }


    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
}
