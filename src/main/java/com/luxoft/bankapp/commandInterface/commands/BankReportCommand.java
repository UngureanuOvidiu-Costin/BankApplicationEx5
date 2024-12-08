package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.model.Client;

import java.util.List;
import java.util.Map;

public class BankReportCommand extends AbstractCommand {
    private BankReportService bankReportService;

    public BankReportCommand(int num, BankReportService bankReportService) {
        super(num);
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        System.out.println("========== Bank report ====================");
        System.out.println("Number of clients: " + bankReportService.getNumberOfBankClients());
        System.out.println("Number of accounts: " + bankReportService.getAccountsNumber());
        System.out.println("Credit sum: " + bankReportService.getBankCreditSum());

        System.out.println("Clients (by balance): ");

        bankReportService.getClientsSorted().forEach(client ->
        {
            System.out.println("\t");
            System.out.println(client);
        });

        System.out.println("Clients (by city): ");
        Map<String, List<Client>> clientsByCity = bankReportService.getClientsByCity();

        for (String city : clientsByCity.keySet()) {
            System.out.println("\t" + city);
            clientsByCity.get(city).forEach(c -> System.out.println("\t\t" + c));
        }
        System.out.println("============================================= end of report");
    }
}
