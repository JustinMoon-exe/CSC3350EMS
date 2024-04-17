package employeemanage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeList extends EmployeeData {

    private List<Employee> employeeList = new ArrayList<>();

    @Override
    public void addEmployee(Employee employee) throws SQLException {
        // No SQL for in-memory storage, directly add to list
        employeeList.add(employee);
    }

    @Override
    public Employee getEmployee(int empId) throws SQLException {
        for (Employee employee : employeeList) {
            if (employee.getEmpId() == empId) {
                return employee;
            }
        }
        return null;
    }

    @Override
    public List<Employee> searchEmployees(String searchTerm) throws SQLException {
        List<Employee> foundEmployees = new ArrayList<>();
        for (Employee employee : employeeList) {
            if (employee.getName().contains(searchTerm) ||
                    employee.getJobTitle().contains(searchTerm) ||
                    employee.getDivision().contains(searchTerm)) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    @Override
    public List<Employee> getAllEmployees() throws SQLException {
        return new ArrayList<>(employeeList); // Return a copy to prevent accidental modification
    }

    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        // Find the employee and update its details
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmpId() == employee.getEmpId()) {
                employeeList.set(i, employee); // Replace existing employee object with updated one
                break;
            }
        }
    }

    @Override
    public void deleteEmployee(int empId) throws SQLException {
        // Find the employee by ID and remove from the list
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getEmpId() == empId) {
                employeeList.remove(i);
                break;
            }
        }
    }
}

