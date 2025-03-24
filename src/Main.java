//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    interface Payable{
        public double getPaymentAmount();
    }

    class Employee implements Payable {
        private String firstname;
        private String lastName;
        private String socialSecurityNumber;

        @Override
        public double getPaymentAmount() {
            return 0;
        }
    }

    class Invoice implements Payable{
        private String partNumber;
        private String partDescription;
        private int quantity;
        private double pricePerItem;

        @Override
        public double getPaymentAmount() {
            return 0;
        }
    }

    public static void main(String[] args) {


    }
}