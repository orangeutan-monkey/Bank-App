# Bank-App

## Project Overview
Bank is a JavaFX-based banking application that allows users to manage different types of bank accounts. The application supports account creation, transactions (deposits and withdrawals), account closures, and generating various reports.

## Features

### Account Management
- **Create Accounts**: Open different types of accounts including Checking, College Checking, Savings, Money Market, and Certificate of Deposit (CD)
- **Close Accounts**: Close individual accounts or all accounts for a specific holder
- **View Accounts**: View accounts sorted by holder, type, or branch location

### Transaction Processing
- **Deposits**: Make deposits to accounts (except CD accounts)
- **Withdrawals**: Withdraw funds from accounts (except CD accounts)
- **Activity Tracking**: Record all transaction activities with details

### Data Management
- **Load Accounts**: Import accounts from a comma-delimited text file
- **Load Activities**: Import transaction activities from a comma-delimited text file
- **Print Statements**: Generate account statements showing balances, activities, interest, and fees
- **Print Archive**: View closed accounts in the archive

## Account Types

### Checking
- Standard checking account
- Monthly interest: balance * 1.5% / 12
- Fee: $15 if balance < $1,000, $0 otherwise

### College Checking
- For students age 24 or younger
- No monthly fee
- Associated with a campus (New Brunswick, Newark, or Camden)

### Savings
- Standard savings account
- Annual interest rate: 2.5% (2.75% for loyal customers)
- Fee: $25 if balance < $500, $0 otherwise
- Loyalty status given to customers with checking accounts

### Money Market
- Minimum initial deposit: $2,000
- Annual interest rate: 3.5% (3.75% for loyal customers with balance ≥ $5,000)
- Fee: $15 if balance < $2,000, plus $10 for each withdrawal beyond 3 per statement cycle
- Tracks number of withdrawals

### Certificate of Deposit (CD)
- Fixed-term deposit with maturity date
- Terms: 3, 6, 9, or 12 months
- Minimum deposit: $1,000
- No monthly fee
- Higher interest rates for longer terms (3%–4%)
- Early withdrawal penalty if closed before maturity

## Technical Details

### Project Structure
- `com.example.project3` - Main application package
  - `Main.java` - Application entry point
  - `Controller.java` - UI controller handling events
  - `rubank` - Banking components
    - Account classes (Account, Checking, Savings, etc.)
    - Banking infrastructure (Branch, Profile, etc.)
  - `util` - Utility classes
    - `Date.java` - Custom date handling
    - `List.java` - Generic list implementation
    - `Sort.java` - Account sorting utility

### Data File Formats

#### Accounts File Format
```
accountType,branch,firstName,lastName,DOB,balance[,campusCode/term,openDate]
```
Examples:
```
checking,bridgewater,John,Doe,2/19/2000,500.0
savings,edison,Jane,Doe,11/2/2002,800
college,piscataway,Jane,Anderson,11/5/2003,800.99,1
certificate,bridgewater,Jane,Anderson,11/5/2003,5000,3,2/1/2025
```

#### Activities File Format
```
activityType,accountNumber,date,branch,amount
```
Examples:
```
D,200017410,2/2/2025,bridgewater,1000
W,300031134,2/7/2025,warren,1000
```

## Running the Application
1. Ensure you have JavaFX properly installed and configured
2. Build and run the project using Maven or your preferred IDE
3. The application will open with three tabs for account management, transactions, and reporting
4. Account and activity files can be loaded through the Account Database tab

## Requirements
- Java 17 or later
- JavaFX 17.0.6 or later
- A compatible IDE (Eclipse, IntelliJ IDEA, etc.)

## Authors
- Anirudh Deveram
