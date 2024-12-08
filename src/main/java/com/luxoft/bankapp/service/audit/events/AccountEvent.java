package com.luxoft.bankapp.service.audit.events;

import org.springframework.context.ApplicationEvent;

public abstract class AccountEvent extends ApplicationEvent {
    private long accountId;

    public AccountEvent(Object source, long accountId) {
        super(source);
        this.accountId = accountId;
    }

    public long getAccountId() {
        return accountId;
    }
}
