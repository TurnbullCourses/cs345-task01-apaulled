package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{

        // Equivalence Class: Amount is less than balance
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(200);
        assertEquals(0, bankAccount.getBalance(), 0.001); // border case: amount is equal to balance

        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(200.01)); // border case: amount is just barely greater than balance

        // Equivalence Class: Amount to withdraw is negative
        BankAccount bankAccount3 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(-0.01)); // border case: amount is just barely negative
        bankAccount3.withdraw(0.01); // border case: amount is just barely positive
        assertEquals(199.99, bankAccount3.getBalance(), 0.001);
    }

    @Test
    void isEmailValidTest(){

        // Existence vs Non-Existence
        assertFalse(BankAccount.isEmailValid("")); // email doesn't exist
        // ("existence" is tested in other cases)

        /* Prefix Partitions */ 

        // Punctuation on Ends
        assertFalse(BankAccount.isEmailValid("a-@b.com")); // trailing hyphen
        assertFalse(BankAccount.isEmailValid("-a@b.com")); // hyphen first character
        assertTrue(BankAccount.isEmailValid( "a-b@c.com"));   // valid hyphen (in middle)

        // Valid Characters
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid characters
        assertFalse(BankAccount.isEmailValid("a#b@c.com")); // illegal character

        // Consecutive Punctuation
        assertTrue(BankAccount.isEmailValid( "a-b@c.com"));   // valid hyphen (only one)
        assertFalse(BankAccount.isEmailValid("a--b@c.com")); // consecutive hyphens

        /* Domain Partitions */

        // Valid Characters
        assertTrue(BankAccount.isEmailValid( "a@b-c.com"));   // valid characters
        assertFalse(BankAccount.isEmailValid("a@b#c.com")); // illegal character

        // Domain Part Existence
        assertFalse(BankAccount.isEmailValid("a@b")); // missing last part of domain
        assertTrue(BankAccount.isEmailValid( "a@b.com")); // both domain parts present

        // Last Part of Domain Length
        assertFalse(BankAccount.isEmailValid("a@b.c")); // short last part of domain
        assertTrue(BankAccount.isEmailValid("a@b.cc")); // just long enough

    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

}