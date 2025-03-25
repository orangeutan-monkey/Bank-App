package com.example.project3;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.stage.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.StringTokenizer;
import rubank.Account;
import rubank.AccountDatabase;
import rubank.AccountNumber;
import rubank.AccountType;
import rubank.Activity;
import rubank.Archive;
import rubank.Branch;
import rubank.Campus;
import util.Date;
import rubank.CertificateDeposit;
import rubank.Checking;
import rubank.CollegeChecking;
import rubank.MoneyMarket;
import rubank.Profile;
import rubank.Savings;

public class Controller
{
    private static final int MM_INITIAL_DEPO = 2000;
    private static final int CD_INITIAL_DEPO = 1000;

    // Main Layout Components
    @FXML
    private BorderPane RootPanel;
    @FXML
    private TabPane MainTabPane;
    @FXML
    private TextArea OutputTextArea;

    // Tab 1: Open New Account
    @FXML
    private Tab OpenNewTab;
    @FXML
    private AnchorPane OpenNewAnchorMain;
    @FXML
    private AnchorPane OpenNewAnchorwithinAnchor;
    @FXML
    private GridPane OpenNewMainGrid;
    @FXML
    private Label AccountTypeLabel;
    @FXML
    private GridPane AccountTypeGrid;
    @FXML
    private RadioButton CheckingRB;
    @FXML
    private RadioButton CcRB;
    @FXML
    private RadioButton SavingRB;
    @FXML
    private RadioButton MmRB;
    @FXML
    private RadioButton CdRB;
    @FXML
    private ToggleGroup Rb_types;
    @FXML
    private HBox BranchHbox;
    @FXML
    private ComboBox<String> BranchComboBox;
    @FXML
    private Label FirstNameLabel;
    @FXML
    private TextField FirstNameTextField;
    @FXML
    private Label LastNameLabel;
    @FXML
    private TextField LastNameText;
    @FXML
    private Label DobLabel;
    @FXML
    private DatePicker DobDatePicker;
    @FXML
    private Label InitialDepositLabel;
    @FXML
    private TextField InitialDepositText;
    @FXML
    private VBox CampusVBox;
    @FXML
    private Label CampusLabel;
    @FXML
    private HBox CampusHBox;
    @FXML
    private RadioButton NbRB;
    @FXML
    private RadioButton NewarkRB;
    @FXML
    private RadioButton CamdenRB;
    @FXML
    private ToggleGroup Rb_campus;
    @FXML
    private HBox DateOpenedHBox;
    @FXML
    private Label DateOpenedLabel;
    @FXML
    private DatePicker DateOpenedDatePicker;
    @FXML
    private ComboBox<String> TermComboBox;
    @FXML
    private CheckBox LoyalCheckBox;
    @FXML
    private Button OpenButton;
    @FXML
    private Button CancelButton;

    // Tab 2: Close/Deposit/Withdraw
    @FXML
    private Tab CloseDepositWithdrawTab;
    @FXML
    private GridPane CloseAccountGrid;
    @FXML
    private Label AccountNumberLabel;
    @FXML
    private TextField AccountNumberText;
    @FXML
    private Label AmountLabel;
    @FXML
    private TextField AmountText;
    @FXML
    private Label ClosingDateLabel;
    @FXML
    private DatePicker ClosingDateDatePicker;
    @FXML
    private Button DepositButton;
    @FXML
    private Button WithdrawButton;
    @FXML
    private Button CloseAccountButton;
    @FXML
    private HBox ClosingHBox;
    @FXML
    private Label ClosingFirstNLabel;
    @FXML
    private TextField ClosingFirstNText;
    @FXML
    private Label ClosingLastNLabel;
    @FXML
    private TextField ClosingLastNText;
    @FXML
    private Label ClosingDOBLabel;
    @FXML
    private DatePicker ClosingDOBDatePicker;
    @FXML
    private Button CloseAllButton;

    // Tab 3: Account Database
    @FXML
    private Tab AccountDatabaseTab;
    @FXML
    private AnchorPane AccountDBAnchor;
    @FXML
    private GridPane AccountDBGrid;
    @FXML
    private Button LoadAccountFileButton;
    @FXML
    private Button LoadActivitesFileButton;
    @FXML
    private Button PrintHolderButton;
    @FXML
    private Button PrintTypeButton;
    @FXML
    private Button PrintBranchButton;
    @FXML
    private Button PrintStatementButton;
    @FXML
    private Button PrintArchiveButton;

