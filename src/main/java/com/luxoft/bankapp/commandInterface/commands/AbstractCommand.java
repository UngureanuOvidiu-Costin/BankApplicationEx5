package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.service.Banking;

public abstract class AbstractCommand implements Command, Comparable {
    private String commandName;

    private Banking banking;

    public AbstractCommand(int num) {
        this.commandName = num < 10 ? "0" + num : "" + num;
    }

    @Override
    public String getCommandName() {
        return commandName;
    }

    @Override
    public int compareTo(Object o) {
        return this.getCommandName().compareTo(((AbstractCommand) o).getCommandName());
    }

    public Banking getBanking() {
        return banking;
    }

    public void setBanking(Banking banking) {
        this.banking = banking;
    }
}
