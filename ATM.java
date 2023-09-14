import java.util.*;
class Bank {
    private List<User> users;

    public Bank() {
        users = new ArrayList<>();
        // Add some sample users to the bank
        users.add(new User("Rahul", 7890, 1000.0));
        users.add(new User("Shyam", 1230, 2000.0));
        // Add more users as needed
    }

    public User authenticateUser(String userId, int pin) {
        for (User user : users) {
            if (user.getUserId().equals(userId) && user.getPin() == pin) {
                return user;
            }
        }
        return null;
    }

    public User findUserById(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }
}

class ATM {
    private static final int USER_ID_LENGTH = 6;
    private static final int PIN_LENGTH = 4;

    private Bank bank;
    private User currentUser;
    private List<Transaction> transactionHistory;

    public ATM() {
        bank = new Bank();
        transactionHistory = new ArrayList<>();
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        while (true) {
            if (currentUser == null) {
                System.out.print("\nEnter User ID (6 characters): ");
                String userId = getInput(USER_ID_LENGTH);
                System.out.print("Enter PIN (4 digits): ");
                int pin = Integer.parseInt(getInput(PIN_LENGTH));
                currentUser = bank.authenticateUser(userId, pin);
                if (currentUser == null) {
                    System.out.println("\nAuthentication failed. Please try again.");
                } else {
                    System.out.println("\nAuthentication successful. Welcome, " + currentUser.getUserId() + "!");
                }
            } else {
                displayOptions();
                int choice = Integer.parseInt(getInput(1));
                switch (choice) {
                    case 1:
                        viewTransactionHistory();
                        break;
                    case 2:
                        performWithdrawal();
                        break;
                    case 3:
                        performDeposit();
                        break;
                    case 4:
                        performTransfer();
                        break;
                    case 5:
                    checkBalance();
                    break;
                    case 6:
                        logout(); // Option to log out
                        break;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            }
        }
    }

    private String getInput(int maxLength) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine().trim();
        if (input.length() <= maxLength) {
            return input;
        } else {
            System.out.println("Input too long. Please try again.");
            return getInput(maxLength);
        }
    }

    private void displayOptions() {
        System.out.println("\nSelect an option:");
        System.out.println("1. View Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Check Balance");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
    }

    private void viewTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
    private void checkBalance() {
        if (currentUser != null) {
            System.out.println("Your available balance is: $" + currentUser.getBalance());
        } else {
            System.out.println("You are not logged in. Please log in to check your balance.");
        }
    }


    private void performWithdrawal() {
        System.out.print("Enter withdrawal amount: $");
        double amount;
        try {
            amount = Double.parseDouble(getInput(10));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (amount <= 0) {
            handleNegativeTransferAmount();
            return;
        }

        if (currentUser.withdraw(amount)) {
            transactionHistory.add(new Transaction("Withdrawal", amount));
            System.out.println("Withdrawal successful. New balance: $" + currentUser.getBalance());
        } else {
            handleInvalidWithdrawalAmount();
        }
    }

    private void performDeposit() {
        System.out.print("Enter deposit amount: $");
        double amount;
        try {
            amount = Double.parseDouble(getInput(10));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (amount <= 0) {
            handleInvalidDepositAmount();
            return;
        }

        currentUser.deposit(amount);
        transactionHistory.add(new Transaction("Deposit", amount));
        System.out.println("Deposit successful. New balance: $" + currentUser.getBalance());
    }

    private void performTransfer() {
        System.out.print("Enter recipient's User ID: ");
        String recipientId = getInput(USER_ID_LENGTH);
        User recipient = bank.findUserById(recipientId);
        if (recipient == null) {
            System.out.println("Recipient not found. Please check the User ID.");
            return;
        }
        System.out.print("Enter transfer amount: $");
        double amount;
        try {
            amount = Double.parseDouble(getInput(10));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        if (amount <= 0) {
            handleNegativeTransferAmount();
            return;
        }

        if (currentUser.transfer(recipient, amount)) {
            transactionHistory.add(new Transaction("Transfer to " + recipient.getUserId(), amount));
            System.out.println("Transfer successful. New balance: $" + currentUser.getBalance());
        } else {
            handleInvalidTransfer();
        }
    }

    private void handleNegativeTransferAmount() {
        System.out.println("Transfer amount must be a positive value.");
    }

    private void handleInvalidWithdrawalAmount() {
        System.out.println("Invalid withdrawal amount or insufficient balance.");
    }

    private void handleInvalidDepositAmount() {
        System.out.println("Invalid deposit amount. Please enter a positive amount.");
    }

    private void handleInvalidTransfer() {
        System.out.println("Transfer failed. Insufficient balance or invalid recipient.");
    }

    private void logout() {
        currentUser = null;
        transactionHistory.clear();
        System.out.println("You have been logged out.");
    }
}



