package employeemanage;

import java.sql.Date;

public class PayStatement 
{
    private Date payDate;
    private double payAmount;

    // Constructors

    public PayStatement() 
    {
        // Default constructor (optional)
    }

    public PayStatement(Date payDate, double payAmount) 
    {
        this.payDate = payDate;
        this.payAmount = payAmount;
    }

    // Getters and Setters

    public Date getPayDate() {
        return payDate;
    }

    public void payDate(Date payDate) 
    {
    	this.payDate = payDate;
    }

    public double getPayAmount() {
        return payAmount;
    }

    public void payDate(double payAmount) 
    {
    	this.payAmount = payAmount;
    }

    // Additional methods (optional)

    public String getPayDetails() 
    {
        return "Payed " + payAmount + " on " + payDate;
    }
}
