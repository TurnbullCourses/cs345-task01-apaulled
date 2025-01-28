package edu.ithaca.dturnbull.bank;

import java.util.List;

public class BankAccount {

    private String email;
    private double balance;

    // FYI: I commented out some of these methods and replaced them with my own for the sake of the assignment. Kat, if you want to uncomment the originals and finish it feel free.

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    /*public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }*/

    public BankAccount(String email, double startingBalance) {
        if (!isEmailValid(email)) throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        if (!isAmountValid(startingBalance)) throw new IllegalArgumentException("Starting balance: " + startingBalance + " is invalid");
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
     * @param amount the amount of money to withdraw
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
     * @post increases the balance by amount if amount is non-negative
     */
    public void deposit (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)) throw new IllegalArgumentException("Amount: " + amount + " is invalid"); 
        balance += amount;
    }

    /**
     * @param other the account to transfer money to
     * @param amount the amount of money to transfer
     * @post increases the balance of the other account and decreases the balance of this account by the amount if it is non-negative and less than the balance of this account
     */
    public void transfer (BankAccount other, double amount) throws InsufficientFundsException{
        withdraw(amount);
        other.deposit(amount);
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

    // FYI: I replaced isEmailValid with my implementation so that the tests would pass. Kat, if you want to uncomment your version and finish it feel free

    private static boolean checkPart(String part, List<Character> allowedPunctuation) {
        if (allowedPunctuation.contains(part.charAt(0)) || allowedPunctuation.contains(part.charAt(part.length()-1))) return false;
        for (int i = 0; i < part.length(); i++) {
            if (!Character.isLetterOrDigit(part.charAt(i)) && !allowedPunctuation.contains(part.charAt(i))) return false;
            if (allowedPunctuation.contains(part.charAt(i)) && allowedPunctuation.contains(part.charAt(i-1))) return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email){
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) return false;
        List<Character> allowedPunctuation = List.of('-', '.', '_');
        if (!checkPart(parts[0], allowedPunctuation)) return false;

        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2 || domainParts[0].length() == 0 || domainParts[1].length() == 0) return false;

        List<Character> allowedPunctuationDomain = List.of('-');
        if (!checkPart(domainParts[0], allowedPunctuationDomain)) return false;

        if (domainParts[1].length() < 2) return false;

        if (!checkPart(domainParts[1], allowedPunctuationDomain)) return false;
        return true;
    }

    /*
     * checks the validity of an email address
     * @param email the email address to check
     */
    /*public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){
            return false;
        }
        if (email.contains("#")) {
            return false;
        }
        else {
            return true;
        }
    }*/
}