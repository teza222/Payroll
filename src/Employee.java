import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public abstract class Employee implements Payable {
    private String name;

    public Employee(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void writeToFile() throws IOException {
        String content = String.format("Employee Name: %s\nPayment Amount: $%.2f\nDate: %s\n\n",
                getName(), getPaymentAmount(), LocalDateTime.now());
        try (FileWriter writer = new FileWriter("paystub.txt", true)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Error writing paystub for " + "paystub.txt");
        }
        
    }
}