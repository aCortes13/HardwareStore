package HardwareStore;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * This class is used to represent a database interface for a list of
 * <CODE>item</CODE>'s. It using a plain-text file "database.txt" to store and
 * write item objects in readable text form. It contains an
 * <CODE>ArrayList</CODE> called <CODE>itemList</CODE> to store the database in
 * a runtime friendly data structure. The <CODE>itemList</CODE> is written to
 * "database.txt" at the end of the <CODE>HardwareStore</CODE> object's life by
 * calling <CODE>writeDatabase()</CODE>. This class also provides methods for
 * adding, removing, and searching for items in the list.
 * -Code adopted from Junye Wen-
 * 
 * @author Anthony Cortes
 */
public class HardwareStore implements Serializable {

    private ArrayList<Item> itemList;
    private ArrayList<User> userList;
    private ArrayList<Transaction> transactionList;

    private static final String DATA_FILE_NAME = "HardwareStore.ser";


    /**
     * This constructor creates an empty ArrayList and then calls the 
     * <CODE>readDatabase()</CODE> method to populate items previously stored. 
     *
     * @throws IOException
     */
    public HardwareStore() throws IOException {
        itemList = new ArrayList<Item>();
        userList = new ArrayList<User>();
        transactionList = new ArrayList<Transaction>();
        readDatabase();
    }

    /**
     * Method getAllItemsFormatted returns the current list of items in the Arraylist in
     * no particular order.
     * 
     * @return a formatted String representation of all the items in itemList.
     */
    public String getAllItemsFormatted() {
        return getFormattedItemList(itemList);
    }

    /**
     * Method used to organize code and reduce clutter for items
     */
    public static String itemLine() {
        return " ------------------------------------------------------------------------------------------\n";
    }

    /**
     * Method used to organize line code for users
     */
    public static String userLine() {
        return " ------------------------------------------------------------------------------------\n";
    }

    /**
     * method used to organize header for users
     */
    public static String userHeader() {
        return String.format("| %-10s| %-15s| %-9s| %-10s| %-30s|%n", "ID Number", "Name", "User type",
            "SSN / Phone", "Monthly Salary / Address");
    }

    /**
     * Method used to reduce clutter for items
     * 
     */
    public static String itemHeader() {
        return String.format("| %-10s| %-25s| %-10s| %-10s| %-20s|%n", "ID Number", "Name", "Quantity",
            "Price", "Category / Brand and Type");
    }

    /**
     * Method getAllUsers returns the current list of users in the userList
     * 
     * @return a formatted string representation of all users in userList
     */
    public String getAllUsers() {
        return getFormattedUsers(userList);
    }
    
    public String getTransactions() {
        return getFormattedTransactions(transactionList);
    }
    
