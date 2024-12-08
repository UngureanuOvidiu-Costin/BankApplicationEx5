package com.luxoft.bankapp.commandInterface.commands;

public interface Command {
    void execute();

    String getCommandName();

    default void printCommandInfo() {
        System.out.println(this.getClass().getSimpleName());
    }
}
