
public class CommissionEmployee extends Employee {
    private double commissionRate;
    private double sales;

    public CommissionEmployee(String name, double commissionRate, double sales) {
        super(name);
        this.commissionRate = commissionRate;
        this.sales = sales;
    }

    @Override
    public double getPaymentAmount() {
        return commissionRate * sales;
    }

}