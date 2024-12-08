package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class TransferCommand extends AbstractCommand {

    public TransferCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter sender name:");

        Scanner s = new Scanner(System.in);
        String senderName = s.nextLine();

        System.out.println("Enter receiver name:");

        s = new Scanner(System.in);
        String receiverName = s.nextLine();

        System.out.println("Enter amount to transfer:");

        s = new Scanner(System.in);
        String amount = s.nextLine();

        Client sender;
        Client receiver;

        try {
            sender = getBanking().getClient(senderName);
            receiver = getBanking().getClient(receiverName);

            getBanking().transferMoney(sender, receiver, Double.parseDouble(amount));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }
}
