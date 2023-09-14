class User {
    private String userId;
    private int pin;
    private double balance;

    public User(String userId, int pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
    }

    public String getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer(User recipient, double amount) {
        if (recipient != null && amount > 0 && balance >= amount) {
            balance -= amount;
            recipient.deposit(amount);
            return true;
        }
        return false;
    }
}