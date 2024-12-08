package com.luxoft.bankapp.service;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BankReportServiceImpl implements BankReportService {
    @Autowired
    private Storage<Client> storage;

    @Override
    public int getNumberOfBankClients() {
        return storage.getAll().size();
    }

    @Override
    public int getAccountsNumber() {
        return storage.getAll()
                .stream()
                .flatMap(c -> c.getAccounts().stream())
                .collect(Collectors.toList()).size();
    }

    @Override
    public List<Client> getClientsSorted() {
        return storage.getAll()
                .stream()
                .sorted(Comparator.comparing(Client::getName))
                .collect(Collectors.toList());
    }

    @Override
    public double getBankCreditSum() {
        return storage.getAll().stream()
                .flatMap(c -> c.getAccounts().stream())
                .filter(a -> a.getType() == AccountType.CHECKING)
                .mapToDouble(Account::getBalance)
                .filter(b -> b < 0)
                .sum();
    }

    @Override
    public Map<String, List<Client>> getClientsByCity() {
        return storage.getAll().stream()
                .collect(Collectors.groupingBy(Client::getCity));
    }

    public void setStorage(Storage<Client> storage) {
        this.storage = storage;
    }
}
