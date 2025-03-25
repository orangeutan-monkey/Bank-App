package com.example.project3.bank;

import com.example.project3.util.Date;

/**
 * Represents an account activity – either a deposit ('D') or withdrawal ('W').
 * 
 * @author
 * Anirudh Deveram
 */
public class Activity implements Comparable<Activity>
{
    private Date date;
    private Branch location;
    private char type;
    private double amount;
    private boolean atm;

    public Activity(Date date, Branch location, char type, double amount, boolean atm)
    {
        this.date = date;
        this.location = location;
        this.type = type;
        this.amount = amount;
        this.atm = atm;
    }

    public Date getDate()
    {
        return date;
    }

    public Branch getLocation()
    {
        return location;
    }

    public char getType()
    {
        return type;
    }

    public double getAmount()
    {
        return amount;
    }

    public boolean isAtm()
    {
        return atm;
    }

    @Override
    public int compareTo(Activity other)
    {
        return this.date.compareTo(other.date);
    }

    @Override
    public String toString()
    {
        return "Activity[" + type + " $" + amount + " on " + date.toString() +
               " at " + location.name() + (atm ? " ATM" : "") + "]";
    }
}
