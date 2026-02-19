import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String accName = "Chounce De Guzman";
        String accPass = "pogiako123";
        int balance = 100604;

        System.out.print("Enter account name: ");
        String name = sc.nextLine();

        System.out.print("Enter password: ");
        String pass = sc.nextLine();

        if (!name.equals(accName) || !pass.equals(accPass)) {
            System.out.println("Access Denied");
            return;
        }

        Bank user = new Bank(name, pass, balance);

        System.out.println("Login Successful! Current balance: " + user.getBalance());

        while (true) {

            user.deposit(sc);
            System.out.println("Total Balance: " + user.getBalance());

            user.withdraw(sc);
            System.out.println("Total Balance: " + user.getBalance());

            System.out.print("Do you want to continue? (yes/no): ");
            String choice = sc.nextLine();
            if (!choice.equalsIgnoreCase("yes")) {
                System.out.println("Thank you! Final Balance: " + user.getBalance());
                break;
            }
        }

        sc.close();
    }
}

class Bank {
    private String accName;
    private String accPassword;
    private int Balance;

    public Bank(String name, String pass, int balance) {
        this.accName = name;
        this.accPassword = pass;
        this.Balance = balance;
    }

    public int getBalance() {
        return Balance;
    }

    public void deposit(Scanner sc) {
        System.out.print("Enter deposit amount: ");
        int amount = sc.nextInt();
        sc.nextLine(); 

        if (amount > 0) {
            Balance += amount;
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(Scanner sc) {
        System.out.print("Enter withdrawal amount: ");
        int amount = sc.nextInt();
        sc.nextLine();

        if (amount > Balance) {
            System.out.println("Insufficient balance.");
        } else if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else {
            Balance -= amount;
            System.out.println("Withdrawal successful.");
        }
    }
}
