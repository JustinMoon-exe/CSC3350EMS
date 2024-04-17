package employeemanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDB extends EmployeeData {

	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  // Update for your database driver
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData"; // Update connection string
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "victor1234$$";
    
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        Connection connection = getConnection();
        String sql = "INSERT INTO employees (name, SSN, job_title, division, salary, hire_date) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSSN()); // Consider encryption for sensitive data
        statement.setString(3, employee.getJobTitle());
        statement.setString(4, employee.getDivision());
        statement.setDouble(5, employee.getSalary());
        statement.setDate(6, new java.sql.Date(employee.getHireDate().getTime())); // Convert to SQL Date
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @Override
    public Employee getEmployee(int empId) throws SQLException {
        Connection connection = getConnection();
        String sql = "SELECT * FROM employees WHERE emp_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, empId);
        ResultSet resultSet = statement.executeQuery();
        Employee employee = null;
        if (resultSet.next()) {
            employee = new Employee(
                    resultSet.getInt("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("SSN"), // Consider masking for security
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary"),
                    resultSet.getDate("hire_date"));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return employee;
    }

    @Override
    public List<Employee> searchEmployees(String searchTerm) throws SQLException {
        Connection connection = getConnection();
        String sql = "SELECT * FROM employees WHERE name LIKE ? OR job_title LIKE ? OR division LIKE ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + searchTerm + "%"); // Add wildcards for partial matches
        statement.setString(2, "%" + searchTerm + "%");
        statement.setString(3, "%" + searchTerm + "%");
        ResultSet resultSet = statement.executeQuery();
        List<Employee> foundEmployees = new ArrayList<>();
        while (resultSet.next()) {
            foundEmployees.add(new Employee(
                    resultSet.getInt("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("SSN"), // Consider masking for security
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary"),
                    resultSet.getDate("hire_date")));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return foundEmployees;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        Connection connection = getConnection();
        String sql = "SELECT * FROM employees";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Employee> allEmployees = new ArrayList<>();
        while (resultSet.next()) {
            allEmployees.add(new Employee(
                    resultSet.getInt("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("SSN"), // Consider masking for security
                    resultSet.getString("job_title"),
                    resultSet.getString("division"),
                    resultSet.getDouble("salary"),
                    resultSet.getDate("hire_date")));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return allEmployees;
    }
    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        Connection connection = getConnection();
        String sql = "UPDATE employees SET name = ?, SSN = ?, job_title = ?, division = ?, salary = ?, hire_date = ? WHERE emp_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, employee.getName());
        statement.setString(2, employee.getSSN()); // Consider encryption for sensitive data
        statement.setString(3, employee.getJobTitle());
        statement.setString(4, employee.getDivision());
        statement.setDouble(5, employee.getSalary());
        statement.setDate(6, new java.sql.Date(employee.getHireDate().getTime())); // Convert to SQL Date
        statement.setInt(7, employee.getEmpId()); // Set employee ID for WHERE clause
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    @Override
    public void deleteEmployee(int empId) throws SQLException {
        Connection connection = getConnection();
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, empId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }
    
    
}
