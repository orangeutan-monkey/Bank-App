package com.example.project3.util;
import java.time.LocalDate;

/**
 * Date class represents a calendar date.
 * Provides methods for parsing, validating, comparing, and representing dates.
 * Also checks for leap years.
 * 
 * @author 
 *   Karthik Penumetch, 
 *   Anirudh Deveram
 */
public class Date implements Comparable<Date>
{
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;

    public int month;
    public int day;
    public int year;

    public Date(int month, int day, int year)
    {
        this.month = month;
        this.day = day;
        this.year = year;
    }
    public Date (LocalDate date)
    {
        this.month = date.getMonthValue();
        this.day = date.getDayOfMonth();
        this.year = date.getYear();
    }
    private boolean isLeapYear(int y)
    {
        if (y % QUADRENNIAL != 0)
        {
            return false;
        }
        if (y % CENTENNIAL == 0 && y % QUATERCENTENNIAL != 0)
        {
            return false;
        }
        return true;
    }

    public static Date parseDate(String dateStr)
    {
        String[] parts = dateStr.split("/");
        if (parts.length != 3)
        {
            return null;
        }
        try
        {
            int m = Integer.parseInt(parts[0]);
            int d = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            return new Date(m, d, y);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }

    public boolean isValid()
    {
        if (month < 1 || month > 12)
        {
            return false;
        }
        int maxDay;
        switch (month)
        {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
            {
                maxDay = 31;
                break;
            }
            case 4: case 6: case 9: case 11:
            {
                maxDay = 30;
                break;
            }
            case 2:
            {
                maxDay = isLeapYear(year) ? 29 : 28;
                break;
            }
            default:
            {
                return false;
            }
        }
        return day >= 1 && day <= maxDay;
    }

    @Override
    public int compareTo(Date other)
    {
        if (this.year != other.year)
        {
            return this.year - other.year;
        }
        if (this.month != other.month)
        {
            return this.month - other.month;
        }
        return this.day - other.day;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Date)
        {
            Date other = (Date)obj;
            return this.year == other.year && this.month == other.month && this.day == other.day;
        }
        return false;
    }
    public static Date fromLocalDate(LocalDate localDate)
    {
        if (localDate == null)
        {
            return null;
        }
        return new Date(
                localDate.getMonthValue(),
                localDate.getDayOfMonth(),
                localDate.getYear()
        );
    }
    @Override
    public String toString()
    {
        return month + "/" + day + "/" + year;
    }
    // Testbed main() method

    /**
     * Testbed main() for the Date class.
     * Contains four invalid test cases and two valid test cases.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args)
    {
        Date d1 = new Date(0, 19, 2000);   // invalid month
        Date d2 = new Date(13, 19, 2000);  // invalid month
        Date d3 = new Date(2, 29, 1999);   // invalid (1999 not a leap year)
        Date d4 = new Date(6, 31, 2000);   // invalid day for June
        Date d5 = new Date(2, 29, 2000);   // valid leap year
        Date d6 = new Date(3, 15, 2000);   // valid

        System.out.println(d1.isValid()); // false
        System.out.println(d2.isValid()); // false
        System.out.println(d3.isValid()); // false
        System.out.println(d4.isValid()); // false
        System.out.println(d5.isValid()); // true
        System.out.println(d6.isValid()); // true
    }
}
