package com.example.project3.bank;

import com.example.project3.util.Date;

/**
 * Represents a customer's profile with first name, last name, and date of birth.
 * Implements Comparable to allow sorting by last name, then first name, then DOB.
 * 
 * @author 
 *
 *   Anirudh Deveram
 */
public class Profile implements Comparable<Profile>
{
    private String fname;
    private String lname;
    private Date dob;

    public Profile(String fname, String lname, Date dob)
    {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname()
    {
        return fname;
    }

    public String getLname()
    {
        return lname;
    }

    public Date getDob()
    {
        return dob;
    }

    @Override
    public int compareTo(Profile other)
    {
        int cmp = this.lname.compareToIgnoreCase(other.lname);
        if (cmp != 0)
        {
            return cmp;
        }
        cmp = this.fname.compareToIgnoreCase(other.fname);
        if (cmp != 0)
        {
            return cmp;
        }
        return this.dob.compareTo(other.dob);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Profile)
        {
            Profile other = (Profile)obj;
            return this.fname.equalsIgnoreCase(other.fname)
                   && this.lname.equalsIgnoreCase(other.lname)
                   && this.dob.equals(other.dob);
        }
        return false;
    }

    @Override
    public String toString()
    {
        return fname + " " + lname + " " + dob.toString();
    }
    // Testbed main() method

    /**
     * Testbed main() for the Profile class.
     * Contains 7 test cases: three returning -1, three returning 1, and one returning 0.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        Profile p1 = new Profile("Alice", "Smith", new Date(1, 1, 1990));
        Profile p2 = new Profile("Bob", "Smith", new Date(1, 1, 1990));
        Profile p3 = new Profile("Alice", "Smith", new Date(1, 1, 1991));
        Profile p4 = new Profile("Alice", "Smith", new Date(1, 1, 1990));
        Profile p5 = new Profile("Charlie", "Brown", new Date(5, 5, 1985));
        Profile p6 = new Profile("Alice", "Anderson", new Date(1, 1, 1990));
        Profile p7 = new Profile("Alice", "Smith", new Date(1, 2, 1990));

        System.out.println(p1.compareTo(p2)); // Expect negative (Alice < Bob)
        System.out.println(p2.compareTo(p1)); // Expect positive
        System.out.println(p1.compareTo(p3)); // Expect negative (1990 vs 1991)
        System.out.println(p3.compareTo(p1)); // Expect positive
        System.out.println(p5.compareTo(p6)); // Expect positive ("Brown" > "Anderson")
        System.out.println(p6.compareTo(p5)); // Expect negative
        System.out.println(p1.compareTo(p4)); // Expect 0 (equal)
    }
}
