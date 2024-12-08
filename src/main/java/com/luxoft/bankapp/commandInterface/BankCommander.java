package com.luxoft.bankapp.commandInterface;

import com.luxoft.bankapp.commandInterface.commands.AbstractCommand;
import com.luxoft.bankapp.commandInterface.commands.AddClientCommand;
import com.luxoft.bankapp.commandInterface.commands.BankFeedCommand;
import com.luxoft.bankapp.commandInterface.commands.BankReportCommand;
import com.luxoft.bankapp.commandInterface.commands.Command;
import com.luxoft.bankapp.commandInterface.commands.DepositCommand;
import com.luxoft.bankapp.commandInterface.commands.ExitCommand;
import com.luxoft.bankapp.commandInterface.commands.FindClientCommand;
import com.luxoft.bankapp.commandInterface.commands.GetAccountsCommand;
import com.luxoft.bankapp.commandInterface.commands.GetClientsCommand;
import com.luxoft.bankapp.commandInterface.commands.SaveFeedCommand;
import com.luxoft.bankapp.commandInterface.commands.TransferCommand;
import com.luxoft.bankapp.commandInterface.commands.WithdrawCommand;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankReportServiceImpl;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.feed.BankFeedService;
import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;
import com.luxoft.bankapp.service.storage.ClientStorage;
import com.luxoft.bankapp.service.storage.Storage;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class BankCommander {
    private Map<String, Command> commands = new TreeMap<>();

    private Banking banking;
    private Storage<Client> storage;
    private BankReportService reportService;
    private BankFeedService feedService;

    public BankCommander() {
        storage = new ClientStorage();

        banking = new BankingImpl();
        banking.setStorage(storage);

        reportService = new BankReportServiceImpl();
        reportService.setStorage(storage);

        feedService = new BankFeedServiceImpl(banking);

        initCommands();
    }

    private void initCommands() {
        int i = 1;

        registerCommand(new BankReportCommand(i++, reportService));
        registerCommand(new GetClientsCommand(i++));
        registerCommand(new AddClientCommand(i++));
        registerCommand(new BankFeedCommand(i++, feedService));
        registerCommand(new FindClientCommand(i++));
        registerCommand(new GetAccountsCommand(i++));
        registerCommand(new DepositCommand(i++));
        registerCommand(new WithdrawCommand(i++));
        registerCommand(new TransferCommand(i++));
        registerCommand(new SaveFeedCommand(i++, feedService));
        registerCommand(new ExitCommand(i));


    }

    private void registerCommand(AbstractCommand command) {
        command.setBanking(banking);
        commands.put(command.getCommandName(), command);
    }

    public static void main(String args[]) {
        BankCommander commander = new BankCommander();

        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("\n ----------");
            for (Map.Entry<String, Command> command : commander.commands.entrySet()) {
                System.out.print(command.getKey() + ") ");
                command.getValue().printCommandInfo();
            }
            System.out.println("----------\n");

            String commandString = s.nextLine();
            if (commandString == null) {
                continue;
            }
            if (!commander.commands.containsKey(commandString)) {
                System.out.println("Invalid command");
                continue;
            }
            commander.commands.get(commandString).printCommandInfo();
            commander.commands.get(commandString).execute();
        }
    }
}

