package com.luxoft.bankapp.commandInterface.commands;

public class ExitCommand extends AbstractCommand {

    public ExitCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Good buy!");
        System.exit(0);
    }
}
