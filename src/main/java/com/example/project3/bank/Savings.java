package com.example.project3.bank;

/**
 * Regular Savings account.
 * Annual interest rate is 2.5%, increased to 2.75% if the customer is loyal.
 * A fee of $25 is charged if the balance is below $500; otherwise, no fee.
 * 
 * @author 
 *
 *   Anirudh Deveram
 */
public class Savings extends Account
{
    protected boolean isLoyal;

    public Savings(AccountNumber number, Profile holder, double balance, boolean isLoyal)
    {
        super(number, holder, balance);
        this.isLoyal = isLoyal;
    }

    @Override
    public double interest()
    {
        double rate = isLoyal ? 0.0275 : 0.025;
        return balance * rate / 12;
    }

    @Override
    public double fee()
    {
        return (balance >= 500) ? 0 : 25;
    }

    public boolean getLoyalStatus()
    {
        return isLoyal;
    }

    public void setLoyalStatus(boolean status)
    {
        isLoyal = status;
    }

    @Override
    public String toString()
    {
        return "Savings " + super.toString() + (isLoyal ? " Loyal" : "");
    }
}
