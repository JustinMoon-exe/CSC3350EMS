package employeemanage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Date;

public class EmployeeDB extends EmployeeData {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "victor1234$$";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            String sql = "INSERT INTO employees (Fname, Lname, email, HireDate, Salary) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getEmail());
            statement.setDate(4, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setDouble(5, employee.getSalary());
            
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating employee failed, no rows affected.");
            }
            
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int empId = generatedKeys.getInt(1);
                    employee.setEmpId(empId); // Set the generated empID in the Employee object
                } else {
                    throw new SQLException("Creating employee failed, no ID obtained.");
                }
            }
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    @Override
    public Employee getEmployee(int empId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Employee employee = null;
        
        try {
            connection = getConnection();
            String sql = "SELECT employees.*, job_titles.job_title, division.Name AS divisionName " +
                    "FROM employees " +
                    "INNER JOIN employee_job_titles ON employees.empid = employee_job_titles.empid " +
                    "INNER JOIN job_titles ON employee_job_titles.job_title_id = job_titles.job_title_id " +
                    "INNER JOIN employee_division ON employees.empid = employee_division.empid " +
                    "INNER JOIN division ON employee_division.div_ID = division.ID " +
                         "WHERE employees.empid = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, empId); // Set the value of the placeholder
            resultSet = statement.executeQuery();
            
            if (resultSet.next()) {
                employee = new Employee(
                    resultSet.getInt("empid"),
                    resultSet.getString("Fname"),
                    resultSet.getString("Lname"),
                    resultSet.getString("email"),
                    resultSet.getString("job_title"),
                    resultSet.getString("divisionName"), // Fetch division name
                    resultSet.getDouble("Salary"),
                    resultSet.getDate("HireDate")
                );
            }
        } finally {
            // Close resources in finally block to ensure they are always closed
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
        
        return employee;
    }



    @Override
    public List<Employee> searchEmployees(String searchTerm) throws SQLException {
        System.out.println("Searching for employees with search term: " + searchTerm);
        
        Connection connection = getConnection();
        String sql = "SELECT employees.*, job_titles.job_title, division.Name AS divisionName " +
                     "FROM employees " +
                     "INNER JOIN employee_job_titles ON employees.empid = employee_job_titles.empid " +
                     "INNER JOIN job_titles ON employee_job_titles.job_title_id = job_titles.job_title_id " +
                     "INNER JOIN employee_division ON employees.empid = employee_division.empid " +
                     "INNER JOIN division ON employee_division.div_ID = division.ID " +
                     "WHERE Fname LIKE '%" + searchTerm + "%'";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        List<Employee> foundEmployees = new ArrayList<>();
        while (resultSet.next()) {
            foundEmployees.add(new Employee(
                    resultSet.getInt("empid"),
                    resultSet.getString("Fname"),
                    resultSet.getString("Lname"),
                    resultSet.getString("email"),
                    resultSet.getString("job_title"),
                    resultSet.getString("divisionName"),
                    resultSet.getDouble("Salary"),
                    resultSet.getDate("HireDate")));
        }
        resultSet.close();
        statement.close();
        connection.close();
        
        System.out.println("Found " + foundEmployees.size() + " employees matching the search term.");
        
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
            		resultSet.getInt("empid"),
                    resultSet.getString("Fname"),
                    resultSet.getString("Lname"),
                    resultSet.getString("email"),
                    resultSet.getString("jobTitle"),
                    resultSet.getString("Division"),
                    resultSet.getDouble("Salary"),
                    resultSet.getDate("HireDate")));
        }
        resultSet.close();
        statement.close();
        connection.close();
        return allEmployees;
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            String sql = "UPDATE employees SET Fname = ?, Lname = ?, email = ?, Salary = ?, HireDate = ? WHERE empID = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getEmail());
            statement.setDouble(4, employee.getSalary());
            statement.setDate(5, new java.sql.Date(employee.getHireDate().getTime()));
            statement.setInt(6, employee.getEmpId());
            statement.executeUpdate();
        } finally {
            // Close resources in finally block to ensure they are always closed
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }



    @Override
    public void deleteEmployee(int empId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            connection = getConnection();
            String sql = "DELETE FROM employees WHERE empID = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, empId);
            statement.executeUpdate();
        } finally {
            // Close resources in finally block to ensure they are always closed
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}