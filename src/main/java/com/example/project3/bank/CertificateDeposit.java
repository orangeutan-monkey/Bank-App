package com.example.project3.bank;

import com.example.project3.util.Date;
import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * Certificate Deposit (CD) account.
 * A fixed-term deposit with allowed terms of 3, 6, 9, or 12 months.
 * Minimum opening deposit is $1,000. No monthly fee.
 * Monthly interest = balance * (term-specific rate) / 12.
 * When closing, interest is computed over the days from open to close.
 * If closed before maturity, a different rate applies and a penalty (10% of interest) is charged.
 *
 * @author
 *
 *   Anirudh Deveram
 */
public class CertificateDeposit extends Savings
{
    private int term;
    private Date open;

    public CertificateDeposit(AccountNumber number, Profile holder, double balance, int term, Date open)
    {
        super(number, holder, balance, false);
        this.term = term;
        this.open = open;
    }

    public int getTerm()
    {
        return term;
    }

    public Date getOpenDate()
    {
        return open;
    }

    public Date getMaturityDate() {
        Calendar calOpen = Calendar.getInstance();
        calOpen.set(open.year, open.month - 1, open.day, 0, 0, 0);
        calOpen.add(Calendar.MONTH, term);
        
        return new Date(calOpen.get(Calendar.MONTH) + 1, 
                        calOpen.get(Calendar.DAY_OF_MONTH),
                        calOpen.get(Calendar.YEAR));
    }

    // Returns the standard rate based on term.
    private double getStandardRate()
    {
        switch (term)
        {
            case 3:
            {
                return 0.03;
            }
            case 6:
            {
                return 0.0325;
            }
            case 9:
            {
                return 0.035;
            }
            case 12:
            {
                return 0.04;
            }
            default:
            {
                return 0;
            }
        }
    }

    @Override
    public double interest()
    {
        double rate = getStandardRate();
        return balance * rate / 12;
    }

    @Override
    public double fee()
    {
        return 0;
    }

    /**
     * Calculates the closing interest and penalty (if closed before maturity).
     * Returns a formatted string with the interest and, if applicable, a penalty line.
     */
    public String closeAccount(Date closeDate)
    {
        // Compute the difference in days between open and close.
        int days = daysBetween(open, closeDate);
        double standardRate = getStandardRate();
        boolean matured = hasMatured(closeDate);
        double interestEarned;
        double penalty = 0;
        if (matured)
        {
            // If closed on or after maturity, use standard rate.
            interestEarned = balance * standardRate / 365 * days;
        }
        else
        {
            // Closed before maturity: apply alternate rate based on elapsed months.
            int monthsElapsed = days / 30;
            double altRate;
            if (monthsElapsed <= 6)
            {
                altRate = 0.03;
            }
            else if (monthsElapsed <= 9)
            {
                altRate = 0.0325;
            }
            else
            {
                altRate = 0.035;
            }
            interestEarned = balance * altRate / 365 * days;
            penalty = interestEarned * 0.10;
        }
        DecimalFormat df = new DecimalFormat("0.00");
        String result = "--interest earned: $" + df.format(interestEarned);
        if (penalty > 0)
        {
            result += "\n  [penalty] $" + df.format(penalty);
        }
        return result;
    }

    /**
     * Returns true if the CD has reached maturity.
     * Maturity date is computed as open date plus term months.
     */
    private boolean hasMatured(Date closeDate)
    {
        Calendar calOpen = Calendar.getInstance();
        calOpen.set(open.year, open.month - 1, open.day, 0, 0, 0);
        Calendar calClose = Calendar.getInstance();
        calClose.set(closeDate.year, closeDate.month - 1, closeDate.day, 0, 0, 0);
        calOpen.add(Calendar.MONTH, term);
        return !calClose.before(calOpen);
    }

    /**
     * Approximates the number of days between two dates.
     * (This uses a simple approximation assuming 30 days per month.)
     */
    private int daysBetween(Date start, Date end)
    {
        int monthsDiff = (end.year - start.year) * 12 + (end.month - start.month);
        int daysDiff = monthsDiff * 30 + (end.day - start.day);
        return daysDiff;
    }

    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("0.00");
        Date maturityDate = getMaturityDate();
        return "CD Account#[" + number.toString() + "] Holder[" + holder.toString() +
                "] Balance[$" + df.format(balance) + "] Branch[" + number.getBranch().name() + 
                "] Term[" + term + "] Date opened[" + open.toString() + 
                "] Maturity date[" + maturityDate.toString() + "]";
    }
}