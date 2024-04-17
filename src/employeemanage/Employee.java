package employeemanage;

import java.sql.Date;

public class Employee {

    private int empId; // Unique identifier (consider auto-increment for database)
    private String name;
    private String SSN; // Consider encryption for sensitive data
    private String jobTitle;
    private String division;
    private double salary;
    private Date hireDate;

    // Constructors

    public Employee() {
        // Default constructor (optional)
    }

    public Employee(int empId, String name, String SSN, String jobTitle, String division, double salary, Date hireDate) {
        this.empId = empId;
        this.name = name;
        this.SSN = SSN; // Consider encryption for security
        this.jobTitle = jobTitle;
        this.division = division;
        this.salary = salary;
        this.hireDate = hireDate;
    }

    // Getters and Setters

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSSN() {
        return SSN;
    }

    public void setSSN(String SSN) {
        this.SSN = SSN; // Consider encryption for security
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    // Additional methods (optional)

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", SSN='" + SSN + '\'' + // Consider masking for security
                ", jobTitle='" + jobTitle + '\'' +
                ", division='" + division + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }
}

