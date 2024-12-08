package com.luxoft.bankapp.commandInterface.commands;

public class GetClientsCommand extends AbstractCommand {

    public GetClientsCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Clients:");

        getBanking().getClients().forEach(System.out::println);
    }
}
