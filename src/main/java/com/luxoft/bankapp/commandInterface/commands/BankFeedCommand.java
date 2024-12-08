package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.service.feed.BankFeedService;

import java.util.Scanner;

public class BankFeedCommand extends AbstractCommand {

    private BankFeedService feedService;

    public BankFeedCommand(int num, BankFeedService feedService) {
        super(num);
        this.feedService = feedService;
    }

    @Override
    public void execute() {
        System.out.println("Enter folder");

        Scanner s = new Scanner(System.in);
        String folder = s.nextLine();

        feedService.loadFeed(folder);
    }
}
