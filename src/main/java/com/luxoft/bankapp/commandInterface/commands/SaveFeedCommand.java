package com.luxoft.bankapp.commandInterface.commands;

import com.luxoft.bankapp.commandInterface.BankCommander;
import com.luxoft.bankapp.service.feed.BankFeedService;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class SaveFeedCommand extends AbstractCommand {

    private BankFeedService feedService;

    public SaveFeedCommand(int num, BankFeedService feedService) {
        super(num);
        this.feedService = feedService;
    }

    @Override
    public void execute() {
        System.out.println("Enter file name to save");
        Scanner s = new Scanner(System.in);
        String fileName = s.nextLine();

        feedService.saveFeed(fileName);
    }
}
