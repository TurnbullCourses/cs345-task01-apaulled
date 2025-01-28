package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        // Equivalence Class: Amount in account is positive
        assertTrue(bankAccount.getBalance() > 0); // border case: amount is positive
        assertFalse(bankAccount.getBalance() < 0); // border case: amount is negative

        // test continuous withdrawal
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

        // Equivalence Class: Amount to withdraw has more/less than two decimal places
        BankAccount bankAccount4 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(0.001)); // border case: amount has three decimal places
        bankAccount4.withdraw(0.01); // border case: amount has two decimal places
        assertEquals(199.99, bankAccount4.getBalance(), 0.001);

        // Added 0 border case after feedback:
        BankAccount bankAccount5 = new BankAccount("a@b.com", 200);
        bankAccount5.withdraw(0);
        assertEquals(200, bankAccount5.getBalance(), 0.001);
    }


    @Test
    void isAmountValidTest() {

        // Equivalence Class: Amount is non-negative
        assertTrue(BankAccount.isAmountValid(0.00)); // border case; smallest possible amount
        assertFalse(BankAccount.isAmountValid(-0.01));; // border case; largest possible negative amount

        // Equivalence Class: Amount has at most two decimal places
        assertTrue(BankAccount.isAmountValid(1.01)); // border case; exactly two decimal places
        assertFalse(BankAccount.isAmountValid(1.011)); // border case; just over two decimal places (three)

        // test 1 decimal place
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

        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.c", -1.00));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("a@b.c", 1.001));
    }

    @Test
    void depositTest() throws InsufficientFundsException{

        // Equivalence Class: Amount to deposit is negative
        BankAccount bankAccount3 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.deposit(-0.01)); // border case: amount is just barely negative
        bankAccount3.deposit(0.01); // border case: amount is just barely positive
        assertEquals(200.01, bankAccount3.getBalance(), 0.001);

        // Equivalence Class: Amount to deposit has more/less than two decimal places
        BankAccount bankAccount4 = new BankAccount("a@b.com", 200);
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.deposit(0.001)); // border case: amount has three decimal places
        bankAccount4.deposit(0.01); // border case: amount has two decimal places
        assertEquals(200.01, bankAccount4.getBalance(), 0.001);

        // Added 0 border case after feedback:
        BankAccount bankAccount5 = new BankAccount("a@b.com", 200);
        bankAccount5.deposit(0);
        assertEquals(200, bankAccount5.getBalance(), 0.001);
    }

    @Test
    void transferTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        BankAccount bankAccount2 = new BankAccount("a@b.com", 200);
        bankAccount.transfer(bankAccount2,1);
        assertEquals(199, bankAccount.getBalance(), 0.001); // Valid transfer
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, -0.01)); // border case: amount is just barely negative
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(bankAccount2, 0.001)); // border case: amount is just barely too many decimal places
        assertThrows(InsufficientFundsException.class, () -> bankAccount.transfer(bankAccount2, 200.01)); // border case: amount is just barely too much

        // Added 0 border case after feedback:
        bankAccount.transfer(bankAccount2, 0);
        assertEquals(199, bankAccount.getBalance(), 0.001);
        assertEquals(201, bankAccount2.getBalance(), 0.001);
    }


}