    /**
     * Method sale gets the necessary information from the user to make a transaction by displaying users, employees, and getting
     *  the necessary sales info. 
     */
    public String sale(Scanner CONSOLE_INPUT) {
        String itemId;
        int saleQuantity;
        int customerId;
        int employeeId;
        boolean employee = false;
        boolean customer = false;
        boolean flag = false;
        //verify there is at least 1 Employee and at least 1 customer
        for(User temp : userList) {
            if(temp instanceof Employee) {employee = true; }
            if(temp instanceof Customer) {customer = true; }
            if(employee && customer) {break; } //reduce loop iterations
        }
        if(employee && customer && !(itemList.isEmpty())) { 
            // we can continue with transaction
            System.out.println(getAllUsers()); // display users
            
            System.out.print("Input Employee Id to begin transaction: ");
            employeeId = CONSOLE_INPUT.nextInt();
            for(User u : userList) {
                if(u instanceof Employee) {
                    Employee temp = (Employee) u;
                    if(temp.getUserId() == employeeId) {
                        flag = true; //the entered Id matches an ID in the database
                        break; // no need to do further iterations                        
                    }
                }
            }
            if(!flag) {
                System.out.println("Error, no employee found with that ID number.");
                return "[No Sale]";
            } else {flag = false; }
            
            
            System.out.println(getAllUsers()); // display users
            System.out.print("[Verified]\nInput Customer Id: ");
            customerId = CONSOLE_INPUT.nextInt();
            int itemIndex = 0; //used for quantity removal
            for(User u : userList) {
                if(u instanceof Customer) {
                    Customer temp = (Customer) u;
                    if(temp.getUserId() == customerId) {
                        flag = true; //the entered Id matches an ID in the database
                        break; // no need to do further iterations                        
                    }
                }
                itemIndex += 1;
            }
            if(!flag) {
                System.out.println("Error, no Customer found with that ID number.");
                return "[No Sale]";
            } else {flag = false; }
            CONSOLE_INPUT.nextLine(); // input cleanup
            
            // by this point, we have valid Employees and Customers in the system
            System.out.println(getAllItemsFormatted());
            System.out.print("Enter item ID number: ");
            int tempQuantity = 0;
            
            itemId = CONSOLE_INPUT.nextLine();
            for(Item i : itemList) {
                Item temp = (Item) i;
                if(temp.getIdNumber().equalsIgnoreCase(itemId)) {
                    flag = true;
                    tempQuantity = temp.getQuantity();
                    break;
                }
            }
            if(!flag) {
                System.out.println("Item Id not found");
                return "[No Sale]";
            } else {flag = false; }
            
            // by this point we now have valid item Id, Employee, and Customer
            System.out.print("Enter quantity: ");
            saleQuantity = CONSOLE_INPUT.nextInt();
            if(saleQuantity > tempQuantity) {
                System.out.println("Error, quantity(" + saleQuantity + ") exceeds inventory in-stock(" 
                                    + tempQuantity + ").");
                return "[No Sale]";
            }
            
            // Validated: employee, customer, item, and item quantity. Complete transaction
            System.out.print("Working..");
            transactionList.add(new Transaction(itemId, saleQuantity, customerId, employeeId));
            for(Item x : itemList) {
                Item i = (Item) x;
                if(i.getIdNumber().equalsIgnoreCase(itemId)) {
                    i.setQuantity(i.getQuantity() - saleQuantity);
                }
            }
            return "Sale completed.";
        } else {
            return "[Sale Not Possible] Must have at minimum 1 of the following: \n\tCustomer\n\tEmployee\n\tItem\n";        
        }
    }
    /**
     * Method getFormattedTransactions returns a formatted lsit of the transactions in the transactionList
     * 
     * 
     */
    public String getFormattedTransactions(ArrayList<Transaction> sales) {
        String text = "";
        final int LINE_LENGTH = 85;
        if(sales.isEmpty()) {
            text = "[No Transactions]";
        } else {
            for(int i = 0; i <LINE_LENGTH; i++) {text += "-"; }
            text += String.format("\n| %-12s| %-12s| %-8s| %-14s| %-28s|%n", "Customer ID", "Employee Id", "Item Id",
                    "Sale Quantity", "Sale Date");
            for(int i = 0; i <LINE_LENGTH; i++) {text += "-"; }
            text += "\n";
            for(int i = 0; i < sales.size(); i++) {
                text += String.format("| %-12s| %-12s| %-8s| %-14s| %-25s|%n", sales.get(i).getCustomerId(),
                    sales.get(i).getEmployeeId(), sales.get(i).getItemId(), sales.get(i).getSaleQuantity(), sales.get(i).getSaleDate());
            }
            for(int i = 0; i <LINE_LENGTH; i++) {text += "-"; }
        }
        
        return text;
    }
    /**
     * Method getFormattedUsers returns a formatted list of the users in the userList
     * 
     * @param users the user list to be diplayed
     * @return a formatted string representation of all users in the list
     */
    public String getFormattedUsers(ArrayList<User> users) {
        String text;
        if(users.isEmpty()) {
            text = "[No Users]";
        } else {
            text = userLine() + userHeader() + userLine();

            String userType;
            String ssn_phone; //gets employee ssn or customer phone#
            String salary_address; //gets employee salary or customer address
            for (int i = 0; i < users.size(); i++) {
                User object = users.get(i);

                if(object instanceof Employee) {                
                    Employee e = (Employee) object;

                    userType = e.getType();
                    ssn_phone = Integer.toString(e.getSSN());
                    salary_address = Float.toString(e.getSalary());
                }
                else {
                    Customer c = (Customer) object;

                    userType = c.getType();
                    ssn_phone = c.getPhoneNumber();
                    salary_address = c.getAddress();
                }
                text += String.format("| %-10s| %-15.15s| %-9s| %-10s | %-30.30s|%n",
                    users.get(i).getUserId(),
                    users.get(i).getFullName(),
                    userType, ssn_phone, salary_address);
            }
        }
        text += userLine();
        return text;
    }

