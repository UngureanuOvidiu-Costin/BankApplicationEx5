package com.luxoft.bankapp.service.audit.events;

public class BalanceEvent extends AccountEvent {

    public BalanceEvent(long accountId) {

        super("BALANCE CHECK", accountId);

    }
}