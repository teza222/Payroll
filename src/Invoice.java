import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class Invoice implements Payable {
    private String contractorName;
    private double amount;

    public Invoice(String contractorName, double amount) {
        this.contractorName = contractorName;
        this.amount = amount;
    }

    @Override
    public double getPaymentAmount() {
        return amount;
    }

    @Override
    public void writeToFile() throws IOException {
        String content = String.format("Contractor Name: %s\nPayment Amount: $%.2f\nDate: %s\n\n",
                contractorName, getPaymentAmount(), LocalDateTime.now());
         try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing paystub for " + "paystub.txt");
        }
    }
}