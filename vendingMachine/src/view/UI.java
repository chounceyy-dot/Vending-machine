package view;


public class UI {
    public static void showMainMenu() {
        System.out.println("""
                           \nChounce's V-Machine
                           1) Insert money :D
                           2) Buy something!
                           3) Withdraw all remaining money
                           4) Enter admin panel
        """);
    }
    
    public static void showAdminLogin() {
        System.out.println("""
                           \nAdmin Log-in Page
                           1) Login
                           2) Register
                           3) Go back
        """);
    }
    
    public static void showAdminMenu() {
        System.out.println("""
                           \nAdmin Panel
                           1) Create new product
                           2) Retrieve something
                           3) Edit a product
                           4) Delete a product
                           5) Claim money
                           
                           Others:
                           6) Change password
                           7) Log-out
        """);
    }
}
