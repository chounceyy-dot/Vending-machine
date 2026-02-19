package vendingmachine;
import view.UI;
import utils.DBConnection;
import controller.ProductController;
import controller.AdminController;
import model.Admin;

import java.util.Scanner;


public class VendingMachine {
    
    static Scanner scan = new Scanner(System.in);
    static AdminController ac = new AdminController();
    static int currentMoney = 0;
    
    public static void main(String[] args) 
    {
        
        DBConnection.initializeDatabase();
        
        System.out.println("Project initialized.");
        
        while (true)
        {
            UI.showMainMenu();
            
            int choice = scan.nextInt();
            scan.nextLine();
            choicesMainMenu(choice);
        }
    }
  
    public static void choicesMainMenu(int choice)
    {
        //choices menu for MAIN MENU
        switch (choice) 
        {
            case 1 -> 
            {
                System.out.println("Insert amount of cash (must be whole positive numbers): ");
                int insertedMoney = scan.nextInt();
                scan.nextLine();
                currentMoney = currentMoney + insertedMoney;
                System.out.println("Inserted successfully.");
            }
            
            case 2 ->
            {
                ProductController.viewProducts();
                System.out.println("Select desired product ID: ");
                int selectedID = scan.nextInt();
                scan.nextLine();
                
                System.out.println("Enter quantity: ");
                int howMany = scan.nextInt();
                scan.nextLine();
               
                if (howMany <= ProductController.getQuantity(selectedID))
                {
                    int fullPrice = howMany*ProductController.getPrice(selectedID);
                    
                    if (fullPrice <= currentMoney)
                    {
                            System.out.println(howMany + " pieces of " + ProductController.getProductName(selectedID) + ". Do you want to proceed(1-YES/0-NO)?: ");

                            int buyChoice = scan.nextInt();
                            scan.nextLine();

                            if (buyChoice == 1)
                            {
                                //decrease fullprice from currentMoney
                                currentMoney = currentMoney - fullPrice;
                                ProductController.vendMachMoney += fullPrice;
                                
                                int stock = ProductController.getQuantity(selectedID);
                                int newStock = stock - howMany;
                                
                                ProductController.setQuantity(selectedID, newStock);
                                System.out.println("Bought successfully.");
                            }

                            else // 0
                            {
                                System.out.println("Cancelled successfully.");
                            }
                            
                    }
                    
                    else
                    {
                        System.out.println("Insufficient amount of cash.");
                    }
                }
                
                else //more than the stock
                {
                    System.out.println("More than the # of stock, try again.");
                }
            }
            
            case 3 ->
            {
                System.out.println("Withdrawed " + currentMoney +" pesos successfully.");
                currentMoney = 0;
            }
            
            case 4 ->
            {
                //enter admin panel
                UI.showAdminLogin();
                
                int choiceAdminLogin = scan.nextInt();
                scan.nextLine();
                choicesAdminLogin(choiceAdminLogin);
            }
        }
    }
    
    public static void choicesAdminLogin(int choice)
    {
        switch (choice)
        {
            case 1 -> //LOGIN
            {
                System.out.println("Enter username: ");
                String username = scan.next();
                scan.nextLine();
                
                System.out.println("Enter password: ");
                String password = scan.next();
                scan.nextLine();
                
                Admin admin = ac.login(username, password);
                
                if (admin != null) //basically if walang nainput
                {
                    choicesAdminMenu(admin);
                }
            }
            
            case 2 -> //REGISTER
            {
                System.out.println("Enter username: ");
                String username = scan.next();
                scan.nextLine();
                
                System.out.println("Enter password: ");
                String password = scan.next();
                scan.nextLine();
                
                System.out.println("Confirm password: ");
                String confirmPassword = scan.next();
                scan.nextLine();
                
                Admin admin = ac.register(username, password, confirmPassword);
            }
            
            case 3 ->
            {
                System.out.println("Gone back successfully.");
            }
        }
    }
    
    public static void choicesAdminMenu(Admin admin)
    {
        boolean run = true;
        
        while (run)
        {
            UI.showAdminMenu();
            
            int choice = scan.nextInt();
            scan.nextLine();
            
            switch (choice)
            {
                case 1 ->
                {
                    System.out.println("Enter new product name: ");
                    String productName = scan.next();
                    scan.nextLine();
                    
                    System.out.println("Enter quantity: ");
                    int quantity = scan.nextInt();
                    scan.nextLine();
                    
                    System.out.println("Enter price: ");
                    int price = scan.nextInt();
                    scan.nextLine();
                    
                    ProductController.addProduct(productName, quantity, price);
                    System.out.println("Product created successfully.");
                }
                
                case 2 ->
                {//
                    System.out.println("""
                                       1) View all products
                                       2) A product
                                       """);
                    
                    int choiceRetrieve = scan.nextInt();
                    scan.nextLine();
                    
                    if (choiceRetrieve == 1)
                    {
                        ProductController.viewProducts();
                    }
                    
                    else if (choiceRetrieve == 2)
                    {
                        System.out.println("Enter desired product name: ");
                        String productName = scan.next();
                        scan.nextLine();
                        
                        ProductController.viewOneProduct(productName);
                    }
                }
                
                case 3 ->
                {
                    System.out.println("Enter the new product you wish to change: ");
                    String productName = scan.next();
                    scan.nextLine();
                    
                    System.out.println("Enter new product name: ");
                    String newProductName = scan.next();
                    scan.nextLine();
                    
                    System.out.println("Enter new quantity: ");
                    int newQuantity = scan.nextInt();
                    scan.nextLine();
                    
                    System.out.println("Enter new price: ");
                    int newPrice = scan.nextInt();
                    scan.nextLine();
                    
                    ProductController.editProduct(productName, newProductName, newQuantity, newPrice);
                    System.out.println("Product edited successfully.");
                }
                
                case 4 ->
                {
                    System.out.println("Enter the name of the product you wish to delete: ");
                    String productName = scan.next();
                    scan.nextLine();
                    
                    ProductController.deleteProduct(productName);
                    System.out.println("Product deleted successfullay.");
                }
                
                case 5 ->
                {
                    ProductController.vendMachMoney = 0;
                    System.out.println("Vending machine money claimed successfully.");
                }
                
                case 6 ->
                {
                    System.out.println("Enter old password: ");
                    String oldPassword = scan.nextLine();

                    System.out.println("Enter new password: ");
                    String newPassword = scan.nextLine();

                    System.out.println("Confirm password: ");
                    String confirmPassword = scan.nextLine();

                    AdminController.changePassword(admin, oldPassword, newPassword, confirmPassword);
                }
                
                case 7 ->
                {
                    System.out.println("Logged out.");
                    run = false;
                }
            }
        }
    }
}
