package com.example.project3.bank;

/**
 * Money Market Savings account.
 * Minimum opening deposit is $2,000.
 * Annual interest is 3.5% (or 3.75% if loyal).
 * No fee if balance is $2,000 or above; if below, base fee is $15.
 * For withdrawals beyond 3 per statement cycle, a $10 fee is added for each extra withdrawal.
 *
 * @author
 *
 *   Anirudh Deveram
 */
public class MoneyMarket extends Savings
{
    private int withdrawals;

    public MoneyMarket(AccountNumber number, Profile holder, double balance, boolean isLoyal)
    {
        super(number, holder, balance, isLoyal);
        withdrawals = 0;
    }

    public int getWithdrawals()
    {
        return withdrawals;
    }

    public void incrementWithdrawals()
    {
        withdrawals++;
    }

    @Override
    public double interest()
    {
        double rate = isLoyal ? 0.0375 : 0.035;
        return balance * rate / 12;
    }

    @Override
    public double fee()
    {
        // For accounts with balance < 2000, base fee is $15.
        if (balance >= 2000)
        {
            return 0;
        }
        int extraWithdrawals = (withdrawals > 3) ? (withdrawals - 3) : 0;
        return 15 + extraWithdrawals * 10;
    }

    @Override
    public String toString()
    {
        return "MoneyMarket " + super.toString() + " Withdrawals[" + withdrawals + "]";
    }
}
