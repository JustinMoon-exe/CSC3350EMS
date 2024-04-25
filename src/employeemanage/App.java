package employeemanage;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.sql.Date;


public class App {

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  // Update for your database driver
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employeeData"; // Update connection string
    private static final String USERNAME = "root";
    private static final String PASSWORD = "victor1234$$";
    private static Scanner scanner = new Scanner(System.in);
    private static EmployeeDB employeeDB = new EmployeeDB(); // Use EmployeeDB class
    
    private static SalaryAdjuster salaryAdjuster = new SalaryAdjuster(); // Use SalaryAdjuster class
    private static PayrollStatementPrint payrollStatementPrint = new PayrollStatementPrint(employeeDB); // Create an instance of PayrollStatementPrint

    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add New Employee");
            System.out.println("2. Search Employees by Name");
            System.out.println("3. Update Employee Data");
            System.out.println("4. Adjust Salaries");
            System.out.println("5. Print Payroll");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after reading the choice

            try {
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        searchEmployees();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        adjustSalary();
                        break;
                    case 5:
                    	printEmployeePayrollStatement();
                        break;
                    case 6:
                        System.out.println("Exiting the system...");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addEmployee() {
        int empID = 0;
        String empIdStr = getDataInput("Enter employee ID: ");
        while (empIdStr.isEmpty()) {
            System.out.println("Error: Employee ID cannot be empty. Please enter a valid ID.");
            empIdStr = getDataInput("Enter employee ID: ");
        }
        empID = Integer.parseInt(empIdStr);
        String firstName = getDataInput("Enter employee first name: ");
        String lastName = getDataInput("Enter employee last name: ");
        String email = getDataInput("Enter employee email: ");
        String jobTitle = getDataInput("Enter job ID: ");
        String Division = getDataInput("Enter Division: ");
        double salary = Double.parseDouble(getDataInput("Enter salary: "));

        try {
            Employee employee = new Employee(empID, firstName, lastName, email, jobTitle, Division, salary, new java.sql.Date(new java.util.Date().getTime()));
            employeeDB.addEmployee(employee);
            System.out.println("Employee added successfully!");
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
        }
    }
    
    private static void searchEmployees() {
        System.out.print("Enter search term (name): ");
        String searchTerm = scanner.nextLine();
        try {
            List<Employee> foundEmployees = employeeDB.searchEmployees(searchTerm);
            if (foundEmployees.isEmpty()) {
                System.out.println("No employees found with that name.");
            } else {
                System.out.println("\nSearch Results:");
                for (Employee employee : foundEmployees) {
                    System.out.println(employee); // Utilize toString() method of Employee class
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching employees: " + e.getMessage());
        }
    }
    
    private static void updateEmployee() throws SQLException {
        System.out.print("Enter employee ID to update: ");
        int empId = scanner.nextInt();

        Employee employee = employeeDB.getEmployee(empId);
        if (employee == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.println("Employee Details:");
        System.out.println(employee); // Utilize toString() method of Employee class (if implemented)

        System.out.println("\nWhat do you want to update?");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Email");
        System.out.println("4. Job Title");
        System.out.println("5. Salary");
        System.out.println("6. Cancel Update");
        System.out.print("Enter your choice: ");
        int updateChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (updateChoice) {
            case 1:
                updateEmployeeFirstName(employee);
                break;
            // other cases...
        }
        // Debug statement to check if the employee object is updated
        System.out.println("After updating employee:");
        System.out.println(employee);
    }


    private static void updateEmployeeFirstName(Employee employee) {
        System.out.println("Before getting input"); // Debug statement
        String newFirstName = getDataInput("Enter new first name: ");
        System.out.println("After getting input"); // Debug statement

        employee.setFirstName(newFirstName);
        try {
            employeeDB.updateEmployee(employee);
            System.out.println("Employee first name updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee first name: " + e.getMessage());
        }
    }




    private static void updateEmployeeLastName(Employee employee) {
        String newLastName = getDataInput("Enter new last name: ");
        employee.setLastName(newLastName);
        try {
            employeeDB.updateEmployee(employee);
            System.out.println("Employee last name updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee last name: " + e.getMessage());
        }
    }

    private static void updateEmployeeEmail(Employee employee) {
        String newEmail = getDataInput("Enter new email: ");
        employee.setEmail(newEmail);
        try {
            employeeDB.updateEmployee(employee);
            System.out.println("Employee email updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee email: " + e.getMessage());
        }
    }
    
    private static void updateEmployeeSalary(Employee employee) { // Update salary
        String salaryInput = getDataInput("Enter new salary: $");
        System.out.println("Debug: Input salary string: " + salaryInput); // Debug statement
        double newSalary;
        try {
            newSalary = Double.parseDouble(salaryInput);
            if (newSalary < 0) {
                System.out.println("Error: Salary cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid salary format. Please enter a number.");
            return;
        }

        employee.setSalary(newSalary);
        try {
            employeeDB.updateEmployee(employee);
            System.out.println("Employee salary updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee salary: " + e.getMessage());
        }
    }
    
    private static void printEmployeePayrollStatement() {
        System.out.print("Enter employee ID to print payroll statement: ");
        int empId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        try {
            Employee employee = employeeDB.getEmployee(empId);
            payrollStatementPrint.printPayrollStatement(employee);
        } catch (SQLException e) {
            System.err.println("Error printing payroll statement: " + e.getMessage());
        }
    }


    private static void updateEmployeeJobTitle(Employee employee) {
        String newJobTitle = getDataInput("Enter new job title: ");
        employee.setJobTitle(newJobTitle);
        try {
            employeeDB.updateEmployee(employee);
            System.out.println("Employee job title updated successfully!");
        } catch (SQLException e) {
            System.err.println("Error updating employee job title: " + e.getMessage());
        }
    }


    
    private static String getDataInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static void adjustSalary() throws SQLException { // Adjust
        System.out.print("Enter the target percentage to adjust by: ");
        double adjustPercentage = scanner.nextDouble();
	System.out.print("Enter minimum and maximum salaries to target for adjustment: ");
        double minSalary = scanner.nextDouble();
        double maxSalary = scanner.nextDouble();

	System.out.print("Confirm? Y/N");
	boolean adjust = scanner.next().equalsIgnoreCase("Y");

	if(adjust)
	{
	    System.out.println("Adjusting salaries");
	    salaryAdjuster.adjustSalaryByRange(minSalary, maxSalary, adjustPercentage);
	}
	else
	{
	    System.out.println("Cancelling adjustment");
	}
    }
    		
//  Connection connection = null;
//  Statement statement = null;
//
//  try {
//      // Register JDBC driver
//      Class.forName(JDBC_DRIVER);
//
//      // Open a connection
//      connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//
//      // Create a statement
//      statement = connection.createStatement();
//
//      // ** Query 1: Average Salary of all Employees **
//      String query1 = "SELECT AVG(Salary) AS Average_Salary FROM employees;";
//      ResultSet resultSet1 = statement.executeQuery(query1);
//      while (resultSet1.next()) {
//          double averageSalary = resultSet1.getDouble("Average_Salary");
//          System.out.println("Average Salary: $" + averageSalary);
//      }
//      resultSet1.close();
//
//      // ** Query 2: Employees in a specific division ** (Replace 'Marketing' with desired name)
//      String query2 = "SELECT e.Fname, e.Lname " +
//              "FROM employees e " +
//              "INNER JOIN employee_division ed ON e.empid = ed.empid " +
//              "INNER JOIN division d ON ed.div_ID = d.ID " +
//              "WHERE d.Name = 'Marketing';";
//      ResultSet resultSet2 = statement.executeQuery(query2);
//      while (resultSet2.next()) {
//          String firstName = resultSet2.getString("Fname");
//          String lastName = resultSet2.getString("Lname");
//          System.out.println("Employee: " + firstName + " " + lastName);
//      }
//      resultSet2.close();
//
//      // Implement similar logic for queries 3, 4, and 5 (update query strings and result handling)
//
//  } catch (ClassNotFoundException e) {
//      e.printStackTrace();
//  } catch (SQLException e) {
//      e.printStackTrace();
//  } finally {
//      // Close resources
//      if (statement != null) {
//          try {
//              statement.close();
//          } catch (SQLException e) {
//              e.printStackTrace();
//          }
//      }
//      if (connection != null) {
//          try {
//              connection.close();
//          } catch (SQLException e) {
//              e.printStackTrace();
//          }
//      }
//  }
    

}
