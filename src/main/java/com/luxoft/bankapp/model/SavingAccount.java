package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Map;

public class SavingAccount extends AbstractAccount {

    public SavingAccount(double initialBalance) {
        super(AccountType.SAVING);

        if (initialBalance >= 0) {
            setBalance(initialBalance);
        }
    }

    @Override
    public void withdraw(double amount) {
        if (getBalance() < amount) {
            throw new NotEnoughFundsException(amount);
        }
    }

    // TODO feed
    @Override
    public void parseFeed(Map<String, String> map) {
        super.parseFeed(map);
    }
}
