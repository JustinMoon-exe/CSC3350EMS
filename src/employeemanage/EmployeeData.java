package employeemanage;

import java.sql.SQLException;
import java.util.List;

public abstract class EmployeeData {

    public abstract void addEmployee(Employee employee) throws SQLException;

    public abstract Employee getEmployee(int empId) throws SQLException;

    public abstract List<Employee> searchEmployees(String searchTerm) throws SQLException;

    public abstract List<Employee> getAllEmployees() throws SQLException;

    public abstract void updateEmployee(Employee employee) throws SQLException;

    public abstract void deleteEmployee(int empId) throws SQLException;
}

