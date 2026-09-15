import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private String type;
    private String details;
    private double amount;
    private LocalDateTime dateTime;

    public Transaction(String type, String details, double amount) {
        this.type = type;
        this.details = details;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return dateTime.format(formatter)
                + " | " + type
                + " | " + details
                + " | Amount: ₹" + amount;
    }
}
