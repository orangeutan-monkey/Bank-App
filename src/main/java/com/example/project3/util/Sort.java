package com.example.project3.util;

import com.example.project3.bank.Account;

/**
 * Utility class for sorting a List<Account> using bubble sort.
 * Allowed keys:
 *   'B' - sort by branch (county and branch name, case-insensitive)
 *   'H' - sort by account holder (using Profile.compareTo)
 *   'T' - sort by account type (using AccountType.toString)
 */
public class Sort
{
    public static void account(List<Account> list, char key)
    {
        int n = list.size();
        for (int i = 0; i < n - 1; i++)
        {
            for (int j = 0; j < n - 1 - i; j++)
            {
                Account a = list.get(j);
                Account b = list.get(j + 1);
                boolean swap = false;
                switch (key)
                {
                    case 'B':
                    {
                        String branchA = a.getNumber().getBranch().getCounty() + a.getNumber().getBranch().name();
                        String branchB = b.getNumber().getBranch().getCounty() + b.getNumber().getBranch().name();
                        if (branchA.compareToIgnoreCase(branchB) > 0)
                        {
                            swap = true;
                        }
                        break;
                    }
                    case 'H':
                    {
                        if (a.getHolder().compareTo(b.getHolder()) > 0)
                        {
                            swap = true;
                        }
                        break;
                    }
                    case 'T':
                    {
                        String typeA = a.getNumber().getType().toString();
                        String typeB = b.getNumber().getType().toString();
                        if (typeA.compareTo(typeB) > 0)
                        {
                            swap = true;
                        }
                        break;
                    }
                    default:
                    {
                        if (a.compareTo(b) > 0)
                        {
                            swap = true;
                        }
                    }
                }
                if (swap)
                {
                    Account temp = list.get(j);
                    list.set(j, b);
                    list.set(j + 1, temp);
                }
            }
        }
    }
}
