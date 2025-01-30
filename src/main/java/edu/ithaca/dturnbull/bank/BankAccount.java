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
     * withdraws money from this bank account
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
     * deposits money in this bank account
     * @param amount the amount of money to deposit
     * @post increases the balance by amount if amount is non-negative
     */
    public void deposit (double amount) throws InsufficientFundsException{
        if (!isAmountValid(amount)) throw new IllegalArgumentException("Amount: " + amount + " is invalid"); 
        balance += amount;
    }

    /**
     * transfers money from this count to another
     * @param other the account to transfer money to
     * @param amount the amount of money to transfer
     * @post increases the balance of the other account and decreases the balance of this account by the amount if it is non-negative and less than the balance of this account
     */
    public void transfer (BankAccount other, double amount) throws InsufficientFundsException{
        if (this == other) throw new IllegalArgumentException("Cannot transfer to the same account");
        if (other == null) throw new IllegalArgumentException("Cannot transfer to a null account");
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
        if (allowedPunctuation.contains(part.charAt(0)) || allowedPunctuation.contains(part.charAt(part.length()-1))) return false; // make sure the first and last character are alphanumeric
        for (int i = 0; i < part.length(); i++) {
            if (!Character.isLetterOrDigit(part.charAt(i)) && !allowedPunctuation.contains(part.charAt(i))) return false; // make sure each character is alphanumeric or allowed
            if (allowedPunctuation.contains(part.charAt(i)) && allowedPunctuation.contains(part.charAt(i-1))) return false; // make sure no two allowed punctuation appear in a row
        }
        return true;
    }

    /*
     * checks the validity of an email address
     * @param email the email address to check
     * @return true if the email address is valid
     */
    public static boolean isEmailValid(String email){
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[0].length() == 0 || parts[1].length() == 0) return false; // checks for length on both sides of the @
        List<Character> allowedPunctuation = List.of('-', '.', '_'); // the prefix is allowed these 3 punctuations
        if (!checkPart(parts[0], allowedPunctuation)) return false;

        String[] domainParts = parts[1].split("\\.");
        if (domainParts.length != 2 || domainParts[0].length() == 0 || domainParts[1].length() == 0) return false; // check for length in both parts of the domain

        List<Character> allowedPunctuationDomain = List.of('-'); // the domain is allowed only this punctuation (in a list for easy reuse of the checkPart method)
        if (!checkPart(domainParts[0], allowedPunctuationDomain)) return false;

        if (domainParts[1].length() < 2) return false; // top-level domain must be at least 2 characters

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