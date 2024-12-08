package com.luxoft.bankapp.service;

import com.luxoft.bankapp.exceptions.AccountNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.*;
import com.luxoft.bankapp.service.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BankingImpl implements Banking {
    @Autowired
    private Storage<Client> storage;

    @Override
    public Client addClient(Client c) {
        Client created = storage.add(c);
        c.setStorage(storage);

        return created;
    }

    @Override
    public Client getClient(String name) {
        Client found = storage.getBy(name);

        if (found != null) {
            return found;
        }

        throw new ClientNotFoundException(name);
    }

    @Override
    public Set<Client> getClients() {
        return storage.getAll();
    }

    @Override
    public void removeClient(Client c) {
        storage.remove(c.getId());
    }

    @Override
    public Account createAccount(Client c, AccountType type) {
        Account account = null;
        Client client = storage.get(c.getId());

        if (client != null) {
            account = new SavingAccount(0);

            if (type == AccountType.CHECKING) {
                account = new CheckingAccount(0);
            }

            client.addAccount(account);

            storage.update(c);
        }

        return account;
    }

    @Override
    public void updateAccount(Client c, Account account) {
        Client toUpdate = storage.get(c.getId());

        if (toUpdate != null) {
            toUpdate.removeAccount(account.getType());
            toUpdate.addAccount(account);

            storage.update(c);
        }
    }

    @Override
    public Account getAccount(Client c, AccountType type) {
        for (Account account : c.getAccounts()) {
            if (type == account.getType()) {
                return account;
            }
        }

        throw new AccountNotFoundException();
    }

    @Override
    public Set<Account> getAllAccounts() {
        Set<Account> accounts = new HashSet<>();

        for (Client client : storage.getAll()) {
            accounts.addAll(client.getAccounts());
        }

        return accounts;
    }

    @Override
    public Set<Account> getAllAccounts(Client c) {
        return storage.get(c.getId()).getAccounts();
    }

    @Override
    public void removeAccount(Client c, AccountType type) {
        Client toUpdate = storage.get(c.getId());

        if (toUpdate != null) {
            toUpdate.removeAccount(type);
            storage.update(toUpdate);
        }
    }

    @Override
    public void transferMoney(Client from, Client to, double amount) {
        from.withdraw(amount);
        to.deposit(amount);
    }

    @Override
    public void setStorage(Storage<Client> storage) {
        this.storage = storage;
    }

    // TODO feed
    public void parseFeed(Map<String, String> map) {
        String name = map.get("NAME");

        Client client = storage.getBy(name);

        if (client == null) {
            client = addClient(new Client(name));
        }

        client.parseFeed(map);

        AccountType type = AccountType.valueOf(map.get("type"));
        Account account = client.getAccount(type);

        if (account == null) {
            account = createAccount(client, type);
        }

        account.parseFeed(map);

        updateAccount(client, account);
    }

}
