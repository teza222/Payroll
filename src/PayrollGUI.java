import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**Group Members*************************************************************************************************
 * 
 * Everton Richards
 * Asheka Quallo
 * Adrian Williams
 * Andre Mcmillon
 * 
 ****************************************************************************************************************/

public class PayrollGUI extends JFrame {
    private DefaultTableModel tableModel;
    private JTable employeeTable;
    private List<Payable> payableList;
    
    public PayrollGUI() {
        payableList = new ArrayList<>();
        setTitle("Employee Payroll System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Hourly", "Salaried", "Commission", "BasePlusCommission", "Contractor"});
        JTextField nameField = new JTextField();
        JTextField hourlyRateField = new JTextField();
        JTextField hoursWorkedField = new JTextField();
        JTextField baseSalaryField = new JTextField();
        JButton addBtn = new JButton("Add");
       
    

        inputPanel.add(new JLabel("Employee Type:"));
        inputPanel.add(typeCombo);
        JLabel nameLabel = new JLabel("Employee Name:");
        inputPanel.add(nameLabel);
      
        inputPanel.add(nameField);
        JLabel hourlyRateLabel = new JLabel("Hourly Rate:");
        inputPanel.add(hourlyRateLabel);
        inputPanel.add(hourlyRateField);
        JLabel hoursWorkedLabel = new JLabel("Hours Worked:");
        inputPanel.add(hoursWorkedLabel);
        inputPanel.add(hoursWorkedField);
        JLabel baseSalaryLabel = new JLabel("Base Salary:");
        inputPanel.add(baseSalaryLabel);
        
        inputPanel.add(baseSalaryField);
        inputPanel.add(addBtn);

  
        baseSalaryField.setVisible(false);
        baseSalaryLabel.setVisible(false);

        // create the table
        tableModel = new DefaultTableModel(new Object[]{"Name", "Type", "Payment"}, 0);
        employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Buttons Panel
        JPanel btnPanel = new JPanel();
        JButton removeBtn = new JButton("Remove Selected");
        JButton generateBtn = new JButton("Generate Pay Stubs");

        btnPanel.add(removeBtn);
        btnPanel.add(generateBtn);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        typeCombo.addActionListener(e -> {
            String selectedType = (String) typeCombo.getSelectedItem();
            if ("Salaried".equals(selectedType)) {
                nameLabel.setText("Employee Name:");
                hourlyRateField.setVisible(true);
                hoursWorkedField.setVisible(false);
                hourlyRateLabel.setText("Salary:");
                hoursWorkedLabel.setText("");
                baseSalaryField.setVisible(false);
                baseSalaryLabel.setVisible(false);
            } else if ("Commission".equals(selectedType)) {
                nameLabel.setText("Employee Name:");
                hourlyRateLabel.setText("Commission Rate:");
                hourlyRateField.setVisible(true);
                hoursWorkedField.setVisible(true);
                hoursWorkedLabel.setText("Sales Amount:");
                baseSalaryField.setVisible(false);
                baseSalaryLabel.setVisible(false);

            } else if ("BasePlusCommission".equals(selectedType)) {
                nameLabel.setText("Employee Name:");
                hourlyRateLabel.setText("Commission Rate:");
                hourlyRateField.setVisible(true);
                hoursWorkedField.setVisible(true);
                hoursWorkedLabel.setText("Sales Amount:");
                baseSalaryField.setVisible(true);
                baseSalaryLabel.setVisible(true);
            } else if ("Hourly".equals(selectedType)) {
                nameLabel.setText("Employee Name:");
                hourlyRateLabel.setText(selectedType + "Hourly Rate:");
                hourlyRateField.setVisible(true);
                hoursWorkedField.setVisible(true);
                hoursWorkedLabel.setText("Hours Worked:");
                hourlyRateLabel.setText("Hourly Rate:");
                baseSalaryField.setVisible(false);
                baseSalaryLabel.setVisible(false);
            } else{
                nameLabel.setText("Contractor Name:");
                hourlyRateLabel.setText("Payment Amount:");
                hourlyRateField.setVisible(true);
                hoursWorkedField.setVisible(false);
                baseSalaryField.setVisible(false);
                baseSalaryLabel.setVisible(false);
                hoursWorkedLabel.setText("");
            }
        });
   
        // do different calculations based on the type of employee
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    String type = (String) typeCombo.getSelectedItem();
                    String name = nameField.getText();
                    double hourlyRate = Double.parseDouble(hourlyRateField.getText());
                    
                    //throw custom exception if name is empty or hourly rate is negative
                    if (name.isEmpty()){
                        throw new CustomException("Name cannot be empty");
                    }
                    
                    if (hourlyRate < 0 ){
                        throw new CustomException("Hourly rate cannot be negative");
                    }
                   
                
                    Payable payable;
                    switch (type) {
                        case "Hourly":
                            if(Double.parseDouble(hoursWorkedField.getText()) < 0){
                                throw new CustomException("Hours worked cannot be negative");
                            }
                            payable = new HourlyEmployee(name, hourlyRate, Double.parseDouble(hoursWorkedField.getText()));
                            break;
                        case "Salaried":
                            payable = new SalariedEmployee(name, hourlyRate);
                            break;
                        case "Commission":
                        if(Double.parseDouble(hoursWorkedField.getText()) < 0){
                            throw new CustomException("Hours worked cannot be negative");
                        }
                            payable = new CommissionEmployee(name, hourlyRate, Double.parseDouble(hoursWorkedField.getText()));
                            break;
                        case "BasePlusCommission":
                        if(Double.parseDouble(hoursWorkedField.getText()) < 0){
                            throw new CustomException("Hours worked cannot be negative");
                        }
                            payable = new BasePlusCommissionEmployee(name, hourlyRate, Double.parseDouble(hoursWorkedField.getText()), Double.parseDouble(baseSalaryField.getText())); 
                            break;
                        case "Contractor":
                            payable = new Invoice(name, hourlyRate);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid type");
                    }

                    payableList.add(payable);
                    tableModel.addRow(new Object[]{name, type, payable.getPaymentAmount()});
                    clearFields();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input format!");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                } catch (CustomException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                                }
            }
        

            private void clearFields() {
                nameField.setText("");
                hourlyRateField.setText("");
                hoursWorkedField.setText("");
            }
    });

        removeBtn.addActionListener(e -> {
            int selectedRow = employeeTable.getSelectedRow();
            if (selectedRow >= 0) {
                tableModel.removeRow(selectedRow);
                payableList.remove(selectedRow);
            }
        });

        generateBtn.addActionListener(e -> {
            try {
                for (Payable payable : payableList) {
                    payable.writeToFile();
                }
                JOptionPane.showMessageDialog(null, "Pay stubs generated successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error writing to file!");
            }
        });
    }

    public static void main(String[] args) {
        new PayrollGUI().setVisible(true);
    }
}