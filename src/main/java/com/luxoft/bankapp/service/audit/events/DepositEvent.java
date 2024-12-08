package com.luxoft.bankapp.service.audit.events;

public class DepositEvent extends AccountEvent {

    private final double amount;

    public DepositEvent(long accountId, double amount) {
        super("DEPOSIT", accountId);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

}