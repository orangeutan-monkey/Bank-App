package com.example.project3.bank;

/**
 * Regular Checking account.
 * Monthly interest = balance * 1.5% / 12.
 * No fee if balance is $1,000 or above; otherwise, a fee of $15 is charged.
 * 
 * @author 
 *
 *   Anirudh Deveram
 */
public class Checking extends Account
{
    public Checking(AccountNumber number, Profile holder, double balance)
    {
        super(number, holder, balance);
    }

    @Override
    public double interest()
    {
        return balance * 0.015 / 12;
    }

    @Override
    public double fee()
    {
        return (balance >= 1000) ? 0 : 15;
    }
}
