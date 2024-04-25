package employeemanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportGenerator {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  // Update for your database driver
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData"; // Update connection string
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "victor1234$$";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public ReportGenerator()
    {
    }

    public void GenerateEmployeeReport(Employee employee)
    {
        System.out.println("Pay History of " + employee.getName());
        for(PayStatement payStatement: employee.getPayHistory())
        {
            System.out.println(payStatement.getPayDetails());
        }

        connection.close();
    }

    public void GenerateJobTitleAverageReport(String jobTitle) throws SQLException 
    {
	Connection connection = getConnection();
        String sql = "SELECT AVG(Salary) AS Average_Salary" +
              "FROM employees e " +
              "INNER JOIN employee_job_titles ej ON e.empid = ej.empid " +
              "INNER JOIN job_titles j ON ej.job_title_id = j.job_title_id " +
              "WHERE j.job_title = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, jobTitle);
        ResultSet resultSet = statement.executeQuery();
	while (resultSet.next()) {
              double averageSalary = resultSet.getDouble("Average_Salary");
              System.out.println("Average Salary: $" + averageSalary);
        }
        resultSet.close();
    }

    public void GenerateDivisionAverageReport(String division) throws SQLException 
    {
	Connection connection = getConnection();
        String sql = "SELECT AVG(Salary) AS Average_Salary" +
              "FROM employees e " +
              "INNER JOIN employee_division ed ON e.empid = ed.empid " +
              "INNER JOIN division d ON ed.div_ID = d.ID " +
              "WHERE d.Name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, division);
        ResultSet resultSet = statement.executeQuery();
	while (resultSet.next()) {
              double averageSalary = resultSet.getDouble("Average_Salary");
              System.out.println("Average Salary: $" + averageSalary);
        }
        resultSet.close();
    }
}
