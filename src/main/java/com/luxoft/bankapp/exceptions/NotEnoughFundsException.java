package com.luxoft.bankapp.exceptions;

public class NotEnoughFundsException extends BankException {

    private static final long serialVersionUID = 1L;

    protected double amount;

    public NotEnoughFundsException(double amount) {
        this.amount = amount;
    }

    @Override
    public String getMessage() {
        return "Not Enough Funds " + amount;
    }

}