    /**
     * Private method getFormattedPackageList used as an auxiliary method to return a given ArrayList
     * of items in a formatted manner.
     *
     * @param items the item list to be displayed.
     * @return a formatted String representation of all the items in the list give as a parameter.
     */
    private String getFormattedItemList(ArrayList<Item> items) {
        String text;
        if(items.isEmpty()) {
            text = "[No Items]";
        } else {    
            text = itemLine() + itemHeader() + itemLine();
            String extraItem = "";
            for (int i = 0; i < items.size(); i++) {
                Item object = items.get(i);

                if(object instanceof Appliance) {
                    Appliance extra = (Appliance) object;
                    extraItem = extra.getType();
                }
                else if(object instanceof SmallHardware) {
                    SmallHardware extra = (SmallHardware) object;
                    extraItem = extra.getCategory();
                }
                text += String.format("| %-10s| %-25s| %-10s| %-10s| %-25s|%n",
                    items.get(i).getIdNumber(),
                    items.get(i).getName(),
                    Integer.toString(items.get(i).getQuantity()),
                    String.format("%.2f", items.get(i).getPrice()),
                    extraItem);

            }
            text += itemLine();
        }

        return text;
    }

    /**
     * 
     * 
     */
    public void updateUser(int id, int index, Scanner CONSOLE_INPUT) {
        User temp = userList.get(index);
        if(temp instanceof Employee) {
            Employee e = (Employee) temp;
            // Employee update questions: UPDATE SSN AND UPDATE ID INTENTIONALLY LEFT OFF
            System.out.println("Employee update options: \n" 
                + "\t1. Update First Name\n"
                + "\t2. Update Last Name\n"
                + "\t3. Update Monthly Salary");
            int choice = CONSOLE_INPUT.nextInt();
            switch(choice) {
                case 1:
                    System.out.println("Enter NEW first name for Employee (no spaces)");
                    String newFirst = CONSOLE_INPUT.next();
                    CONSOLE_INPUT.nextLine(); // input cleanup
                    System.out.print("Updating..");
                    e.setFirstName(newFirst);
                    return;
                case 2:
                    System.out.println("Enter NEW last name for Employee (no spaces)");
                    String newLast = CONSOLE_INPUT.next();
                    CONSOLE_INPUT.nextLine(); // input cleanup
                    System.out.print("Updating..");
                    e.setLastName(newLast);
                    return;
                case 3:
                    System.out.println("Enter NEW employee monthly salary");
                    float newSalary = CONSOLE_INPUT.nextFloat();
                    CONSOLE_INPUT.nextLine(); // input cleanup
                    System.out.print("Updating..");
                    e.setSalary(newSalary);
                    return;
                default:
                    System.out.println("Invalid Option");
                    CONSOLE_INPUT.nextLine(); // input cleanup;
                    return;
                }

        }
        else if(temp instanceof Customer) {
            Customer c = (Customer) temp;
            // Custome update questions
            System.out.println("Customer update options: \n" 
                    + "\t1. Update First Name\n"
                    + "\t2. Update Last Name\n"
                    + "\t3. Update Phone Number\n"
                    + "\t4. Update Address");
            int choice = CONSOLE_INPUT.nextInt();
            CONSOLE_INPUT.nextLine(); //input cleanup
            switch(choice) {
                case 1:
                    System.out.println("Enter NEW first name for Customer (no spaces)");
                    String newFirst = CONSOLE_INPUT.next();
                    CONSOLE_INPUT.nextLine(); // input cleanup
                    System.out.print("Updating..");
                    c.setFirstName(newFirst);
                    return;
                case 2:
                    System.out.println("Enter NEW last name for Customer (no spaces)");
                    String newLast = CONSOLE_INPUT.next();
                    CONSOLE_INPUT.nextLine(); // input cleanup
                    System.out.print("Updating..");
                    c.setLastName(newLast);
                    return;
                case 3:
                    System.out.println("Enter NEW Customer Phone number (no hyphens)");
                    // Phone input validation
                    String phoneNum = CONSOLE_INPUT.nextLine();
                    while(phoneNum.length() != 10) {
                        System.out.println("[Error] Phone number must include area code and be 10 digits long");
                        phoneNum = CONSOLE_INPUT.nextLine();
                    }   
                    System.out.print("Updating..");
                    c.setPhoneNumber(phoneNum);
                    return;
                    
                case 4: 
                    //Enter Address: Street info w/ unit; city; state; zip
                    System.out.println("Enter NEW Physical address with apartment or unit number"
                        + "\n\t[Ex: 1701 Mill St Apt 12345]");           
                    String address = CONSOLE_INPUT.nextLine();
                    // city
                    System.out.println("Enter City \n\t[EX: San Marcos]");
                    String city = CONSOLE_INPUT.nextLine();
                    // state
                    System.out.println("Enter State \n\t[Ex: Tx -or- Texas]");
                    String state = CONSOLE_INPUT.nextLine();
                    // zip
                    System.out.println("Enter 5 digit zip");
                    String zip = CONSOLE_INPUT.nextLine();
                    while(zip.length() != 5) {
                        System.out.println("Zip must be 5 digits, try again");
                        zip = CONSOLE_INPUT.nextLine();
                    }
                    address = address + ", " + city + ", " + state + " " + zip;
                    c.setAddress(address);
                default:
                    System.out.println("Invalid Option");
                    return;
                }
        }
        else {
            System.out.println("[Error updating user]");
        }
    }

