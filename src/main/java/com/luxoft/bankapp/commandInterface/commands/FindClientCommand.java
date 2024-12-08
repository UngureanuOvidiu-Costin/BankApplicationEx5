package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class FindClientCommand extends AbstractCommand {

    public FindClientCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {

        System.out.println("Enter name of the client");
        Scanner s = new Scanner(System.in);

        String clientName = s.nextLine();

        try {
            Client client = getBanking().getClient(clientName);
            System.out.println(client);
        } catch (ClientNotFoundException e) {
            System.out.println("Client wasn't found");
        }
    }
}
