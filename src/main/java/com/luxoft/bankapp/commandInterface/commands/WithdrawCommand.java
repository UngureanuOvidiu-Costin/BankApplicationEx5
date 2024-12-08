package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.exceptions.ActiveAccountNotSet;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class WithdrawCommand extends AbstractCommand {

    public WithdrawCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter client name:");

        Scanner s = new Scanner(System.in);
        String clientName = s.nextLine();

        System.out.println("How much do you want to withdraw?");

        s = new Scanner(System.in);
        String amount = s.nextLine();

        Client client;

        try {
            client = getBanking().getClient(clientName);
            client.withdraw(Double.parseDouble(amount));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
