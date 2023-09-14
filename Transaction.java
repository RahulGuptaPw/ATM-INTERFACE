import java.text.SimpleDateFormat;
import java.util.*;

class Transaction {
    private String date;
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return date + " - " + type + " $" + amount;
    }
}