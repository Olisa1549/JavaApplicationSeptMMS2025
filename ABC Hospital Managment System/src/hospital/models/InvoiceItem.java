
package hospital.models;


public class InvoiceItem {
    private int id;
    private Invoice invoice;
    private String description;
    private int quantity;
    private int unitPrice;
    private int amount;
    
    public InvoiceItem(){
        
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

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getunitPrice() {
        return unitPrice;
    }

    public void setunitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
        calculateAmount();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateAmount();
    }

    private void calculateAmount(){
        this.amount = quantity * unitPrice;
    }
}
