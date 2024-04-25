package employeemanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalaryAdjuster {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  // Update for your database driver
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData"; // Update connection string
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "victor1234$$";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public SalaryAdjuster()
    {
    }

    public void adjustSalaryByRange(double salaryMin, double salaryMax, double adjustmentPercent) throws SQLException {
        Connection connection = getConnection();
        String sql = "SELECT * FROM employees WHERE salary BETWEEN salaryMin AND salaryMax";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Employee> targetEmployees = new ArrayList<>();
        while (resultSet.next()) {
            targetEmployees.add(new Employee(
                    resultSet.getInt("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("SSN"), // Consider masking for security
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary"),
                    resultSet.getDate("hire_date")));
        }

        for(Employee employee: targetEmployees)
        {
            sql = "UPDATE employees SET salary = ? WHERE emp_id = ?";
            statement = connection.prepareStatement(sql);
            statement.setDouble(1, employee.getSalary() * (1 + adjustmentPercent));
            statement.setInt(2, employee.getEmpId());
            statement.executeUpdate();
            statement.close();
        }

        connection.close();
    }
}
