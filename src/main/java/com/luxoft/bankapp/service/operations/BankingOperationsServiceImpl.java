package com.luxoft.bankapp.service.operations;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;
import org.springframework.stereotype.Service;

@Service
public class BankingOperationsServiceImpl implements BankingOperationsService {
    @Override
    public double getBalance(Client client) {
        return client.getBalance();
    }

    @Override
    public double getBalance(Account account) {
        return account.getBalance();
    }

    @Override
    public void deposit(Client client, double amount) {
        client.deposit(amount);
    }

    @Override
    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(Client client, double amount) {
        client.withdraw(amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }
}
