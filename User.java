package HardwareStore;
import java.io.Serializable;

/**
 * A simple definition of a User. Contains setters and getters for each private variable. 
 *
 * @author Anthony Cortes
 */
public class User implements Serializable {
    private final int userId;
    private String firstName;
    private String lastName;
    /**
     * Initialzes <CODE>userId, firstName, lastName</CODE> to the values passed in that order. 
     * 
     * @param userId a <b><CODE>int</CODE></b> that represents the ID,user ID auto generated **[NOT YET]**
     * @param firstName a <b><CODE>String</CODE></b> that represents the first name.
     * @param lastName a <b><CODE>String</CODE></b> that represents the last name
     */
    public User(int userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * @return userId
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * @return first+last name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    /**
     * @return firstName
     */
    
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets firstName
     * 
     * @param firstName a <CODE>String</CODE> that sets the firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * @param lastName a <CODE>String</CODE> that sets the lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
