package HardwareStore;
import java.io.Serializable;

/**
 * Simple representation of an employee. Additional information includes Social Security Number and Salary
 *
 * @author Anthony Cortes
 */
public class Employee extends User implements Serializable {
    private int ssn;
    private float salary;

    /**
     *  Initializes the Employee class.
     *  @param idNumber an <CODE>int</CODE> that represents the user Id
     *  @param firstName a <CODE>String</CODE> that represents the users first name
     *  @param lastName a <CODE>String</CODE> that represents the users last name
     *  @param ssn an <CODE>int</CODE> that contains the social security number
     *  @param salary a <CODE>float</CODE> that represents the employees monthly salary
     */
    public Employee(int userId, String firstName, String lastName, int ssn, float salary) {
        super(userId, firstName, lastName);
        this.ssn = ssn;
        this.salary = salary;
        System.out.println("New Employee - " + firstName + " - has been created.");
        
    }
    
    public String getType() {
        return "Employee";
    }
    
    /**
     * @return ssn
     */
    public int getSSN() {
        return ssn;
    }
    
    /**
     * @return salary
     */
    
    public float getSalary() {
        return salary;
    }
    
    /**
     * Sets ssn
     * 
     * @param ssn an <CODE>int</CODE> that sets the Social Security Number
     */
    public void setSSN(int ssn) {
        this.ssn = ssn;
    }
    
    /**
     * @param salary a <CODE>Float</CODE> that sets the salary
     */
    public void setSalary(Float salary) {
        this.salary = salary;
    }
}