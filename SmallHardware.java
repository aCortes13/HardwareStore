package HardwareStore;
import java.io.Serializable;
/**
 * This class is a representation of a small hardware item. Only has getter methods and 
 * therefore cannot be mutated once initialized. 
 * 
 * @author Anthony Cortes
 */

class SmallHardware extends Item implements Serializable {
    
    private final String category;
    
    SmallHardware(String idNumber, String name, int quantity, float price, String category) {
        super(idNumber, name, quantity, price);
        this.category = category;
    }
    
    /**
     * This method returns the item's category.
     *
     * @return a <b><CODE>String</CODE></b> that is the item's category.
     */
    public String getCategory() {
        return category;
    }
}

