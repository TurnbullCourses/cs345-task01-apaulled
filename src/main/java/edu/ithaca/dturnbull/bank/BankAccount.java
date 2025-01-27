package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (!isEmailValid(email)) throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        if (!isAmountValid(startingBalance)) throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid, cannot create account");
        this.email = email;
        this.balance = startingBalance;
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * @throws InsufficientFundsException if amount is negative or greater than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)) throw new IllegalArgumentException("Amount: " + amount + " is invalid");
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

        /**
     * checks the validity of an amount of money
     * @param amount the amount of money to check
     * @return true if the amount is non-negative and has at most two decimal places
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0 || amount * 100 != (int)(amount * 100)) return false;
        return true;
    }

    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        if (email.contains("#")) {
            return false;
        }
        else {
            return true;
        }
    }
}