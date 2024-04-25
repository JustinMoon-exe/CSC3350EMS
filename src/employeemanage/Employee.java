package employeemanage;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Employee {

    private int empId; // Unique identifier (consider auto-increment for database)
    private String fname;
    private String lname;
    private String email;
    private String SSN; // Consider encryption for sensitive data
    private String jobTitle;
    private String division;
    private double salary;
    private Date hireDate;
    private List<PayStatement> payHistory = new ArrayList();

    // Constructors

    public Employee() {
        // Default constructor (optional)
    }

    public Employee(int empId, String fname, String lname, String email, String jobTitle, String division, double salary, Date hireDate) {
        this.empId = empId;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
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
    
    public void setFirstName(String fname) {
    	this.fname = fname;
    }
    
    public String getFirstName() {
    	return fname;
    }
    
    public void setLastName(String lname) {
    	this.lname = lname;
    }
    
    public String getLastName() {
    	return lname;
    }
    
    public void setName(String name) {
    	
    }
    
    public String getName() {
    	return(fname + " " + lname);
    }
    
    public void setEmail(String email) {
    	this.email = email;
    }
    
    public String getEmail() {
    	return email;
    }
    


    public List<PayStatement> getPayHistory() {
        return payHistory;
    }

    // Additional methods (optional)

    public void addPayStatement(PayStatement payStatement) 
    {
        payHistory.add(payStatement);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", division='" + division + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                '}';
    }
}

