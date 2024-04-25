package employeemanage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayrollStatementPrint {

    private EmployeeDB employeeDB; // Assuming you have an EmployeeDB class

    public PayrollStatementPrint(EmployeeDB employeeDB) {
        this.employeeDB = employeeDB;
    }

    public void printPayrollStatement(Employee employee) throws SQLException {
        
        if (employee == null) {
            System.out.println("Error: Employee with ID " + employee.getEmpId() + " not found.");
            return;
        }

        try (Connection connection = employeeDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM payroll WHERE empid = ?")) {
            
            statement.setInt(1, employee.getEmpId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                System.out.println("\nPayroll Statement");
                System.out.println("Employee ID: " + employee.getEmpId());
                System.out.println("Pay Date: " + resultSet.getDate("pay_date"));
                System.out.println("Earnings: $" + resultSet.getDouble("earnings"));
                System.out.println("Federal Tax: $" + resultSet.getDouble("fed_tax"));
                System.out.println("Federal Medicare: $" + resultSet.getDouble("fed_med"));
                System.out.println("Federal Social Security: $" + resultSet.getDouble("fed_SS"));
                System.out.println("State Tax: $" + resultSet.getDouble("state_tax"));
                System.out.println("Retirement (401k): $" + resultSet.getDouble("retire_401k"));
                System.out.println("Healthcare: $" + resultSet.getDouble("health_care"));
            } else {
                System.out.println("Payroll information not found for employee ID " + employee.getEmpId());
            }
        }
    }
}
