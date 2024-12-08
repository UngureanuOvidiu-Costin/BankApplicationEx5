package com.luxoft.bankapp.service.feed;

import java.io.File;

public interface BankFeedService {
    void loadFeed(String folder);

    void loadFeed(File file);

    void saveFeed(String file);
}
