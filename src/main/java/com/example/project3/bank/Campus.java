package com.example.project3.bank;

/**
 * Enum for campus codes used in College Checking accounts.
 * 
 * @author
 * Anirudh Deveram
 */
public enum Campus
{
    NEW_BRUNSWICK("1", "New Brunswick"),
    NEWARK("2", "Newark"),
    CAMDEN("3", "Camden");

    private String code;
    private String name;

    Campus(String code, String name)
    {
        this.code = code;
        this.name = name;
    }

    public String getCode()
    {
        return code;
    }

    public String getCampusName()
    {
        return name;
    }

    public static Campus fromCode(String code)
    {
        for (Campus c : Campus.values())
        {
            if (c.code.equals(code))
            {
                return c;
            }
        }
        return null;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
