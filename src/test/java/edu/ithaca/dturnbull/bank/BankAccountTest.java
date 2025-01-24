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
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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
        assertFalse(BankAccount.isEmailValid("a@b.cc")); // just long enough

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