package com.example.project3.bank;

/**
 * College Checking account.
 * Extends Checking by including a campus code. No monthly fee is charged.
 * 
 * @author 
 *
 * Anirudh Deveram
 */
public class CollegeChecking extends Checking
{
    private Campus campus;

    public CollegeChecking(AccountNumber number, Profile holder, double balance, Campus campus)
    {
        super(number, holder, balance);
        this.campus = campus;
    }

    public Campus getCampus()
    {
        return campus;
    }

    @Override
    public double fee()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return "College Checking " + super.toString() + " Campus[" + campus.toString() + "]";
    }
}
