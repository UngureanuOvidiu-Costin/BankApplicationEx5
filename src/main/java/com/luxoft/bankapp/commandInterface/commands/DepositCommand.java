package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class DepositCommand extends AbstractCommand {
    public DepositCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter client name:");

        Scanner s = new Scanner(System.in);
        String clientName = s.nextLine();

        System.out.println("How much do you want to deposit?");

        s = new Scanner(System.in);
        String amount = s.nextLine();

        Client client;

        try {
            client = getBanking().getClient(clientName);
            client.deposit(Double.parseDouble(amount));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
