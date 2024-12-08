package com.luxoft.bankapp.service.audit.events;

public class WithdrawEvent extends AccountEvent {
    private final double amount;
    private final State state;

    public WithdrawEvent(long accountId, double amount) {
        super("WITHDRAWAL", accountId);
        this.amount = amount;
        this.state = State.TRYING;
    }

    public WithdrawEvent(long accountId, double amount, State state) {
        super("WITHDRAWAL", accountId);
        this.amount = amount;
        this.state = state;
    }

    public double getAmount() {
        return amount;
    }

    public State getState() {
        return state;
    }

    public enum State {
        TRYING, SUCCESSFUL, FAILED
    }
}
