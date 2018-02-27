package HardwareStore;
import java.util.Date;
import java.io.Serializable;
/**
 * Write a description of class Transaction here. All Transactions are final and therefore, immutable. As a result, this class only has getter methods. 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Transaction implements Serializable {
    // instance variables - replace the example below with your own
    private String itemId;
    private Date saleDate;
    private int saleQuantity;
    private int customerId;
    private int employeeId;

    /**
     * Constructor Transaction
     */
    public Transaction(String itemId, int saleQuantity, int customerId, int employeeId) {
        Date saleDate = new Date();
        this.itemId = itemId;
        this.saleDate = saleDate;
        this.saleQuantity = saleQuantity;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }
    
    /** 
     * method getItemId
     * 
     * @return string transaction item id
     */
    public String getItemId() {
        return itemId;
    }
    
    /**
     * Method getSaleDate 
     * 
     * @return String transaction Time of Sale
     */
    public String getSaleDate() {
        return saleDate.toString();
    }
    
    /** 
     * Method getSaleQuantity
     * 
     * @return int number of items sold
     */
    public int getSaleQuantity() {
        return saleQuantity;
    }
    
    /**
     * Method getCustomerId 
     * 
     * @return int Customer Id number
     */
    public int getCustomerId() {
        return customerId;
    }
    
    /**
     * Method getEmployeeId
     * 
     * @return int Employee Id number
     */
    public int getEmployeeId() {
        return employeeId;
    }
}
