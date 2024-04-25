package employeemanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTester {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // Update for your database driver
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData"; // Update connection string
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "victor1234$$";

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            String searchTerm = "Snoopy";
            String sql = "SELECT employees.*, job_titles.job_title, division.Name AS divisionName " +
                         "FROM employees " +
                         "INNER JOIN employee_job_titles ON employees.empid = employee_job_titles.empid " +
                         "INNER JOIN job_titles ON employee_job_titles.job_title_id = job_titles.job_title_id " +
                         "INNER JOIN employee_division ON employees.empid = employee_division.empid " +
                         "INNER JOIN division ON employee_division.div_ID = division.ID " +
                         "WHERE Fname LIKE ?";
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + searchTerm + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Retrieve and print employee data
                int empId = resultSet.getInt("empid");
                String firstName = resultSet.getString("Fname");
                String lastName = resultSet.getString("Lname");
                String email = resultSet.getString("email");
                String jobTitle = resultSet.getString("job_title");
                String divisionName = resultSet.getString("divisionName");
                double salary = resultSet.getDouble("Salary");
                System.out.println("Employee ID: " + empId + ", Name: " + firstName + " " + lastName +
                                   ", Email: " + email + ", Job Title: " + jobTitle +
                                   ", Division: " + divisionName + ", Salary: " + salary);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
