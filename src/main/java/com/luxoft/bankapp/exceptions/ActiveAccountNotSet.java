package com.luxoft.bankapp.exceptions;

public class ActiveAccountNotSet extends RuntimeException {
    private String clientName;

    public ActiveAccountNotSet(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public String getMessage() {
        return "Active account not set for " + clientName;
    }
}
