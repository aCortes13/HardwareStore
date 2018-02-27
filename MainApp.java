package HardwareStore;

import java.io.*;
import java.util.Scanner;

/**
 * This is the main class of the Hardware Store database manager. It provides a
 * console for a user to use the 5 main commands. 
 * -Code adopted from Junye Wen-
 * 
 * @author Anthony Cortes
 * 
 */
public class MainApp {
    // This object will allow us to interact with the methods of the class HardwareStore
    private final HardwareStore hardwareStore;
    
    private static final Scanner CONSOLE_INPUT = new Scanner(System.in); // Used to read from System's standard input

    /**
     * Default constructor. Initializes a new object of type HardwareStore
     *
     * @throws IOException
     */
    public MainApp() throws IOException {
        hardwareStore = new HardwareStore();
    }

    /**
     * Shows all items in the inventory.
     */
    public void showAllItems() {
        System.out.print(hardwareStore.getAllItemsFormatted());
    }

    /**
     * This method will add items quantity with given number. If the item does
     * not exist, it will call another method to add it.
     */
    public void addItemQuantity() {
        String brand = "";
        String type = ""; 
        String idNumber;
        System.out.println("Please input the ID of item");
        idNumber = CONSOLE_INPUT.nextLine();

        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be 5 alphanumeric characters.\n");
            return;
        }

