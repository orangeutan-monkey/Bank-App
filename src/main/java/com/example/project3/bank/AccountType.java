package com.example.project3.bank;

/**
 * Enum for account types offered by Bank.
 * Additional types: COLLEGE_CHECKING ("04") and CD ("05").
 * 
 * @author
 * Anirudh Deveram
 */
public enum AccountType
{
    CHECKING("01"),
    SAVINGS("02"),
    MONEY_MARKET("03"),
    COLLEGE_CHECKING("04"),
    CD("05");

    private String code;

    AccountType(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }

    public static AccountType fromString(String typeStr)
    {
        if (typeStr == null)
        {
            return null;
        }
        String s = typeStr.trim().toLowerCase();
        if (s.equals("checking"))
        {
            return CHECKING;
        }
        if (s.equals("savings"))
        {
            return SAVINGS;
        }
        if (s.equals("moneymarket") || s.equals("money market"))
        {
            return MONEY_MARKET;
        }
        if (s.equals("college") || s.equals("college checking"))
        {
            return COLLEGE_CHECKING;
        }
        if (s.equals("certificate") || s.equals("cd") || s.equals("certificate deposit"))
        {
            return CD;
        }
        return null;
    }

    @Override
    public String toString()
    {
        return this.name();
    }
}
