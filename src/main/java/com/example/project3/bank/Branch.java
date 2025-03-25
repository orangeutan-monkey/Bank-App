package com.example.project3.bank;

/**
 * Enum representing Bank branch locations.
 * Each branch has a ZIP code, branch code, and county.
 * 
 * @author 
 *
 *   Anirudh Deveram
 */
public enum Branch
{
    EDISON("08817", "100", "Middlesex"),
    BRIDGEWATER("08807", "200", "Somerset"),
    PRINCETON("08542", "300", "Mercer"),
    PISCATAWAY("08854", "400", "Middlesex"),
    WARREN("07057", "500", "Somerset");

    private final String zip;
    private final String branchCode;
    private final String county;

    Branch(String zip, String branchCode, String county)
    {
        this.zip = zip;
        this.branchCode = branchCode;
        this.county = county;
    }

    public String getZip()
    {
        return zip;
    }

    public String getBranchCode()
    {
        return branchCode;
    }

    public String getCounty()
    {
        return county;
    }

    public static Branch getBranchByCity(String city)
    {
        for (Branch b : Branch.values())
        {
            if (b.name().equalsIgnoreCase(city))
            {
                return b;
            }
        }
        return null;
    }
}
