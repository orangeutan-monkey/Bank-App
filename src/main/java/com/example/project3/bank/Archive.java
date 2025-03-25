package com.example.project3.bank;

import com.example.project3.util.Date;

/**
 * Archive implements a linked list to store closed accounts along with their closing date.
 * 
 * @author 
 *
 *   Anirudh Deveram
 */
public class Archive
{
    private AccountNode first;

    private class AccountNode
    {
        Account account;
        Date close;
        AccountNode next;

        AccountNode(Account account, Date close)
        {
            this.account = account;
            this.close = close;
            this.next = null;
        }
    }

    public void add(Account account, Date close)
    {
        AccountNode node = new AccountNode(account, close);
        node.next = first;
        first = node;
    }

    public void add(Account account)
    {
        add(account, null);
    }

    public void print()
    {
        System.out.println("*List of closed accounts in the archive.");
        AccountNode current = first;
        while (current != null)
        {
            System.out.print(current.account.toString());
            if (current.close != null)
            {
                System.out.print(" Closed on " + current.close.toString());
            }
            System.out.println();
            current = current.next;
        }
        System.out.println("*end of list.");
    }
}
