
package chapter12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class AtmGUI extends JFrame {
/**
 * GUI-Based ATM Case Study
 * 
 * Buttons:
 * - Remove Cash: simulates the customer taking cash
 * - Insert Envelope: simulates the customer inserting a deposit envelope
 */


    // -----------------------------
    // ATM DATA
    // -----------------------------
    private final Map<Integer, Account> accounts = new HashMap<>();

    private Account currentAccount;

    // -----------------------------
    // GUI COMPONENTS
    // -----------------------------
    private JTextArea display;

    private JTextField accountNumberField;
    private JPasswordField pinField;

    private JTextField amountField;

    private JButton loginButton;
    private JButton balanceButton;
    private JButton withdrawButton;
    private JButton depositButton;
    private JButton exitButton;

    private JButton removeCashButton;
    private JButton insertEnvelopeButton;

    private JButton clearButton;

    // Current transaction state
    private double pendingWithdrawal = 0;
    private double pendingDeposit = 0;

    public AtmGUI() {

        // Create sample accounts
        accounts.put(1001, new Account(1001, 1234, 5000.00));
        accounts.put(1002, new Account(1002, 5678, 3000.00));

        setTitle("ATM GUI");
        setSize(600, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        setVisible(true);
    }

    // =========================================================
    // CREATE GUI
    // =========================================================

    private void createGUI() {

        setLayout(new BorderLayout(10, 10));

        // ---------------------------------
        // TITLE
        // ---------------------------------

        JLabel title = new JLabel("ATM MACHINE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(0, 70, 140));
        titlePanel.add(title);

        add(titlePanel, BorderLayout.NORTH);

        // ---------------------------------
        // DISPLAY
        // ---------------------------------

        display = new JTextArea();
        display.setEditable(false);
        display.setFont(new Font("Monospaced", Font.PLAIN, 16));
        display.setText(
                "Welcome to the ATM\n\n" +
                "Please enter your account number\n" +
                "and PIN, then click LOGIN."
        );

        JScrollPane scrollPane = new JScrollPane(display);

        add(scrollPane, BorderLayout.CENTER);

        // ---------------------------------
        // INPUT PANEL
        // ---------------------------------

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 5, 5));

        inputPanel.add(new JLabel("Account Number:"));

        accountNumberField = new JTextField();
        inputPanel.add(accountNumberField);

        inputPanel.add(new JLabel("PIN:"));

        pinField = new JPasswordField();
        inputPanel.add(pinField);

        inputPanel.add(new JLabel("Amount:"));

        amountField = new JTextField();
        inputPanel.add(amountField);

        loginButton = new JButton("LOGIN");
        clearButton = new JButton("CLEAR");

        inputPanel.add(loginButton);
        inputPanel.add(clearButton);

        add(inputPanel, BorderLayout.SOUTH);

        // ---------------------------------
        // BUTTON PANEL
        // ---------------------------------

        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        balanceButton = new JButton("Balance");
        withdrawButton = new JButton("Withdraw");
        depositButton = new JButton("Deposit");
        exitButton = new JButton("Exit");

        removeCashButton = new JButton("Remove Cash");
        insertEnvelopeButton = new JButton("Insert Envelope");

        buttonPanel.add(balanceButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(depositButton);

        buttonPanel.add(removeCashButton);
        buttonPanel.add(insertEnvelopeButton);
        buttonPanel.add(exitButton);

        add(buttonPanel, BorderLayout.WEST);

        // ---------------------------------
        // INITIAL BUTTON STATES
        // ---------------------------------

        balanceButton.setEnabled(false);
        withdrawButton.setEnabled(false);
        depositButton.setEnabled(false);
        exitButton.setEnabled(true);

        removeCashButton.setEnabled(false);
        insertEnvelopeButton.setEnabled(false);

        // ---------------------------------
        // EVENT HANDLERS
        // ---------------------------------

        loginButton.addActionListener(e -> login());

        balanceButton.addActionListener(e -> showBalance());

        withdrawButton.addActionListener(e -> startWithdrawal());

        depositButton.addActionListener(e -> startDeposit());

        removeCashButton.addActionListener(e -> removeCash());

        insertEnvelopeButton.addActionListener(e -> insertEnvelope());

        clearButton.addActionListener(e -> clearFields());

        exitButton.addActionListener(e -> exitATM());
    }

    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        try {

            int accountNumber =
                    Integer.parseInt(accountNumberField.getText());

            int pin =
                    Integer.parseInt(new String(pinField.getPassword()));

            Account account = accounts.get(accountNumber);

            if (account != null && account.getPin() == pin) {

                currentAccount = account;

                display.setText(
                        "Login successful!\n\n" +
                        "Welcome, Account #" + accountNumber + "\n\n" +
                        "Please select a transaction."
                );

                balanceButton.setEnabled(true);
                withdrawButton.setEnabled(true);
                depositButton.setEnabled(true);

                loginButton.setEnabled(false);

                accountNumberField.setEnabled(false);
                pinField.setEnabled(false);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid account number or PIN.",
                        "Login Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter numbers only.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================================================
    // BALANCE INQUIRY
    // =========================================================

    private void showBalance() {

        if (currentAccount == null) {
            return;
        }

        display.setText(
                "BALANCE INQUIRY\n\n" +
                "Account Number: " +
                currentAccount.getAccountNumber() +
                "\n\n" +
                String.format(
                        "Available Balance: $%.2f",
                        currentAccount.getBalance()
                )
        );
    }

    // =========================================================
    // WITHDRAWAL
    // =========================================================

    private void startWithdrawal() {

        if (currentAccount == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(amountField.getText());

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Amount must be greater than zero."
                );

                return;
            }

            if (amount > currentAccount.getBalance()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Insufficient funds."
                );

                return;
            }

            pendingWithdrawal = amount;

            display.setText(
                    "WITHDRAWAL\n\n" +
                    String.format(
                            "Requested amount: $%.2f",
                            amount
                    ) +
                    "\n\n" +
                    "Cash is ready.\n" +
                    "Please click REMOVE CASH."
            );

            removeCashButton.setEnabled(true);

            withdrawButton.setEnabled(false);
            depositButton.setEnabled(false);
            balanceButton.setEnabled(false);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid withdrawal amount."
            );
        }
    }

    // =========================================================
    // REMOVE CASH
    // =========================================================

    private void removeCash() {

        if (currentAccount == null || pendingWithdrawal <= 0) {
            return;
        }

        currentAccount.withdraw(pendingWithdrawal);

        display.setText(
                "CASH DISPENSED\n\n" +
                String.format(
                        "You removed: $%.2f",
                        pendingWithdrawal
                ) +
                "\n\n" +
                String.format(
                        "New balance: $%.2f",
                        currentAccount.getBalance()
                ) +
                "\n\nTransaction complete."
        );

        JOptionPane.showMessageDialog(
                this,
                String.format(
                        "Please take your $%.2f cash.",
                        pendingWithdrawal
                ),
                "Cash Dispenser",
                JOptionPane.INFORMATION_MESSAGE
        );

        pendingWithdrawal = 0;

        removeCashButton.setEnabled(false);

        enableTransactionButtons();
    }

    // =========================================================
    // DEPOSIT
    // =========================================================

    private void startDeposit() {

        if (currentAccount == null) {
            return;
        }

        try {

            double amount =
                    Double.parseDouble(amountField.getText());

            if (amount <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Deposit amount must be greater than zero."
                );

                return;
            }

            pendingDeposit = amount;

            display.setText(
                    "DEPOSIT\n\n" +
                    String.format(
                            "Deposit amount: $%.2f",
                            amount
                    ) +
                    "\n\n" +
                    "Place your cash/check in an envelope.\n" +
                    "Then click INSERT ENVELOPE."
            );

            insertEnvelopeButton.setEnabled(true);

            withdrawButton.setEnabled(false);
            depositButton.setEnabled(false);
            balanceButton.setEnabled(false);

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid deposit amount."
            );
        }
    }

    // =========================================================
    // INSERT ENVELOPE
    // =========================================================

    private void insertEnvelope() {

        if (currentAccount == null || pendingDeposit <= 0) {
            return;
        }

        currentAccount.deposit(pendingDeposit);

        display.setText(
                "DEPOSIT ACCEPTED\n\n" +
                String.format(
                        "Amount deposited: $%.2f",
                        pendingDeposit
                ) +
                "\n\n" +
                String.format(
                        "New balance: $%.2f",
                        currentAccount.getBalance()
                ) +
                "\n\nTransaction complete."
        );

        JOptionPane.showMessageDialog(
                this,
                "Deposit envelope received.",
                "Deposit Slot",
                JOptionPane.INFORMATION_MESSAGE
        );

        pendingDeposit = 0;

        insertEnvelopeButton.setEnabled(false);

        enableTransactionButtons();
    }

    // =========================================================
    // ENABLE TRANSACTION BUTTONS
    // =========================================================

    private void enableTransactionButtons() {

        balanceButton.setEnabled(true);
        withdrawButton.setEnabled(true);
        depositButton.setEnabled(true);

        amountField.setText("");
    }

    // =========================================================
    // CLEAR
    // =========================================================

    private void clearFields() {

        accountNumberField.setText("");
        pinField.setText("");
        amountField.setText("");
    }

    // =========================================================
    // EXIT
    // =========================================================

    private void exitATM() {

        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to exit?",
                "Exit ATM",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {

            System.exit(0);
        }
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new AtmGUI();
        });
    }
}


/**
 * Account class
 */
class Account {

    private int accountNumber;
    private int pin;
    private double balance;

    public Account(
            int accountNumber,
            int pin,
            double balance) {

        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public int getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {

        balance += amount;
    }

    public boolean withdraw(double amount) {

        if (amount <= balance) {

            balance -= amount;
            return true;
        }

        return false;
    }
}
