package com.luxoft.bankapp.service.operations;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

public interface BankingOperationsService {
    double getBalance(Client client);

    double getBalance(Account account);

    void deposit(Client client, double amount);

    void deposit(Account account, double amount);

    void withdraw(Client client, double amount);

    void withdraw(Account client, double amount);
}