    /**
     * This method is used to add a item to the itemList ArrayList.
     *
     * @param idNumber a <CODE>String</CODE> representing the ID number of item
     * @param name a <CODE>String</CODE> representing the name of item
     * @param quantiy an <CODE>int</CODE> representing the quantiy of item
     * @param price a <CODE>float</CODE> representing the price of item
     */
    public void addNewItem(String idNumber, String name, int quantiy, float price) {
        //If passed all the checks, add the item to the list
        itemList.add(new Item(idNumber, name, quantiy, price));
        System.out.println("Item has been added.\n");
    }

    /**
     * This method is used to add an item "SmallHardware" to the arrayList
     * 
     * @param idNumber a <CODE>String</CODE> representing the ID number of item
     * @param name a <CODE>String</CODE> representing the name of item
     * @param quantiy an <CODE>int</CODE> representing the quantiy of item
     * @param price a <CODE>float</CODE> representing the price of item
     * @param category a <CODE>String</CODE> representing the category of the Small Hardware
     */
    public void addSmallHardware(String idNumber, String name, int quantity, float price, String category) {
        itemList.add(new SmallHardware(idNumber, name, quantity, price, category));
        System.out.println("SmallHardware Added");
    }

    /**
     * Method generates a new random 5 digit ID number
     * @return 5 digit ID
     */
    public int newUserId() {
        Random rand = new Random();
        int id = 10000 + rand.nextInt(90000);
        return id;
    }

    /**
     * 
     * 
     */
    public void addEmployee(String first, String last, long ssn, float salary) {
        // auto-generate employee ID
        int employeeId = newUserId();

        // verify ID does not exist
        for(User temp : userList) {
            if(temp.getUserId() == employeeId) {
                employeeId = newUserId();
            }
        }
        userList.add(new Employee(employeeId, first, last, (int)ssn, salary));
    }

    /**
     * 
     */
    public void addCustomer(String first, String last, String phone, String address) {
        // auto-generate employee ID
        int customerId = newUserId();

        // verify ID does not exist
        for(User temp : userList) {
            if(temp.getUserId() == customerId) {
                customerId = newUserId();
            }
        }
        userList.add(new Customer(customerId, first, last, phone, address));
    }

