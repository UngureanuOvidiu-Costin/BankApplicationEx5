package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.Storage;

import java.util.Map;
import java.util.Set;

public interface Banking {
    void setStorage(Storage<Client> storage);

    Client addClient(Client c);

    Client getClient(String name);

    Set<Client> getClients();

    void removeClient(Client c);

    Account createAccount(Client c, AccountType type);

    void updateAccount(Client c, Account account);

    Account getAccount(Client c, AccountType type);

    Set<Account> getAllAccounts();

    Set<Account> getAllAccounts(Client c);

    void removeAccount(Client c, AccountType type);

    void transferMoney(Client from, Client to, double amount);

    void parseFeed(Map<String, String> map);
}
