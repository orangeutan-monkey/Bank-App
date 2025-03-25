package com.example.project3.bank;

/**
 * AccountNumber creates the bank account number based on branch, account type,
 * and the fixed sequence provided by the professor’s Random class.
 *
 * @author
 * Anirudh Deveram
 */
public class AccountNumber implements Comparable<AccountNumber>
{
    private static final int DUMMY_SEED = 9999;
    private static Random randomGenerator = new Random(DUMMY_SEED);

    private Branch branch;
    private AccountType type;
    private String number;

    public AccountNumber(Branch branch, AccountType type)
    {
        this.branch = branch;
        this.type = type;
        int nextNum = randomGenerator.nextInt(DUMMY_SEED);
        this.number = String.format("%04d", nextNum);
    }

    public Branch getBranch()
    {
        return branch;
    }

    public AccountType getType()
    {
        return type;
    }

    public void setType(AccountType newType)
    {
        this.type = newType;
    }

    @Override
    public int compareTo(AccountNumber other)
    {
        return this.toString().compareTo(other.toString());
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof AccountNumber)
        {
            return this.toString().equals(obj.toString());
        }
        return false;
    }

    @Override
    public String toString()
    {
        return branch.getBranchCode() + type.getCode() + number;
    }
}
