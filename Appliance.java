package HardwareStore;
import java.io.Serializable;
/**
 * This class is a representation of an Appliance item. Only has getter methods and 
 * therefore cannot be mutated once initialized. 
 * 
 * @author Anthony Cortes
 */

class Appliance extends Item implements Serializable {
    
    private final String brand_type;
    
    Appliance(String idNumber, String name, int quantity, float price,
            String brand, String type) {
        super(idNumber, name, quantity, price);
        brand_type = brand + " - " + type;
    }
    
    /**
     * This method returns the Appliance brand and type.
     *
     * @return a <b><CODE>String</CODE></b> that is the Application.
     */
    public String getType() {
        return brand_type;
    }
    
    /**
     * This method returns the Appliance's fields as a string representation.
     *
     * @return a <b><CODE>String</CODE></b> that lists the fields of the appliance
     * object delineated by a space and in the same order as the constructor
     */
    @Override
    public String toString() {
        return super.getIdNumber() + "~" + super.getName() + "~" + super.getQuantity() + "~"
        		+ String.format("%.2f", super.getPrice()) + brand_type + "\n";
    }
}
