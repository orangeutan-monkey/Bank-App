package com.example.project3.bank;

import com.example.project3.util.List;
import java.text.DecimalFormat;

/**
 * Abstract Account class for Bank.
 * Implements the Template Method design pattern for generating account statements.
 * Stores the account number, account holder profile, balance, and a list of account activities.
 * Subclasses must implement the abstract methods interest() and fee().
 *
 * @author
 * Anirudh Deveram
 */
public abstract class Account implements Comparable<Account>
{
    protected AccountNumber number;
    protected Profile holder;
    protected double balance;
    protected List<Activity> activities;

    public Account(AccountNumber number, Profile holder, double balance)
    {
        this.number = number;
        this.holder = holder;
        this.balance = balance;
        this.activities = new List<Activity>();
    }

    public AccountNumber getNumber()
    {
        return number;
    }

    public Profile getHolder()
    {
        return holder;
    }

    public double getBalance()
    {
        return balance;
    }

    public void deposit(double amount)
    {
        balance += amount;
    }

    public void withdraw(double amount)
    {
        balance -= amount;
    }

    public void addActivity(Activity activity)
    {
        activities.add(activity);
    }

    /**
     * Template method for printing the account statement.
     * DO NOT modify.
     */
    public final void statement()
    {
        printActivities();
        double interest = interest();
        double fee = fee();
        printInterestFee(interest, fee);
        printBalance(interest, fee);
    }

    private void printActivities()
    {
        System.out.println("Account Activities for " + number.toString() + ":");
        for (int i = 0; i < activities.size(); i++)
        {
            System.out.println(activities.get(i).toString());
        }
    }

    private void printInterestFee(double interest, double fee)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        System.out.println("Interest: $" + df.format(interest) + ", Fee: $" + df.format(fee));
    }

    private void printBalance(double interest, double fee)
    {
        DecimalFormat df = new DecimalFormat("0.00");
        double newBalance = balance + interest - fee;
        System.out.println("Updated Balance: $" + df.format(newBalance));
    }

    // Subclasses must implement these:
    public abstract double interest();
    public abstract double fee();

    @Override
    public int compareTo(Account other)
    {
        return this.number.toString().compareTo(other.number.toString());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Account)
        {
            Account other = (Account)obj;
            return this.number.equals(other.number);
        }
        return false;
    }

    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        return "Account#[" + number.toString() + "] Holder[" + holder.toString() +
                "] Balance[$" + df.format(balance) + "]";
    }
}
