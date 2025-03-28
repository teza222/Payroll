
public class BasePlusCommissionEmployee extends CommissionEmployee {
    private double baseSalary;

    public BasePlusCommissionEmployee(String name, double commissionRate, double sales, double baseSalary) {
        super(name, commissionRate, sales);
        this.baseSalary = baseSalary;
    }

    @Override
    public double getPaymentAmount() {
        return super.getPaymentAmount() + baseSalary;
    }


}