package com.luxoft.bankapp;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.operations.BankingOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests3 {
    @Autowired
    private Banking banking;

    @Autowired
    private BankingOperationsService bankingOperationsService;

    private Client client;

    @BeforeEach
    public void init() {
        client = banking.getClient("Jonny Bravo");
        client.setDefaultActiveAccountIfNotSet();
        client.getActiveAccount().setId(999);
    }

    @Test
    public void depositToClient1() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(client, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void depositToClient2() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;

        bankingOperationsService.deposit(account, amount);

        assertEquals(account.getBalance(), balance + amount);
    }

    @Test
    public void getClientBalance() {
        Account account = client.getActiveAccount();

        double balance = bankingOperationsService.getBalance(client);

        assertEquals(balance, account.getBalance());
    }

    @Test
    public void withdrawFromClient1() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(client, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient2() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double amount = 100;


        bankingOperationsService.withdraw(account, amount);

        assertEquals(account.getBalance(), balance - amount);
    }

    @Test
    public void withdrawFromClient3() {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        double overdraft = 0;

        if (account instanceof CheckingAccount) {
            overdraft = ((CheckingAccount) account).getOverdraft();
        }

        double amount = balance + overdraft + 1000;

        assertThrows(NotEnoughFundsException.class,
                () -> bankingOperationsService.withdraw(account, amount));

        assertEquals(account.getBalance(), balance);
    }

}
