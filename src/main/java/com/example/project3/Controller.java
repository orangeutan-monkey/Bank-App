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
import com.example.project3.bank.Account;
import com.example.project3.bank.AccountDatabase;
import com.example.project3.bank.AccountNumber;
import com.example.project3.bank.AccountType;
import com.example.project3.bank.Activity;
import com.example.project3.bank.Archive;
import com.example.project3.bank.Branch;
import com.example.project3.bank.Campus;
import com.example.project3.util.Date;
import com.example.project3.bank.CertificateDeposit;
import com.example.project3.bank.Checking;
import com.example.project3.bank.CollegeChecking;
import com.example.project3.bank.MoneyMarket;
import com.example.project3.bank.Profile;
import com.example.project3.bank.Savings;
/*
* This is the controller file that deals with the bank application. this deals with all
* the ui interactions with the account database and associated files
* @author
*   Anirudh Deveram
*   Karthik Penumetch
*/
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
    private void initialize()
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
    /*
    //handle opening an account when open button is clicked
    @FXML
    private void handleOpenAccount()
    {
        try
        {
            OutputTextArea.clear();
            AccountType accountType = getSelectedAccountType();
            String branchStr = ((ComboBox<String>)BranchComboBox).getValue();
            Branch branch = Branch.valueOf(branchStr);
            String firstName = FirstNameTextField.getText().trim();
            String lastName = LastNameText.getText().trim();
            if (firstName.isEmpty() || lastName.isEmpty())
            {
                OutputTextArea.setText("Error: First name and last name are required.");
                return;
            }

            LocalDate localDob = DobDatePicker.getValue();
            if (localDob == null)
            {
                OutputTextArea.setText("Error: Date of birth is required.");
                return;
            }
            /*
            LocalDate localDob = DobDatePicker.getValue();
            System.out.println("Date picker value: " + localDob); // Debug output
            if (localDob == null)
            {
                OutputTextArea.setText("Error: Date of birth is required.");
                return;
            }


            Date dob = Date.fromLocalDate(localDob);
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
            OutputTextArea.setText(accountType + " account " + account.getNumber() + " has been opened.");
            clearInputFields();
        }
        catch (Exception e)
        {
            OutputTextArea.setText("Error: " + e.getMessage());
        }
    }

*/
    //handle opening an account when open button is clicked
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
                OutputTextArea.setText("Error: First name and last name are required.");
                return;
            }

            // Get date of birth - directly try to parse from text field if picker returns null
            LocalDate localDob = DobDatePicker.getValue();
            if (localDob == null)
            {
                // Try to parse from text field
                String dateText = DobDatePicker.getEditor().getText().trim();
                if (dateText.isEmpty())
                {
                    OutputTextArea.setText("Error: Date of birth is required.");
                    return;
                }

                try
                {
                    // Parse MM/dd/yyyy format
                    String[] parts = dateText.split("/");
                    if (parts.length == 3)
                    {
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        // Create date objects directly
                        localDob = LocalDate.of(year, month, day);
                    }
                    else
                    {
                        OutputTextArea.setText("Invalid date format. Please use MM/dd/yyyy.");
                        return;
                    }
                }
                catch (Exception e)
                {
                    OutputTextArea.setText("Invalid date: " + dateText + ". Please use MM/dd/yyyy format.");
                    return;
                }
            }

            Date dob = Date.fromLocalDate(localDob);
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
            OutputTextArea.setText(accountType + " account " + account.getNumber() + " has been opened.");
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
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.YEAR)
        );

        // Check if at least 18 years old
        if (currentDate.year - dob.year > 18)
        {
            return true;
        }
        else if (currentDate.year - dob.year == 18)
        {
            // Check month and day
            if (currentDate.month > dob.month)
            {
                return true;
            }
            else if (currentDate.month == dob.month)
            {
                return currentDate.day >= dob.day;
            }
        }
        return false;
    }

    //get current date
    private Date getCurrentDate()
    {
        LocalDate now = LocalDate.now();
        return Date.fromLocalDate(now);
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
        if (containsSameAccountType(profile, accountType.getCode()))
        {
            OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType + " account.");
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
        if (containsSameAccountType(profile, accountType.getCode()))
        {
            OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType + " account.");
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
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date(
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.YEAR)
        );

        if (currentDate.year - dob.year < 24)
        {
            return true;
        }
        else if (currentDate.year - dob.year == 24)
        {
            if (currentDate.month < dob.month)
            {
                return true;
            }
            else if (currentDate.month == dob.month)
            {
                return currentDate.day <= dob.day;
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
        if (containsSameAccountType(profile, accountType.getCode()))
        {
            OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType + " account.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        boolean isLoyal = LoyalCheckBox.isSelected() || holderHasCheckingAccount(profile);
        return new Savings(number, profile, initialDeposit, isLoyal);
    }

    //creates money market account
    private Account createMoneyMarketAccount(
            AccountType accountType,
            Branch branch,
            Profile profile,
            double initialDeposit
    )
    {
        if (containsSameAccountType(profile, accountType.getCode()))
        {
            OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType + " account.");
            return null;
        }
        if (initialDeposit < MM_INITIAL_DEPO)
        {
            OutputTextArea.setText("Minimum of $2,000 to open a Money Market account.");
            return null;
        }
        AccountNumber number = new AccountNumber(branch, accountType);
        boolean isLoyal = initialDeposit >= 5000;
        return new MoneyMarket(number, profile, initialDeposit, isLoyal);
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
        if (localOpenDate == null)
        {
            // Try to parse from text field
            String dateText = DateOpenedDatePicker.getEditor().getText().trim();
            if (!dateText.isEmpty())
            {
                try
                {
                    // Parse MM/dd/yyyy format
                    String[] parts = dateText.split("/");
                    if (parts.length == 3)
                    {
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        // Create date objects directly
                        localOpenDate = LocalDate.of(year, month, day);
                    }
                }
                catch (Exception e)
                {
                    // Use current date if parsing fails
                    return getCurrentDate();
                }
            }
            else
            {
                return getCurrentDate();
            }
        }
        return Date.fromLocalDate(localOpenDate);
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
        if (containsSameAccountType(profile, accountType.getCode(), term))
        {
            OutputTextArea.setText(profile.getFname() + " " + profile.getLname() + " already has a " + accountType + " account with " + term + " months term.");
            return null;
        }
        if (initialDeposit < CD_INITIAL_DEPO)
        {
            OutputTextArea.setText("Minimum of $1,000 to open a Certificate Deposit account.");
            return null;
        }
        if (openedDate == null || !openedDate.isValid())
        {
            OutputTextArea.setText("CD opening date " + openedDate + " is invalid.");
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
            deposit(account.getNumber(), amount);
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

            if (account instanceof MoneyMarket && account.getBalance() < amount)
            {
                OutputTextArea.setText(accountNumberStr + " balance below $2,000 - withdrawing $" +
                        String.format("%,.2f", amount) + " - insufficient funds.");
                return;
            }

            boolean success = withdraw(account.getNumber(), amount);
            if (!success) {
                OutputTextArea.setText("Withdrawal failed - insufficient funds.");
                return;
            }

            if (account instanceof MoneyMarket)
            {
                MoneyMarket mm = (MoneyMarket) account;
                mm.incrementWithdrawals();
                // Only use this if your MoneyMarket class has these methods
                if (mm.getLoyalStatus() && account.getBalance() < 5000)
                {
                    mm.setLoyalStatus(false);
                }
            }

            Date today = getCurrentDate();
            Branch branch = account.getNumber().getBranch();
            Activity activity = new Activity(today, branch, 'W', amount, false);
            account.addActivity(activity);

            if (account instanceof MoneyMarket && account.getBalance() < 2000)
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
                // Try to parse from text field
                String dateText = ClosingDateDatePicker.getEditor().getText().trim();
                if (dateText.isEmpty())
                {
                    OutputTextArea.setText("Error: Closing date is required.");
                    return;
                }

                try
                {
                    // Parse MM/dd/yyyy format
                    String[] parts = dateText.split("/");
                    if (parts.length == 3)
                    {
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        // Create date objects directly
                        closingLocalDate = LocalDate.of(year, month, day);
                    }
                    else
                    {
                        OutputTextArea.setText("Invalid date format. Please use MM/dd/yyyy.");
                        return;
                    }
                }
                catch (Exception e)
                {
                    OutputTextArea.setText("Invalid date: " + dateText + ". Please use MM/dd/yyyy format.");
                    return;
                }
            }

            Date closeDate = Date.fromLocalDate(closingLocalDate);
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

            String result = closeAccount(accountNumberStr, closeDate);
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
                // Try to parse from text field
                String dateText = ClosingDateDatePicker.getEditor().getText().trim();
                if (dateText.isEmpty())
                {
                    OutputTextArea.setText("Error: Closing date is required.");
                    return;
                }

                try
                {
                    // Parse MM/dd/yyyy format
                    String[] parts = dateText.split("/");
                    if (parts.length == 3)
                    {
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        // Create date objects directly
                        closingLocalDate = LocalDate.of(year, month, day);
                    }
                    else
                    {
                        OutputTextArea.setText("Invalid date format. Please use MM/dd/yyyy.");
                        return;
                    }
                }
                catch (Exception e)
                {
                    OutputTextArea.setText("Invalid date: " + dateText + ". Please use MM/dd/yyyy format.");
                    return;
                }
            }

            Date closeDate = Date.fromLocalDate(closingLocalDate);
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
                // Try to parse from text field
                String dateText = ClosingDOBDatePicker.getEditor().getText().trim();
                if (dateText.isEmpty())
                {
                    OutputTextArea.setText("Error: Date of birth is required.");
                    return;
                }

                try
                {
                    // Parse MM/dd/yyyy format
                    String[] parts = dateText.split("/");
                    if (parts.length == 3)
                    {
                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        // Create date objects directly
                        dobLocalDate = LocalDate.of(year, month, day);
                    }
                    else
                    {
                        OutputTextArea.setText("Invalid date format. Please use MM/dd/yyyy.");
                        return;
                    }
                }
                catch (Exception e)
                {
                    OutputTextArea.setText("Invalid date: " + dateText + ". Please use MM/dd/yyyy format.");
                    return;
                }
            }

            Date dob = Date.fromLocalDate(dobLocalDate);
            if (!dob.isValid())
            {
                OutputTextArea.setText("Date of birth " + dob + " is invalid.");
                return;
            }

            Profile profile = new Profile(firstName, lastName, dob);
            String result = closeAllAccounts(profile, closeDate);
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
    private void handleLoadAccountFile()
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
                    accDatabase.processActivity(selectedFile); // Use your actual method name
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

            // Create a string to capture the output
            StringBuilder output = new StringBuilder();
            output.append("*List of accounts ordered by account holder:\n");

            // Print accounts ordered by holder
            for (int i = 0; i < accDatabase.size(); i++)
            {
                output.append(accDatabase.get(i).toString()).append("\n");
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

            // Create a string to capture the output
            StringBuilder output = new StringBuilder();
            output.append("*List of accounts ordered by account type:\n");

            // Print accounts ordered by type
            AccountType currentType = null;
            for (int i = 0; i < accDatabase.size(); i++)
            {
                Account acc = accDatabase.get(i);
                AccountType thisType = acc.getNumber().getType();
                if (currentType == null || !thisType.equals(currentType))
                {
                    currentType = thisType;
                    output.append("Account Type: ").append(currentType).append("\n");
                }
                output.append(acc.toString()).append("\n");
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

            // Create a string to capture the output
            StringBuilder output = new StringBuilder();
            output.append("*List of accounts ordered by branch location (county, city).\n");

            // Print accounts ordered by branch
            String currentCounty = "";
            for (int i = 0; i < accDatabase.size(); i++)
            {
                Account acc = accDatabase.get(i);
                String county = acc.getNumber().getBranch().getCounty();
                if (!county.equalsIgnoreCase(currentCounty))
                {
                    currentCounty = county;
                    output.append("County: ").append(currentCounty).append("\n");
                }
                output.append(acc.toString()).append("\n");
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

            // Call the database method or format the output ourselves
            accDatabase.printStatements(); // If this method exists
            OutputTextArea.setText("Account statements have been printed.");
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
            accDatabase.printArchive(); // If this method exists
            OutputTextArea.setText("Archive has been printed.");
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
    //helper method to check if profile already has account of the same type
    private boolean containsSameAccountType(Profile profile, String accountTypeCode)
    {
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            if (acc.getHolder().equals(profile) &&
                    acc.getNumber().getType().getCode().equals(accountTypeCode))
            {
                return true;
            }
        }
        return false;
    }

    //helper method to check if profile already has CD account with the same term
    private boolean containsSameAccountType(Profile profile, String accountTypeCode, int term)
    {
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            if (acc.getHolder().equals(profile) &&
                    acc.getNumber().getType().getCode().equals(accountTypeCode) &&
                    acc instanceof CertificateDeposit)
            {
                CertificateDeposit cd = (CertificateDeposit) acc;
                if (cd.getTerm() == term)
                {
                    return true;
                }
            }
        }
        return false;
    }

    //helper method to check if profile has a checking account
    private boolean holderHasCheckingAccount(Profile profile)
    {
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            if (acc.getHolder().equals(profile) &&
                    (acc.getNumber().getType() == AccountType.CHECKING ||
                            acc.getNumber().getType() == AccountType.COLLEGE_CHECKING))
            {
                return true;
            }
        }
        return false;
    }

    //helper method to deposit money to an account
    private void deposit(AccountNumber accNumber, double amount)
    {
        Account account = getAccountByNumber(accNumber.toString());
        if (account != null)
        {
            account.deposit(amount);

            // Update loyalty status for MoneyMarket accounts
            if (account instanceof MoneyMarket)
            {
                MoneyMarket mm = (MoneyMarket) account;
                if (account.getBalance() >= 5000)
                {
                    mm.setLoyalStatus(true);
                }
            }
        }
    }

    //helper method to withdraw money from an account
    private boolean withdraw(AccountNumber accNumber, double amount)
    {
        Account account = getAccountByNumber(accNumber.toString());
        if (account != null)
        {
            if (account.getBalance() >= amount)
            {
                account.withdraw(amount);

                // Update loyalty status for MoneyMarket accounts
                if (account instanceof MoneyMarket)
                {
                    MoneyMarket mm = (MoneyMarket) account;
                    mm.incrementWithdrawals();
                    if (account.getBalance() < 5000)
                    {
                        mm.setLoyalStatus(false);
                    }
                }
                return true;
            }
        }
        return false;
    }

    //helper method to get account by account number
    private Account getAccountByNumber(String accountNumber)
    {
        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            if (acc.getNumber().toString().equals(accountNumber))
            {
                return acc;
            }
        }
        return null;
    }

    //helper method to close an account
    private String closeAccount(String accountNumber, Date closeDate)
    {
        Account account = getAccountByNumber(accountNumber);
        if (account == null)
        {
            return accountNumber + " does not exist.";
        }

        // Add to archive and remove from database
        Archive archive = accDatabase.getArchive();
        archive.add(account, closeDate);

        // Generate result message
        String result = "Account " + accountNumber + " has been closed.";
        if (account instanceof CertificateDeposit)
        {
            CertificateDeposit cd = (CertificateDeposit) account;
            result += "\n" + cd.closeAccount(closeDate);
        }

        // Remove the account
        accDatabase.remove(account);

        return result;
    }

    //helper method to close all accounts for a profile
    private String closeAllAccounts(Profile profile, Date closeDate)
    {
        // Find all accounts for this profile
        int count = 0;
        Archive archive = accDatabase.getArchive();

        // We need to create a temporary list to avoid ConcurrentModificationException
        java.util.ArrayList<Account> accountsToClose = new java.util.ArrayList<>();

        for (int i = 0; i < accDatabase.size(); i++)
        {
            Account acc = accDatabase.get(i);
            if (acc.getHolder().equals(profile))
            {
                accountsToClose.add(acc);
                count++;
            }
        }

        // Now close all the accounts found
        for (Account acc : accountsToClose)
        {
            archive.add(acc, closeDate);
            accDatabase.remove(acc);
        }

        if (count == 0)
        {
            return "No accounts found for " + profile + ".";
        }
        else
        {
            return count + " account(s) closed for " + profile + ".";
        }
    }

}