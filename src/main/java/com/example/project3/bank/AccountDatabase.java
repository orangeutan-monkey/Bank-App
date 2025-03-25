package com.example.project3.bank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import com.example.project3.util.Date;
import com.example.project3.util.List;
import com.example.project3.util.Sort;

/**
 * AccountDatabase extends the generic List to hold Account objects.
 * It maintains an Archive for closed accounts, loads accounts from a file,
 * processes batch account activities, and provides sorted print methods.
 *
 * @author
 * Anirudh Deveram
 */
public class AccountDatabase extends List<Account>
{
    private Archive archive;

    public AccountDatabase()
    {
        super();
        archive = new Archive();
    }

    public Archive getArchive()
    {
        return archive;
    }

    public Account getAccountByNumber(String accNum)
    {
        for (int i = 0; i < this.size(); i++)
        {
            Account acc = this.get(i);
            if (acc.getNumber().toString().equals(accNum))
            {
                return acc;
            }
        }
        return null;
    }
    
    public void printArchive()
    {
        archive.print();
    }
    
    /**
     * Loads accounts from a comma-delimited text file.
     * Expected tokens per line: accountType, branch, firstName, lastName, dob, balance,
     * plus for COLLEGE_CHECKING: campusCode, or for CD: term, openDate (mm/dd/yyyy).
     */
    public void loadAccounts(File file) throws IOException
    {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
            {
                continue;
            }
            StringTokenizer st = new StringTokenizer(line, ",");
            if (st.countTokens() < 6)
            {
                continue;
            }
            String accTypeStr = st.nextToken().trim();
            AccountType accType = AccountType.fromString(accTypeStr);
            String branchStr = st.nextToken().trim();
            Branch branch = Branch.getBranchByCity(branchStr);
            String firstName = st.nextToken().trim();
            String lastName = st.nextToken().trim();
            Date dob = Date.parseDate(st.nextToken().trim());
            double balance = Double.parseDouble(st.nextToken().trim());
            Profile profile = new Profile(firstName, lastName, dob);
            AccountNumber accNumber = new AccountNumber(branch, accType);
            Account account = null;
            switch (accType)
            {
                case CHECKING:
                {
                    account = new Checking(accNumber, profile, balance);
                    break;
                }
                case SAVINGS:
                {
                    // Check if the customer has a checking account
                    boolean loyal = false;
                    for (int i = 0; i < this.size(); i++) {
                        Account existingAcc = this.get(i);
                        if (existingAcc.getHolder().equals(profile) && 
                            existingAcc.getNumber().getType() == AccountType.CHECKING) {
                            loyal = true;
                            break;
                        }
                    }
                    account = new Savings(accNumber, profile, balance, loyal);
                    break;
                }
                case MONEY_MARKET:
                {
                    if (balance < 2000)
                    {
                        continue;
                    }
                    boolean loyal = balance >= 5000;
                    account = new MoneyMarket(accNumber, profile, balance, loyal);
                    break;
                }
                case COLLEGE_CHECKING:
                {
                    if (st.countTokens() < 1)
                    {
                        continue;
                    }
                    Campus campus = Campus.fromCode(st.nextToken().trim());
                    account = new CollegeChecking(accNumber, profile, balance, campus);
                    break;
                }
                case CD:
                {
                    if (st.countTokens() < 2) // Changed from 4 to 2
                    {
                        continue;
                    }
                    int term = Integer.parseInt(st.nextToken().trim());

                    // Parse the date as a whole using your Date.parseDate method
                    String openDateStr = st.nextToken().trim();
                    Date openDate = Date.parseDate(openDateStr);

                    if (openDate == null) {
                        System.out.println("Error: Could not parse CD opening date: " + openDateStr);
                        continue;
                    }

                    account = new CertificateDeposit(accNumber, profile, balance, term, openDate);
                    break;
                }
            }
            if (account != null)
            {
                this.add(account);
            }
        }
        scanner.close();
        System.out.println("Accounts in \"" + file.getName() + "\" loaded to the database.");
    }

    /**
     * Processes account activities from a comma-delimited text file.
     * Expected tokens per line: activityType (D/W), accountNumber, date, branch, amount.

    public void processActivity(File file) throws IOException
    {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
            {
                continue;
            }
            StringTokenizer token = new StringTokenizer(line, ",");
            if (token.countTokens() < 5)
            {
                continue;
            }
            char activityType = token.nextToken().trim().charAt(0);
            String accountNumber = token.nextToken().trim();
            Date date = Date.parseDate(token.nextToken().trim());
            String branchName = token.nextToken().trim();
            Branch branch = Branch.getBranchByCity(branchName);
            double amount = Double.parseDouble(token.nextToken().trim());
            
            if (branch == null) {
                System.out.println("Invalid branch: " + branchName);
                continue;
            }
            
            Activity activity = new Activity(date, branch, activityType, amount, true);
            Account account = getAccountByNumber(accountNumber);
            if (account != null)
            {
                account.addActivity(activity);
                if (activityType == 'D')
                {
                    account.deposit(amount);
                    
                    // Update loyalty status for MoneyMarket accounts if balance increases to $5000 or more
                    if (account instanceof MoneyMarket) {
                        MoneyMarket mm = (MoneyMarket) account;
                        if (account.getBalance() >= 5000) {
                            mm.setLoyalStatus(true);
                        }
                    }
                }
                else if (activityType == 'W')
                {
                    account.withdraw(amount);
                    
                    // Update loyalty status for MoneyMarket accounts if balance drops below $5000
                    if (account instanceof MoneyMarket) {
                        MoneyMarket mm = (MoneyMarket) account;
                        mm.incrementWithdrawals();
                        if (account.getBalance() < 5000) {
                            mm.setLoyalStatus(false);
                        }
                    }
                }
            }
        }
        scanner.close();
    }
    */
    /**
     * Processes account activities from a comma-delimited text file.
     * Expected tokens per line: activityType (D/W), accountNumber, date, branch, amount.
     */
    public void processActivity(File file) throws IOException
    {
        Scanner scanner = new Scanner(file);
        System.out.println("Processing \"" + file.getName() + "\"...");

        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
            {
                continue;
            }
            StringTokenizer token = new StringTokenizer(line, ",");
            if (token.countTokens() < 5)
            {
                continue;
            }

            char activityType = token.nextToken().trim().charAt(0);
            String accountNumber = token.nextToken().trim();
            Date date = Date.parseDate(token.nextToken().trim());
            String branchName = token.nextToken().trim();
            Branch branch = Branch.getBranchByCity(branchName);
            double amount = Double.parseDouble(token.nextToken().trim());

            if (branch == null) {
                System.out.println("Invalid branch: " + branchName);
                continue;
            }

            Account account = getAccountByNumber(accountNumber);
            if (account != null)
            {
                Activity activity = new Activity(date, branch, activityType, amount, true);
                System.out.println(accountNumber + "::" + date.toString() + "::" +
                        branch.name() + "[ATM]::" +
                        (activityType == 'D' ? "deposit" : "withdrawal") + "::$" +
                        String.format("%,.2f", amount));

                account.addActivity(activity);

                if (activityType == 'D')
                {
                    account.deposit(amount);

                    // Update loyalty status for MoneyMarket accounts
                    if (account instanceof MoneyMarket) {
                        MoneyMarket mm = (MoneyMarket) account;
                        if (account.getBalance() >= 5000) {
                            mm.setLoyalStatus(true);
                        }
                    }
                }
                else if (activityType == 'W')
                {
                    account.withdraw(amount);

                    // Update loyalty status and withdrawal count for MoneyMarket
                    if (account instanceof MoneyMarket) {
                        MoneyMarket mm = (MoneyMarket) account;
                        mm.incrementWithdrawals();
                        if (account.getBalance() < 5000) {
                            mm.setLoyalStatus(false);
                        }
                    }
                }
            }
        }
        scanner.close();
        System.out.println("Account activities in \"" + file.getName() + "\" processed.");
    }
    /**
     * Prints accounts ordered by branch location (county, city).
     * Format:
     * *List of accounts ordered by branch location (county, city).
     * County: <county>
     * <account lines...>
     * *end of list.
     */
    public void printByBranch()
    {
        if (this.isEmpty())
        {
            System.out.println("Account database is empty!");
            return;
        }
        Sort.account(this, 'B');
        System.out.println("*List of accounts ordered by branch location (county, city).");
        String currentCounty = "";
        for (int i = 0; i < this.size(); i++)
        {
            Account acc = this.get(i);
            String county = acc.getNumber().getBranch().getCounty();
            if (!county.equalsIgnoreCase(currentCounty))
            {
                currentCounty = county;
                System.out.println("County: " + currentCounty);
            }
            System.out.println(acc.toString());
        }
        System.out.println("*end of list.");
    }

    /**
     * Prints accounts ordered by account holder.
     * Format:
     * *List of accounts ordered by account holder:
     * <account lines...>
     * *end of list.
     */
    public void printByHolder()
    {
        if (this.isEmpty())
        {
            System.out.println("Account database is empty!");
            return;
        }
        Sort.account(this, 'H');
        System.out.println("*List of accounts ordered by account holder:");
        for (int i = 0; i < this.size(); i++)
        {
            System.out.println(get(i).toString());
        }
        System.out.println("*end of list.");
    }

    /**
     * Prints accounts ordered by account type.
     * Format:
     * *List of accounts ordered by account type:
     * Account Type: <TYPE>
     * <account lines...>
     * *end of list.
     */
    public void printByType()
    {
        if (this.isEmpty())
        {
            System.out.println("Account database is empty!");
            return;
        }
        Sort.account(this, 'T');
        System.out.println("*List of accounts ordered by account type:");
        AccountType currentType = null;
        for (int i = 0; i < this.size(); i++)
        {
            Account acc = this.get(i);
            AccountType thisType = acc.getNumber().getType();
            if (currentType == null || thisType != currentType)
            {
                currentType = thisType;
                System.out.println("Account Type: " + currentType);
            }
            System.out.println(acc.toString());
        }
        System.out.println("*end of list.");
    }

    /**
     * Prints the account statements for all accounts.
     */
    public void printStatements()
    {
        if (this.isEmpty())
        {
            System.out.println("Account database is empty!");
            return;
        }
        
        System.out.println("*Account statements by account holder.");
        
        // Create a temporary list of accounts sorted by holder
        List<Account> sortedAccounts = new List<>();
        for (int i = 0; i < this.size(); i++) {
            sortedAccounts.add(this.get(i));
        }
        Sort.account(sortedAccounts, 'H');
        
        // Group accounts by holder profile
        Profile currentHolder = null;
        int accountIndex = 1;
        
        for (int i = 0; i < sortedAccounts.size(); i++) {
            Account acc = sortedAccounts.get(i);
            Profile holder = acc.getHolder();
            
            // If this is a new holder, print holder information
            if (currentHolder == null || !currentHolder.equals(holder)) {
                currentHolder = holder;
                System.out.println(accountIndex + "." + holder.toString());
                accountIndex++;
            }
            
            // Print the account statement for this account
            System.out.println("\t[Account#] " + acc.getNumber().toString());
            
            // Print activities if any
            if (acc.activities.size() > 0) {
                System.out.println("\t[Activity]");
                for (int j = 0; j < acc.activities.size(); j++) {
                    Activity activity = acc.activities.get(j);
                    System.out.println("\t\t" + activity.getDate().toString() + "::" + 
                                      activity.getLocation().name() + 
                                      (activity.isAtm() ? "[ATM]" : "") + "::" +
                                      (activity.getType() == 'D' ? "deposit" : "withdrawal") + "::" +
                                      "$" + String.format("%.2f", activity.getAmount()));
                }
            }
            
            // Calculate and print interest and fee
            double interest = acc.interest();
            double fee = acc.fee();
            System.out.println("\t[interest] $" + String.format("%.2f", interest) + 
                              " [Fee] $" + String.format("%.2f", fee));
            
            // Calculate and print updated balance
            double updatedBalance = acc.getBalance() + interest - fee;
            System.out.println("\t[Balance] $" + String.format("%.2f", updatedBalance));
            
            // Add a blank line between accounts
            System.out.println();
        }
        
        System.out.println("*end of statements.");
    }
}