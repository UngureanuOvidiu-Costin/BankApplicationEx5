package com.luxoft.bankapp.exceptions;

public class ClientNotFoundException extends RuntimeException {
    private String name;

    public ClientNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "Client " + name + " not found.";
    }
}
