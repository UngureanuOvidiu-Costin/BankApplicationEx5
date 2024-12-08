package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class GetAccountsCommand extends AbstractCommand {

    public GetAccountsCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        System.out.println("Enter name of the client");
        Scanner s = new Scanner(System.in);

        String clientName = s.nextLine();
        try {
            Client client = getBanking().getClient(clientName);

            for (Account account : getBanking().getAllAccounts(client)) {
                System.out.println(account);
            }
        } catch (ClientNotFoundException e) {
            System.out.println("Client wasn't found");
        }
    }

}
