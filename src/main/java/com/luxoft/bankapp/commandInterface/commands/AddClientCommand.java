package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.exceptions.AccountNumberLimitException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.Client;

import java.util.Scanner;

public class AddClientCommand extends AbstractCommand {

    public AddClientCommand(int num) {
        super(num);
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name:");
        String name = scanner.nextLine();

        System.out.println("Gender (m/f):");
        Client.Gender gender = Client.Gender.parse(scanner.nextLine());

        Client client = new Client(name, gender);

        client = getBanking().addClient(client);

        while (true) {
            System.out.println("Create account? (y/n)");
            String answer = scanner.nextLine();
            if (!answer.equals("y")) {
                break;
            }

            System.out.println("Type of account (Checking/Saving):");
            String type = scanner.nextLine();
            try {
                AccountType accountType = AccountType.valueOf(type);
                getBanking().createAccount(client, accountType);
                client.setDefaultActiveAccountIfNotSet();
            } catch (AccountNumberLimitException e) {
                System.out.println("Limit of accounts for one client (2) is reached");
                break;
            }
        }
    }

}