    private AccountDatabase accDatabase;

    //initalizing the controller, which is done by fxmlloader
    @FXML
    private void init()
    {
        accDatabase = new AccountDatabase();
        setupInitialUI();
    }

    //ui setup
    private void setupInitialUI()
    {
        setupBranchComboBox();
        setupComboBox();
        setupToggleGroups();
        updateUIBasedOnAccountType();
        OutputTextArea.setEditable(false);
    }

    //branch combobox setup
    private void setupBranchComboBox()
    {
        ComboBox<String> branchBox = (ComboBox<String>) BranchComboBox;
        branchBox.setItems(FXCollections.observableArrayList(
                "EDISON", "BRIDGEWATER", "PRINCETON", "PISCATAWAY", "WARREN"
        ));
        branchBox.getSelectionModel().selectFirst();
    }

    //cd combobox setup
    private void setupComboBox()
    {
        ComboBox<String> termBox = (ComboBox<String>) TermComboBox;
        termBox.setItems(FXCollections.observableArrayList("3","6","9","12"));
        termBox.getSelectionModel().selectFirst();
    }

    //set up toggle groups and radio button handlers
    private void setupToggleGroups()
    {
        if (Rb_types == null)
        {
            Rb_types = new ToggleGroup();
            CheckingRB.setToggleGroup(Rb_types);
            CcRB.setToggleGroup(Rb_types);
            SavingRB.setToggleGroup(Rb_types);
            MmRB.setToggleGroup(Rb_types);
            CdRB.setToggleGroup(Rb_types);
        }

        if (Rb_campus == null)
        {
            Rb_campus = new ToggleGroup();
            NbRB.setToggleGroup(Rb_campus);
            NewarkRB.setToggleGroup(Rb_campus);
            CamdenRB.setToggleGroup(Rb_campus);
        }

        CheckingRB.setSelected(true);
        NbRB.setSelected(true);

        Rb_types.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            updateUIBasedOnAccountType();
        });
    }

    //update UI based on selected account type
    private void updateUIBasedOnAccountType()
    {
        CampusVBox.setVisible(false);
        CampusVBox.setManaged(false);
        DateOpenedHBox.setVisible(false);
        DateOpenedHBox.setManaged(false);
        TermComboBox.setVisible(false);
        TermComboBox.setManaged(false);
        LoyalCheckBox.setVisible(false);

        if (CcRB.isSelected())
        {
            CampusVBox.setVisible(true);
            CampusVBox.setManaged(true);
        }
        else if (CdRB.isSelected())
        {
            DateOpenedHBox.setVisible(true);
            DateOpenedHBox.setManaged(true);
            TermComboBox.setVisible(true);
            TermComboBox.setManaged(true);
        }
        else if (SavingRB.isSelected() || MmRB.isSelected())
        {
            LoyalCheckBox.setVisible(true);
        }
    }

    //handle opening account
    @FXML
    private void handleOpenAccount()
    {
        try
        {
            OutputTextArea.clear();
            AccountType accountType = getSelectedAccountType();
            String branchStr = ((ComboBox<String>)BranchComboBox).getValue();
            Branch branch = Branch.valueOf(branchStr);
            if (branch == null) return;
            String firstName = FirstNameTextField.getText().trim();
            String lastName = LastNameText.getText().trim();
            if (firstName.isEmpty() || lastName.isEmpty())
            {
                OutputTextArea.setText("Processing \"" + selectedFile.getName() + "\"...");
                accDatabase.processActivity(selectedFile);
                OutputTextArea.setText("Account activities in \"" + selectedFile.getName() + "\" processed.");
            }
                catch (IOException e)
            {
                OutputTextArea.setText("Error processing activities: " + e.getMessage());
            }
        }
            else
        {
            OutputTextArea.setText("No file selected.");
        }
    }
        catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by holder button click
