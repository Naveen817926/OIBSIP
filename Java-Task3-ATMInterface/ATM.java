import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner sc;
    private Account currentAccount;
    private ArrayList<Transaction> transactionHistory;

    public ATM(Bank bank) {

        this.bank = bank;
        this.sc = new Scanner(System.in);
        this.transactionHistory = new ArrayList<>();
    }

    public void start() {

        System.out.println("=================================");
        System.out.println("          ATM INTERFACE");
        System.out.println("=================================");

        if (!login()) {
            System.out.println("Too many incorrect attempts.");
            System.out.println("Access Denied.");
            return;
        }

        boolean running = true;

        while (running) {

            System.out.println("\n=================================");
            System.out.println("             ATM MENU");
            System.out.println("=================================");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Balance Check");
            System.out.println("6. Quit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input.");
                sc.next();
                continue;
            }

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    showTransactionHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    checkBalance();
                    break;

                case 6:
                    System.out.println("\nThank you for using the ATM.");
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private boolean login() {

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String userId = sc.next();

            System.out.print("Enter PIN: ");
            String pin = sc.next();

            Account account = bank.authenticate(userId, pin);

            if (account != null) {

                currentAccount = account;

                System.out.println("\nLogin successful!");
                System.out.println("Welcome, " + currentAccount.getUserId());

                return true;
            }

            attempts++;

            System.out.println("Incorrect User ID or PIN.");
            System.out.println("Attempts remaining: " + (3 - attempts));
        }

        return false;
    }

    private void withdraw() {

        System.out.print("Enter withdrawal amount: ");

        if (!sc.hasNextDouble()) {
            System.out.println("Invalid amount.");
            sc.next();
            return;
        }

        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        if (currentAccount.withdraw(amount)) {

            transactionHistory.add(
                    new Transaction(
                            "WITHDRAW",
                            "Cash withdrawn",
                            amount
                    )
            );

            System.out.println("Withdrawal successful.");
            System.out.println("Amount: Rs." + amount);
            System.out.println("Remaining Balance: Rs."
                    + currentAccount.getBalance());

        } else {

            System.out.println("Insufficient Funds.");
        }
    }

    private void deposit() {

        System.out.print("Enter deposit amount: ");

        if (!sc.hasNextDouble()) {
            System.out.println("Invalid amount.");
            sc.next();
            return;
        }

        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        currentAccount.deposit(amount);

        transactionHistory.add(
                new Transaction(
                        "DEPOSIT",
                        "Cash deposited",
                        amount
                )
        );

        System.out.println("Deposit successful.");
        System.out.println("Amount: Rs." + amount);
        System.out.println("Current Balance: Rs."
                + currentAccount.getBalance());
    }

    private void transfer() {

        System.out.print("Enter recipient Account ID: ");
        String recipientId = sc.next();

        System.out.print("Enter transfer amount: ");

        if (!sc.hasNextDouble()) {
            System.out.println("Invalid amount.");
            sc.next();
            return;
        }

        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return;
        }

        Account recipient = bank.findAccount(recipientId);

        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        if (amount > currentAccount.getBalance()) {
            System.out.println("Insufficient Funds.");
            return;
        }

        boolean success = bank.transfer(
                currentAccount.getAccountId(),
                recipientId,
                amount
        );

        if (success) {

            transactionHistory.add(
                    new Transaction(
                            "TRANSFER",
                            "Transferred to " + recipientId,
                            amount
                    )
            );

            System.out.println("Transfer successful.");
            System.out.println("Amount: Rs." + amount);
            System.out.println("Recipient: " + recipientId);
            System.out.println("Remaining Balance: Rs."
                    + currentAccount.getBalance());

        } else {

            System.out.println("Transfer failed.");
        }
    }

    private void showTransactionHistory() {

        System.out.println("\n=================================");
        System.out.println("       TRANSACTION HISTORY");
        System.out.println("=================================");

        if (transactionHistory.isEmpty()) {

            System.out.println("No transactions found.");

        } else {

            for (Transaction transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }

        System.out.println("=================================");
    }

    private void checkBalance() {

        System.out.println("\nCurrent Balance: Rs."
                + currentAccount.getBalance());
    }
}