package com.luxoft.bankapp.model;

import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.service.feed.Feed;
import com.luxoft.bankapp.service.storage.Storage;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Client implements Identifiable, Serializable {
    private long id;

    @Feed("NAME")
    private String name;

    @Feed("ACCOUNTS")
    private Set<Account> accounts = new HashSet<>(2);

    private Account activeAccount;

    @Feed("GENDER")
    private Gender gender;

    @Feed("CITY")
    private String city;

    private Storage<Client> storage;

    public Client(String name) {
        this(name, Gender.UNDEFINED);
    }

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
    }

    public synchronized double getBalance() {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet(name);
        }

        return activeAccount.getBalance();
    }

    public synchronized void deposit(double amount) {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet(name);
        }

        activeAccount.deposit(amount);
        storage.update(this);
    }

    public synchronized void withdraw(double amount) {
        if (!checkIfActiveAccountSet()) {
            throw new ActiveAccountNotSet(name);
        }

        activeAccount.withdraw(amount);
        storage.update(this);
    }

    private boolean checkIfActiveAccountSet() {
        return activeAccount != null;
    }

    public void removeAccount(AccountType type) {
        accounts = accounts.stream()
                .filter(a -> a.getType() != type)
                .collect(Collectors.toSet());
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts.clear();
        this.accounts.addAll(accounts);
    }

    public Set<Account> getAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    public Account getAccount(AccountType type) {
        for (Account account : accounts) {
            if (account.getType().equals(type)) {
                return account;
            }
        }
        return null;
    }

    public void addAccount(Account account) throws AccountNumberLimitException {
        if (accounts.size() >= 2) {
            throw new AccountNumberLimitException();
        }
        if (account != null) {
            accounts.add(account);
        }
    }

    public void setDefaultActiveAccountIfNotSet() {
        if (activeAccount == null && accounts != null && !accounts.isEmpty()) {
            Account account = getAccount(AccountType.CHECKING);

            if (account == null) {
                account = accounts.iterator().next();
            }

            activeAccount = account;
            storage.update(this);

            System.out.println("Default account set for " + name + ":" + activeAccount.getType());
        }
    }

    public void parseFeed(Map<String, String> map) {
        this.city = map.get("CITY");
        this.gender = Client.Gender.valueOf(map.get("GENDER"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private StringBuilder getSimpleInfoBuilder() {
        StringBuilder builder = new StringBuilder();

        builder.append("\nClient: ")
                .append(name)
                .append("\nGender: ")
                .append(getGender());

        return builder;
    }

    @Override
    public String toString() {
        StringBuilder builder = getSimpleInfoBuilder();

        builder.append("\nAccounts:");

        for (Account account : accounts) {
            builder.append(account.toString());
        }

        builder.append("\nActive account: ");

        builder.append(checkIfActiveAccountSet() ? activeAccount.getType() : "not set");

        return builder.toString();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getActiveAccount() {
        return activeAccount;
    }

    public void setActiveAccount(Account activeAccount) {
        this.activeAccount = activeAccount;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public enum Gender {
        MALE("Mr"), FEMALE("Ms"), UNDEFINED("");

        private String prefix;

        String getSalutation() {
            return prefix;
        }

        Gender(String prefix) {
            this.prefix = prefix;
        }

        public static Gender parse(String s) {
            if ("m".equals(s)) {
                return MALE;
            } else if ("f".equals(s)) {
                return FEMALE;
            }
            return UNDEFINED;
        }
    }

    public void setStorage(Storage<Client> storage) {
        this.storage = storage;
    }
}