    /**
     * This method is used to add an Appliance to the ArrayList 
     * @param idNumber a <CODE>String</CODE> representing the ID number of item
     * @param name a <CODE>String</CODE> representing the name of item
     * @param quantiy an <CODE>int</CODE> representing the quantiy of item
     * @param price a <CODE>float</CODE> representing the price of item
     * @param brand a <CODE>String</CODE> representing the brand of an Appliance
     * @param type a <CODE>String</CODE> representing the type of Appliance
     */
    public void addAppliance(String idNumber, String name, int quantity, float price, 
            String brand, String type) {
        itemList.add(new Appliance(idNumber, name, quantity, price, brand, type));
        System.out.println("Appliance Added");                    
    }

    
    /**
     * Add a certain quantity of the given item index.
     * Preconditions: 1. Item exists.
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to remove
     */
    public void addQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() + quantity);
        System.out.println("Quantity updated.\n");
    }

    /**
     * Removes a certain quantity of the given item index. 
     * Preconditions: 1. Item exists. 2. Quantity to remove smaller than current quantity.
     * @param itemIndex the index of the item in the itemList
     * @param quantity  the quantity to remove
     */
    public void removeQuantity(int itemIndex, int quantity) {
        Item temp = getItem(itemIndex);
        temp.setQuantity(temp.getQuantity() - quantity);
        System.out.println("Quantity updated.\n");
    }

    /**
     * Returns all the items that (partially) match the given name.
     * @param name the name to match.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByName(String name) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getName().toLowerCase().contains(name.toLowerCase())) {
                temp.add(tempItem);
            }
        }

        if (temp.size() == 0) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * Returns all the items with current quantity lower than (or equal) the
     * given threshold.
     * @param quantity the quantity threshold.
     * @return a string containing a table of the matching items.
     */
    public String getMatchingItemsByQuantity(int quantity) {
        ArrayList<Item> temp = new ArrayList<Item>();
        for (Item tempItem : itemList) {
            if (tempItem.getQuantity() <= quantity) {
                temp.add(tempItem);
            }
        }

        if (temp.isEmpty()) {
            return null;
        } else {
            return getFormattedItemList(temp);
        }
    }

    /**
     * This method can be used to find a item in the Arraylist of items.
     *
     * @param idNumber a <CODE>String</CODE> that represents the ID number of
     * the item that to be searched for.
     * @return the <CODE>int</CODE> index of the items in the Arraylist of
     * items, or -1 if the search failed.
     */
    public int findItem(String idNumber) {

        int index = -1;

        for (int i = 0; i < itemList.size(); i++) {
            String temp = itemList.get(i).getIdNumber();
            if (idNumber.equalsIgnoreCase(temp)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * This method is used to retrieve the Item object from the
     * <CODE>itemList</CODE> at a given index.
     *
     * @param i the index of the desired <CODE>Item</CODE> object.
     * @return the <CODE>Item</CODE> object at the index or null if the index is
     * invalid.
     */
    public Item getItem(int i) {
        if (i < itemList.size() && i >= 0) {
            return itemList.get(i);
        } else {
            System.out.println("Invalid Index.\n");
            return null;
        }
    }

    /**
     * This method opens the database file and overwrites it with a
     * serialized representation of all the items in the <CODE>itemList</CODE>. This
     * should be the last method to be called before exiting the program.
     *
     * @throws IOException
     */
    public void writeDatabase() throws IOException {
        try {
            FileOutputStream fs = new FileOutputStream(DATA_FILE_NAME);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(itemList);
            os.writeObject(userList);
            os.writeObject(transactionList);
            os.close();
            fs.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * The method opens the database file and initializes the <CODE>itemList</CODE> 
     * with its contents. If no such file exists, then one is created. 
     * The contents of the file are "loaded" into the itemList ArrayList in no 
     * particular order. The file is then closed during the duration of the 
     * program until <CODE>writeDatabase()</CODE> is called.
     *
     */
    @SuppressWarnings("unchecked")
    public void readDatabase() {
        try {
            FileInputStream fs = new FileInputStream(DATA_FILE_NAME);
            ObjectInputStream os = new ObjectInputStream(fs);
            itemList = (ArrayList<Item>) os.readObject(); // itemList is written first, therefore must be read first
            userList = (ArrayList<User>) os.readObject(); // userList is written second, therefore must be read second
            transactionList = (ArrayList<Transaction>) os.readObject(); // transactionList is written third, therefore must be read third
            os.close();
            fs.close();
            }catch(Exception e) {
                System.out.println("[Database Empty]");
            }

     }
}