        int itemIndex = hardwareStore.findItem(idNumber);
        if (itemIndex != -1) { // If item exists in the database

            System.out.println("[Item found] Please enter quantity to add.");
            int quantity = CONSOLE_INPUT.nextInt();
            if (quantity <= 0) {
                System.out.println("Invalid quantity. "
                        + "The addition amount must be larger than 0.\n");
                return;
            }
            hardwareStore.addQuantity(itemIndex, quantity);
        } else {
            String category = null;
            // If it reaches here, the item does not exist. We need to add new one.
            System.out.println("[Adding new item]\n");
            
            boolean appliance; //flag used to determine storage location
            
            // Determine item type
            System.out.println("Enter item type:\n"
                            + "\t1. Small Hardware Item\n"
                            + "\t2. Appliance" );
            int choice = CONSOLE_INPUT.nextInt();
            while(choice != 1 && choice != 2) {
                System.out.println("[incorrect input] Enter \"1\" or \"2\"");
                choice = CONSOLE_INPUT.nextInt();
            }

            if(choice == 1) {
                appliance = false;
                // Enter category
                System.out.println("\nPlease select the category of small hardware.");
                System.out.println("\t1: Door&Window\n\t2: Cabinet&Furniture\n\t3: Fasteners\n\t4: Structural\n\t5: Other");
                int selection = CONSOLE_INPUT.nextInt();
                
                switch (selection) {
                    case 1:
                        category = "Door&Window";
                        break;
                    case 2:
                        category = "Cabinet&Furniture";
                        break;
                    case 3:
                        category = "Fasteners";
                        break;
                    case 4:
                        category = "Structural";
                        break;
                    case 5:
                        category = "Other";
                        break;
                    default:
                        System.out.println("Invalid category number.");
                        return;
                }
                CONSOLE_INPUT.nextLine(); // eats newLine
            } 
            else {
                CONSOLE_INPUT.nextLine(); // eats newLine
                boolean flag = true;
                System.out.println("Enter brand name: ");
                brand = CONSOLE_INPUT.nextLine();
                
                System.out.println("brand: " + brand);
                
                // Enter Type: [Refrigerators, Washers&Dryers, Ranges&Ovens, Small Appliances] 
                System.out.println("Enter item type:\n" 
                                + "\t1. Refrigerators\n"
                                + "\t2. Washers&Dryers\n"
                                + "\t3. Ranges&Ovens\n"
                                + "\t4. Small Appliances\n");
                choice = CONSOLE_INPUT.nextInt();              
                while(flag){
                    switch (choice) {
                        case 1:
                            type = "Refrigerators";
                            flag = false;
                            break;
                        case 2:
                            type = "Washers&Dryers";
                            flag = false;
                            break;
                        case 3:
                            type = "Ranges&Ovens";
                            flag = false;
                            break;
                        case 4:
                            type = "Small Appliances";
                            flag = false;
                            break;
                        default:
                            System.out.println("Invalid category number. Enter [1-4]");
                            choice = CONSOLE_INPUT.nextInt();
                    }
                }
                CONSOLE_INPUT.nextLine(); // eats newLine
                appliance = true;
                                
            }
                        
            // Enter name
            System.out.println("\nPlease type the name of item.");
            String name = CONSOLE_INPUT.nextLine();

            // Enter quantity
            System.out.println("\nPlease type the quantity of the item.");
            int quantity = CONSOLE_INPUT.nextInt();
            if (quantity < 0) {
                System.out.println("Invalid price. "
                        + "The quantity cannot be smaller than 0.");
                return;
            }

            // Enter price
            System.out.println("\nPlease type the price of the item.");
            float price = CONSOLE_INPUT.nextFloat();
            if (price < 0) {
                System.out.println("Invalid price. "
                        + "The price cannot be smaller than 0.");
                return;
            }
            
            if(appliance) {
                hardwareStore.addAppliance(idNumber, name, quantity,price, brand, type);
            } else {
                hardwareStore.addSmallHardware(idNumber, name, quantity, price, category);   
            }
        }

    }

    /**
     * This method will remove the given quantity of an item with given number.
     * If the item does not exist, it will show an appropriate message.
     */
    public void removeItemQuantity() {

        System.out.println("Please input the ID of item");
        String idNumber = CONSOLE_INPUT.nextLine();
        if (!idNumber.matches("[A-Za-z0-9]{5}")) {
            System.out.println("Invalid ID Number: not proper format. "
                    + "ID Number must be at least 5 alphanumeric characters.");
            return;
        }

        int itemIndex = hardwareStore.findItem(idNumber);
        int currentQuantity;
        if (itemIndex == -1) {
            System.out.println("Item does not exist.\n");
            return;
        } else {
            currentQuantity = hardwareStore.getItem(itemIndex).getQuantity();
            System.out.println("Current quantity: " + currentQuantity + "\n");
        }

        System.out.println("Please input the quantity to remove.");
        int quantity = CONSOLE_INPUT.nextInt();
        if (quantity > currentQuantity) {
            System.out.println("Invalid quantity. "
                    + "The removal amount must be smaller than current quantity.\n");
        } else {
            hardwareStore.removeQuantity(itemIndex, quantity);
        }
    }

    /**
     * This method can search item by a given name (part of name.
     * Case-insensitive.) Will display all items with the given name.
     */
    public void searchItemByName() {

        System.out.println("Please input the name of item.\n");
        String name = CONSOLE_INPUT.nextLine();
        
        String output = hardwareStore.getMatchingItemsByName(name);
        if (output == null) {
            System.out.println("Item not found.");
        } else {
            System.out.println(output);
        }
    }
    
    /**
     * This method calls the HardwareStore to display formatted users
     * 
     */
    public void showUsers() {
        System.out.println(hardwareStore.getAllUsers());
    }  
    
    public void doTransaction() {
        System.out.println(hardwareStore.sale(CONSOLE_INPUT));
    }
    
    public void showTransactions() {
        System.out.println(hardwareStore.getTransactions());
    }
    /**
     * This method will create a new user, either Employee or Customer
     */
    
    public void addUser() {
        System.out.println("Adding user.. Would you like to add or type \"c\" to cancel:\n"
                    + "\t1. Employee\n"
                    + "\t2. Customer\n");
        String choice = CONSOLE_INPUT.nextLine();
        while(!choice.matches("[1-2cC]{1}")) {
            System.out.println("[Invalid Input] try again or type \"c\" to cancel.\n");
            choice = CONSOLE_INPUT.nextLine();
        }
        if(choice.equals("c") || choice.equals("C")) {
            System.out.println("[Cancelled]");
            return;
        }
        
            String first = null;
            String last = null;
            
            //Enter First Name
            System.out.println("Enter first name (no spaces): ");
            first = CONSOLE_INPUT.next();
            CONSOLE_INPUT.nextLine(); // input cleanup
            
            //Enter Last Name
            System.out.println("Enter last name (no spaces): ");
            last = CONSOLE_INPUT.next();
            CONSOLE_INPUT.nextLine(); // input cleanup
            
        if(choice.equals("1")) {
            //Enter SSN   
            System.out.println("Enter Employee's Social Security Number");
            long ssn = CONSOLE_INPUT.nextLong();
            
            // ..Input validation
            while(String.valueOf(ssn).length() != 9) {
                System.out.println("[Social Security Number must be 9 digits. Try again. ]");
                ssn = CONSOLE_INPUT.nextInt();
            }
            
            System.out.println("Enter Employee's Monthly Salary");
            float salary = CONSOLE_INPUT.nextFloat();
            CONSOLE_INPUT.nextLine(); // input cleanup
            
            hardwareStore.addEmployee(first, last, ssn, salary);
        }
        else if(choice.equals("2")) {
            // Enter Phone Number
            System.out.println("Enter Phone Number (no hyphens)");
            String phoneNum = CONSOLE_INPUT.nextLine();
            while(phoneNum.length() != 10) {
                System.out.println("[Error] Phone number must include area code and be 10 digits long");
                phoneNum = CONSOLE_INPUT.nextLine();
            }
            //Enter Address: Street info w/ unit; city; state; zip
            System.out.println("Enter Physical address with apartment or unit number"
                        + "\n\t[Ex: 1701 Mill St Apt 12345]");
            String address = CONSOLE_INPUT.nextLine();
            
            System.out.println("Enter City \n\t[EX: San Marcos]");
            String city = CONSOLE_INPUT.nextLine();
            
            System.out.println("Enter State \n\t[Ex: Tx -or- Texas]");
            String state = CONSOLE_INPUT.nextLine();
            
            System.out.println("Enter 5 digit zip");
            String zip = CONSOLE_INPUT.nextLine();
            while(zip.length() != 5) {
                System.out.println("Zip must be 5 digits, try again");
                zip = CONSOLE_INPUT.nextLine();
            }
            address = address + ", " + city + ", " + state + " " + zip;
            hardwareStore.addCustomer(first, last, phoneNum, address);
        }
    }

    /**
     * This method can search item below a certain quantity. Will display all
     * items fits such condition.
     */
    public void searchItemByQuantity() {

        System.out.println("Please enter the quantity:\n");
        int quantity = CONSOLE_INPUT.nextInt();

        if (quantity < 0) {
            System.out.println("Quantity should be at least 0.\n");
        }

        String output = hardwareStore.getMatchingItemsByQuantity(quantity);
        if (output == null) {
            System.out.println("No items found below given quantity.");
        } else {
            System.out.println(output);
        }
    }
    
    public void updateUserInfo() {
        showUsers();
        
        System.out.println("[Update user info] Input User ID");
        String userId = CONSOLE_INPUT.nextLine();
        int flag = hardwareStore.findItem(userId); //get index of user to be updated
        if(flag == -1) {
            System.out.println("[User Not Found]");
            return;
        }
        else {
            hardwareStore.updateUser(Integer.parseInt(userId), flag, CONSOLE_INPUT);
            System.out.print("done.\n");
        }
    }

    public void saveDatabase() throws IOException {
        hardwareStore.writeDatabase();
    }

    /**
     * This method will begin the user interface console. Main uses a loop to
     * continue executing commands until the user types '6'.
     *
     * @param args this program expects no command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MainApp app = new MainApp();

        String welcomeMessage = "\nChoose one of the following functions:\n\n"
                + "\t1. Show all existing items (Sorted via ID Number).\n"
                + "\t2. Add a new quantity of a specific item to the stock.\n"
                + "\t3. Remove quantity of item.\n"
                + "\t4. Search for an item (given its name or part of its name).\n"
                + "\t5. Show a list of users in the database\n"
                + "\t6. Add new user to the database.\n"
                + "\t7. Update user info (Given user ID).\n"
                + "\t8. Complete sales transaction.\n"
                + "\t9. Show completed sales transactions.\n"
                + "\t10. Exit program.";

        System.out.println(welcomeMessage);

        String selection = CONSOLE_INPUT.next();
        CONSOLE_INPUT.nextLine();

        while (Integer.parseInt(selection) != 10) {
            
            switch (selection) {
                case "1":
                    System.out.println("Show all existing items (Sorted via ID Number).\n");
                    app.showAllItems();
                    break;
                case "2":
                    System.out.println("Add a new quantity of a specific item to the stock.\n");
                    app.addItemQuantity();
                    break;
                case "3":
                    System.out.println("[Remove]Remove quantity of item.\n");
                    app.removeItemQuantity();
                    break;
                case "4":
                    System.out.println("Search for an item (given its name or part of its name).\n");
                    app.searchItemByName();
                    break;
                case "5":
                    System.out.println("Show a list of users in the database\n");
                    app.showUsers();
                    break;
                case "6":
                    System.out.println("Add new user to the database.\n");
                    app.addUser();
                    break;
                case "7":
                    System.out.println("Update user info (Given user ID).\n");
                    app.updateUserInfo();
                    break;
                case "8":
                    System.out.println("Complete sales transaction.\n");
                    app.doTransaction();
                    break;
                case"9":
                    System.out.println("Show completed sales transactions.\n");
                    app.showTransactions();
                    break;
                default:
                    System.out.println("That is not a recognized command. Please enter another command.");
                    break;

            }

            System.out.println(welcomeMessage);
            selection = CONSOLE_INPUT.next();

            CONSOLE_INPUT.nextLine();
        }

        CONSOLE_INPUT.close();
        
        
        System.out.print("Saving database...");
        app.saveDatabase();

        System.out.println("Done!");

    }
}