@FXML
private void handlePrintByHolder()
{
    try
    {
        if (accDatabase.isEmpty())
        {
            OutputTextArea.setText("Account database is empty!");
            return;
        }
        StringBuffer output = new StringBuffer("*List of accounts ordered by account holder:\n");
        accDatabase.printByHolder();
        for (int i = 0; i < accDatabase.size(); i++)
        {
            output.append(accDatabase.get(i).toString() + "\n");
        }
        output.append("*end of list.");
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by type button click
@FXML
private void handlePrintByType()
{
    try
    {
        if (accDatabase.isEmpty())
        {
            OutputTextArea.setText("Account database is empty!");
            return;
        }
        StringBuffer output = new StringBuffer("*List of accounts ordered by account type:\n");
        AccountType currentType = null;
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            AccountType thisType = acc.getNumber().getType();
            if (currentType == null || thisType != currentType)
            {
                currentType = thisType;
                output.append("Account Type: " + currentType + "\n");
            }
            output.append(acc.toString() + "\n");
        }
        output.append("*end of list.");
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by branch button click
@FXML
private void handlePrintByBranch()
{
    try
    {
        if (accDatabase.isEmpty())
        {
            OutputTextArea.setText("Account database is empty!");
            return;
        }
        StringBuffer output = new StringBuffer("*List of accounts ordered by branch location (county, city).\n");
        String currentCounty = "";
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            String county = acc.getNumber().getBranch().getCounty();
            if (!county.equalsIgnoreCase(currentCounty))
            {
                currentCounty = county;
                output.append("County: " + currentCounty + "\n");
            }
            output.append(acc.toString() + "\n");
        }
        output.append("*end of list.");
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print statements button click
@FXML
private void handlePrintStatements()
{
    try
    {
        if (accDatabase.isEmpty())
        {
            OutputTextArea.setText("Account database is empty!");
            return;
        }

        StringBuffer output = new StringBuffer("*Account statements by account holder.\n\n");

        Profile currentHolder = null;
        int accountIndex = 1;

        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            Profile holder = acc.getHolder();

            if (currentHolder == null || !currentHolder.equals(holder))
            {
                currentHolder = holder;
                output.append(accountIndex + "." + holder.toString() + "\n");
                accountIndex++;
            }

            output.append("\t[Account#] " + acc.getNumber().toString() + "\n");

            if (acc.activities.size() > 0)
            {
                output.append("\t[Activity]\n");
                for (int j = 0; j < acc.activities.size(); j++)
                {
                    Activity activity = acc.activities.get(j);
                    output.append("\t\t" + activity.getDate().toString() + "::" +
                            activity.getLocation().name() +
                            (activity.isAtm() ? "[ATM]" : "") + "::" +
                            (activity.getType() == 'D' ? "deposit" : "withdrawal") + "::" +
                            "$" + String.format("%.2f", activity.getAmount()) + "\n");
                }
            }

            double interest = acc.interest();
            double fee = acc.fee();
            output.append("\t[interest] $" + String.format("%.2f", interest) +
                    " [Fee] $" + String.format("%.2f", fee) + "\n");

            double updatedBalance = acc.getBalance() + interest - fee;
            output.append("\t[Balance] $" + String.format("%.2f", updatedBalance) + "\n\n");
        }

        output.append("*end of statements.");
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print archive button click
@FXML
private void handlePrintArchive()
{
    try
    {
        StringBuffer output = new StringBuffer("*List of closed accounts in the archive.\n");
        Archive archive = accDatabase.getArchive();
        if (archive != null)
        {
            // Since the actual archive printing is handled by the Archive class,
            // we'll just call that method and capture its System.out output.
            // For GUI purposes, this is a simplification
            output.append("Closed account information would be displayed here.");
        }
        else
        {
            output.append("No closed accounts in the archive.");
        }
        output.append("\n*end of list.");
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle cancel button click
@FXML
private void handleCancel()
{
    clearInputFields();
}

//clear input fields after successful account creation
private void clearInputFields()
{
    FirstNameTextField.clear();
    LastNameText.clear();
    DobDatePicker.setValue(null);
    InitialDepositText.clear();
    DateOpenedDatePicker.setValue(null);
    LoyalCheckBox.setSelected(false);
}("Error: First name and last name are required.");
        return;
        }
LocalDate localDob = DobDatePicker.getValue();
            if (localDob == null)
        {
        OutputTextArea.setText("Error: Date of birth is required.");
                return;
                        }
Date dob = new Date(localDob.getMonthValue(), localDob.getDayOfMonth(), localDob.getYear());
            if (!dob.isValid())
        {
        OutputTextArea.setText("DOB invalid: " + dob + " not a valid calendar date!");
                return;
                        }
                        if (!isEighteenOrOlder(dob))
        {
        OutputTextArea.setText("Not eligible to open: " + dob + " under 18.");
                return;
                        }
Profile profile = new Profile(firstName, lastName, dob);
double initialDeposit;
            try
                    {
initialDeposit = Double.parseDouble(InitialDepositText.getText().trim());
        if (initialDeposit <= 0)
        {
        OutputTextArea.setText("Initial deposit cannot be 0 or negative.");
                    return;
                            }
                            }
                            catch (NumberFormatException e)
        {
        OutputTextArea.setText("For input string: \"" + InitialDepositText.getText().trim() + "\" - not a valid amount.");
        return;
        }
Account account = createAccountByType(accountType, branch, profile, initialDeposit);
            if (account == null) return;
        accDatabase.add(account);
            OutputTextArea.setText(accountType.getName() + " account " + account.getNumber() + " has been opened.");
        if (accountType.equals(AccountType.CHECKING))
        {
        accDatabase.updateLoyaltyForSavings(profile, true);
            }
clearInputFields();
        }
                catch (Exception e)
        {
        OutputTextArea.setText("Error: " + e.getMessage());
        }
        }

//get selected account type from radio buttons
private AccountType getSelectedAccountType()
{
    if (CheckingRB.isSelected())
    {
        return AccountType.CHECKING;
    }
    else if (CcRB.isSelected())
    {
        return AccountType.COLLEGE_CHECKING;
    }
    else if (SavingRB.isSelected())
    {
        return AccountType.SAVINGS;
    }
    else if (MmRB.isSelected())
    {
        return AccountType.MONEY_MARKET;
    }
    else if (CdRB.isSelected())
    {
        return AccountType.CD;
    }
    OutputTextArea.setText("Error: No account type selected.");
    return null;
}

//checks if person is 18 years or older
private boolean isEighteenOrOlder(Date dob)
{
    // Current date
    Date currentDate = getCurrentDate();
    // Check if at least 18 years old
    if (currentDate.getYear() - dob.getYear() > 18)
    {
        return true;
    }
    else if (currentDate.getYear() - dob.getYear() == 18)
    {
        // Check month and day
        if (currentDate.getMonth() > dob.getMonth())
        {
            return true;
        }
        else if (currentDate.getMonth() == dob.getMonth())
        {
            return currentDate.getDay() >= dob.getDay();
        }
    }
    return false;
}

//gets current date
private Date getCurrentDate()
{
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH) + 1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    return new Date(month, day, year);
}

//creates account based on type
private Account createAccountByType(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit
)
{
    Account account = null;
    switch (accountType)
    {
        case CHECKING:
            account = createCheckingAccount(accountType, branch, profile, initialDeposit);
            break;
        case COLLEGE_CHECKING:
            Campus campus = getSelectedCampus();
            account = createCollegeCheckingAccount(accountType, branch, profile, initialDeposit, campus);
            break;
        case SAVINGS:
            account = createSavingsAccount(accountType, branch, profile, initialDeposit);
            break;
        case MONEY_MARKET:
            account = createMoneyMarketAccount(accountType, branch, profile, initialDeposit);
            break;
        case CD:
            int term = getSelectedTerm();
            Date openingDate = getOpenDate();
            account = createCertificateDepositAccount(accountType, branch, profile, initialDeposit, term, openingDate);
            break;
    }
    return account;
}

//creates checking account
private Account createCheckingAccount(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit
)
{
    if (accDatabase.containsSameAccountType(profile, accountType.getCode()))
    {
        OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
        return null;
    }
    AccountNumber number = new AccountNumber(branch, accountType);
    return new Checking(number, profile, initialDeposit);
}

//get selected campus
private Campus getSelectedCampus()
{
    if(NbRB.isSelected())
    {
        return Campus.NEW_BRUNSWICK;
    }
    else if (NewarkRB.isSelected())
    {
        return Campus.NEWARK;
    }
    else if (CamdenRB.isSelected())
    {
        return Campus.CAMDEN;
    }
    OutputTextArea.setText("Error: No campus selected.");
    return null;
}

//creates college checking account
private Account createCollegeCheckingAccount(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit,
        Campus campus
)
{
    if (accDatabase.containsSameAccountType(profile, accountType.getCode()))
    {
        OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
        return null;
    }
    if (!isEligibleForCollegeChecking(profile.getDob()))
    {
        OutputTextArea.setText("Not eligible to open: " + profile.getDob() + " over 24.");
        return null;
    }
    if (campus == null)
    {
        OutputTextArea.setText("Invalid campus code.");
        return null;
    }
    AccountNumber number = new AccountNumber(branch, accountType);
    return new CollegeChecking(number, profile, initialDeposit, campus);
}

//checks if 24 years or younger
private boolean isEligibleForCollegeChecking(Date dob)
{
    Date currentDate = getCurrentDate();
    if (currentDate.getYear() - dob.getYear() < 24)
    {
        return true;
    }
    else if (currentDate.getYear() - dob.getYear() == 24)
    {
        if (currentDate.getMonth() < dob.getMonth())
        {
            return true;
        }
        else if (currentDate.getMonth() == dob.getMonth())
        {
            return currentDate.getDay() <= dob.getDay();
        }
    }
    return false;
}

//creates savings account
private Account createSavingsAccount(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit
)
{
    if (accDatabase.containsSameAccountType(profile, accountType.getCode()))
    {
        OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
        return null;
    }
    AccountNumber number = new AccountNumber(branch, accountType);
    Savings account = new Savings(number, profile, initialDeposit);
    if (accDatabase.holderHasCheckingAccount(profile))
    {
        account.setLoyal(true);
    }
    return account;
}

//creates money market account
private Account createMoneyMarketAccount(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit
)
{
    if (accDatabase.containsSameAccountType(profile, accountType.getCode()))
    {
        OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
        return null;
    }
    if (initialDeposit < MM_INITIAL_DEPO)
    {
        OutputTextArea.setText("Minimum of $2,000 to open a Money Market account.");
        return null;
    }
    AccountNumber number = new AccountNumber(branch, accountType);
    MoneyMarket account = new MoneyMarket(number, profile, initialDeposit);
    account.setLoyal(initialDeposit >= 5000);
    return account;
}

//gets selected term
private int getSelectedTerm()
{
    String strterm = ((ComboBox<String>)TermComboBox).getValue();
    int term = Integer.parseInt(strterm);
    return term;
}

//gets open date
private Date getOpenDate()
{
    LocalDate localOpenDate = DateOpenedDatePicker.getValue();
    Date openDate = new Date(localOpenDate.getMonthValue(),
            localOpenDate.getDayOfMonth(),
            localOpenDate.getYear());
    return openDate;
}

//creates CD account
private Account createCertificateDepositAccount(
        AccountType accountType,
        Branch branch,
        Profile profile,
        double initialDeposit,
        int term,
        Date openedDate
)
{
    if (term <= 0)
    {
        OutputTextArea.setText(term + " is not a valid term.");
        return null;
    }
    if (accDatabase.containsSameAccountType(profile, accountType.getCode(), term))
    {
        OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType.getName() + " account.");
        return null;
    }
    if (initialDeposit < CD_INITIAL_DEPO)
    {
        OutputTextArea.setText("Minimum of $1,000 to open a Certificate Deposit account.");
        return null;
    }
    if (openedDate == null || !openedDate.isValid())
    {
        OutputTextArea.setText("CD opening date " + openedDate.toString() + " is invalid.");
        return null;
    }
    AccountNumber number = new AccountNumber(branch, accountType);
    return new CertificateDeposit(number, profile, initialDeposit, term, openedDate);
}

//handle deposit button click
@FXML
private void handleDeposit()
{
    try
    {
        OutputTextArea.clear();
        String accountNumberStr = AccountNumberText.getText().trim();
        if (accountNumberStr.isEmpty())
        {
            OutputTextArea.setText("Error: Account number is required.");
            return;
        }
        Account account = accDatabase.getAccountByNumber(accountNumberStr);
        if (account == null)
        {
            OutputTextArea.setText(accountNumberStr + " does not exist.");
            return;
        }
        if (account instanceof CertificateDeposit)
        {
            OutputTextArea.setText("Cannot deposit to a CD account.");
            return;
        }
        double amount;
        try
        {
            amount = Double.parseDouble(AmountText.getText().trim());
            if (amount <= 0)
            {
                OutputTextArea.setText(amount + " - deposit amount cannot be 0 or negative.");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            OutputTextArea.setText("For input string: \"" + AmountText.getText().trim() + "\" - not a valid amount.");
            return;
        }
        Date today = getCurrentDate();
        Branch branch = account.getNumber().getBranch();
        Activity activity = new Activity(today, branch, 'D', amount, false);
        accDatabase.deposit(account.getNumber(), amount);
        account.addActivity(activity);
        OutputTextArea.setText("$" + String.format("%,.2f", amount) + " deposited to " + account.getNumber());
        AmountText.clear();
        AccountNumberText.clear();
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle withdraw button click
@FXML
private void handleWithdraw()
{
    try
    {
        OutputTextArea.clear();
        String accountNumberStr = AccountNumberText.getText().trim();
        if (accountNumberStr.isEmpty())
        {
            OutputTextArea.setText("Error: Account number is required.");
            return;
        }
        Account account = accDatabase.getAccountByNumber(accountNumberStr);
        if (account == null)
        {
            OutputTextArea.setText(accountNumberStr + " does not exist.");
            return;
        }
        if (account instanceof CertificateDeposit)
        {
            OutputTextArea.setText("Cannot withdraw from a CD account.");
            return;
        }
        double amount;
        try
        {
            amount = Double.parseDouble(AmountText.getText().trim());
            if (amount <= 0)
            {
                OutputTextArea.setText(amount + " withdrawal amount cannot be 0 or negative.");
                return;
            }
        }
        catch (NumberFormatException e)
        {
            OutputTextArea.setText("For input string: \"" + AmountText.getText().trim() + "\" - not a valid amount.");
            return;
        }
        if (account.getBalance() < amount && account.getAccountType().equals("03"))
        {
            OutputTextArea.setText(accountNumberStr + " balance below $2,000 - withdrawing $" +
                    String.format("%,.2f", amount) + " - insufficient funds.");
            return;
        }
        boolean success = accDatabase.withdraw(account.getNumber(), amount);
        if (!success)
        {
            OutputTextArea.setText(amount + " not withdrawn from " + accountNumberStr);
            return;
        }
        Date today = getCurrentDate();
        Branch branch = account.getNumber().getBranch();
        Activity activity = new Activity(today, branch, 'W', amount, false);
        account.addActivity(activity);
        if (account.getBalance() < 2000 && account.getAccountType().equals("03"))
        {
            OutputTextArea.setText(accountNumberStr + " balance below $2,000 - $" +
                    String.format("%,.2f", amount) + " withdrawn from " + accountNumberStr);
        }
        else
        {
            OutputTextArea.setText("$" + String.format("%,.2f", amount) + " withdrawn from " + accountNumberStr);
        }
        AmountText.clear();
        AccountNumberText.clear();
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle close account button click
@FXML
private void handleCloseAccount()
{
    try
    {
        OutputTextArea.clear();
        LocalDate closingLocalDate = ClosingDateDatePicker.getValue();
        if (closingLocalDate == null)
        {
            OutputTextArea.setText("Error: Closing date is required.");
            return;
        }
        Date closeDate = new Date(
                closingLocalDate.getMonthValue(),
                closingLocalDate.getDayOfMonth(),
                closingLocalDate.getYear()
        );
        if (!closeDate.isValid())
        {
            OutputTextArea.setText("Invalid closing date.");
            return;
        }
        String accountNumberStr = AccountNumberText.getText().trim();
        if (accountNumberStr.isEmpty())
        {
            OutputTextArea.setText("Error: Account number is required.");
            return;
        }
        String result = accDatabase.closeAccount(accountNumberStr, closeDate);
        OutputTextArea.setText(result);
        AccountNumberText.clear();
        ClosingDateDatePicker.setValue(null);
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle close all accounts button click
@FXML
private void handleCloseAllAccounts()
{
    try
    {
        OutputTextArea.clear();
        LocalDate closingLocalDate = ClosingDateDatePicker.getValue();
        if (closingLocalDate == null)
        {
            OutputTextArea.setText("Error: Closing date is required.");
            return;
        }
        Date closeDate = new Date(
                closingLocalDate.getMonthValue(),
                closingLocalDate.getDayOfMonth(),
                closingLocalDate.getYear()
        );
        if (!closeDate.isValid())
        {
            OutputTextArea.setText("Invalid closing date.");
            return;
        }
        String firstName = ClosingFirstNText.getText().trim();
        String lastName = ClosingLastNText.getText().trim();
        if (firstName.isEmpty())
        {
            OutputTextArea.setText("Error: First name is required.");
            return;
        }
        if (lastName.isEmpty())
        {
            OutputTextArea.setText("Error: Last name is required.");
            return;
        }
        LocalDate dobLocalDate = ClosingDOBDatePicker.getValue();
        if (dobLocalDate == null)
        {
            OutputTextArea.setText("Error: Date of birth is required.");
            return;
        }
        Date dob = new Date(
                dobLocalDate.getMonthValue(),
                dobLocalDate.getDayOfMonth(),
                dobLocalDate.getYear()
        );
        if (!dob.isValid())
        {
            OutputTextArea.setText("Date of birth " + dob + " is invalid.");
            return;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        String result = accDatabase.closeAllAccounts(profile, closeDate);
        OutputTextArea.setText(result);
        ClosingFirstNText.clear();
        ClosingLastNText.clear();
        ClosingDOBDatePicker.setValue(null);
        ClosingDateDatePicker.setValue(null);
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle load accounts file button click
@FXML
private void handleLoadAccountsFile()
{
    try
    {
        OutputTextArea.clear();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Accounts File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File initialDirectory = new File("./resources");
        if (initialDirectory.exists())
        {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        Stage stage = (Stage) RootPanel.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null)
        {
            try
            {
                accDatabase.loadAccounts(selectedFile);
                OutputTextArea.setText("Accounts in \"" + selectedFile.getName() + "\" loaded to the database.");
            }
            catch (IOException e)
            {
                OutputTextArea.setText("Error loading accounts: " + e.getMessage());
            }
        }
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle load activities file button click
@FXML
private void handleLoadActivitesFile()
{
    try
    {
        OutputTextArea.clear();
        OutputTextArea.setText("Select activities file...");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Activities File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        File initialDirectory = new File("./resources");
        if (initialDirectory.exists())
        {
            fileChooser.setInitialDirectory(initialDirectory);
        }
        Stage stage = (Stage) RootPanel.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null)
        {
            try
            {
                OutputTextArea.setText("Processing \"" + selectedFile.getName() + "\"...");
                accDatabase.processActivity(selectedFile);
                OutputTextArea.setText("Account activities in \"" + selectedFile.getName() + "\" processed.");
            }
            catch (IOException e)
            {
                OutputTextArea.setText("Error processing activities: " + e.getMessage());
            }
        }
        else
        {
            OutputTextArea.setText("No file selected.");
        }
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by holder button click
@FXML
private void handlePrintByHolder()
{
    try
    {
        StringBuffer output = new StringBuffer();
        accDatabase.printByHolder();
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by type button click
@FXML
private void handlePrintByType()
{
    try
    {
        StringBuffer output = new StringBuffer();
        accDatabase.printByType();
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print by branch button click
@FXML
private void handlePrintByBranch()
{
    try
    {
        StringBuffer output = new StringBuffer();
        accDatabase.printByBranch();
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print statements button click
@FXML
private void handlePrintStatements()
{
    try
    {
        StringBuffer output = new StringBuffer();
        accDatabase.printStatements();
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle print archive button click
@FXML
private void handlePrintArchive()
{
    try
    {
        StringBuffer output = new StringBuffer();
        accDatabase.printArchive();
        OutputTextArea.setText(output.toString());
    }
    catch (Exception e)
    {
        OutputTextArea.setText("Error: " + e.getMessage());
    }
}

//handle cancel button click - clears all inputs
@FXML
private void handleCancel()
{
    clearInputFields();
}

//clear input fields after successful account creation
private void clearInputFields()
{
    FirstNameTextField.clear();
    LastNameText.clear();
    DobDatePicker.setValue(null);
    InitialDepositText.clear();
    DateOpenedDatePicker.setValue(null);
    LoyalCheckBox.setSelected(false);
}
}