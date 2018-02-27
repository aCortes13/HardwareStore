package HardwareStore;
import java.io.Serializable;

/**
 * A simple definition of a customer containing a phone number and an adddress. 
 * @author Anthony Cortes
 */
public class Customer extends User implements Serializable {
    private String phoneNumber;
    private String address;
    
    /**
     *  Initializes the Customer class.
     *  @param idNumber an <CODE>int</CODE> that represents the user Id
     *  @param firstName a <CODE>String</CODE> that represents the users first name
     *  @param lastName a <CODE>String</CODE> that represents the users last name
     *  @param phoneNumber a <CODE>String</CODE> that contains the phone number
     *  @param address a <CODE>String</CODE> that represents the customers address 
     */
    public Customer(int userId, String firstName, String lastName, String phoneNumber, String address) {
        super(userId, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.address = address;
        System.out.println("New Customer added");
    }
    
    public String getType() {
        return "Customer";
    }
    
    /**
     * @return phoneNumber
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * @return address
     */
    
    public String getAddress() {
        return address;
    }
    
    /**
     * Sets phoneNumber
     * 
     * @param phoneNumber a <CODE>String</CODE> that sets the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * @param address a <CODE>String</CODE> that sets the address of the customer
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
