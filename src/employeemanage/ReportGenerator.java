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

    public void GenerateJobTitleAverageReport() throws SQLException 
    {
    }

    public void GenerateDivisionAverageReport() throws SQLException 
    {
    }
}
