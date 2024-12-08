package com.luxoft.bankapp;

import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.BankReportService;
import com.luxoft.bankapp.service.BankReportServiceImpl;
import com.luxoft.bankapp.service.Banking;
import com.luxoft.bankapp.service.BankingImpl;
import com.luxoft.bankapp.service.demo.BankInitializationService;
import com.luxoft.bankapp.service.demo.DemoBankInitializationService;
import com.luxoft.bankapp.service.feed.BankFeedService;
import com.luxoft.bankapp.service.feed.BankFeedServiceImpl;
import com.luxoft.bankapp.service.storage.ClientStorage;
import com.luxoft.bankapp.service.storage.Storage;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

import java.lang.annotation.Annotation;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = BankApplication.class)
public class BankApplicationTests1 {
    private static final String[] CLIENT_NAMES =
            {"Jonny Bravo", "Adam Budzinski", "Anna Smith"};

    @Autowired
    private Banking banking;

    @Autowired
    private Storage storage;

    @Autowired
    private BankReportService bankReport;

    @Autowired
    private BankFeedService bankFeedService;

    @Autowired
    private BankInitializationService initializationService;

    @Test
    public void storageBeanConfiguration() {
        assertNotNull(storage, "storage bean should be configured");
        assertTrue((storage instanceof ClientStorage), "storage should be instantiated with ClientStorage class");
    }

    @Test
    public void bankingBeanConfiguration() {
        assertNotNull(banking, "banking bean should be configured");
        assertTrue((banking instanceof BankingImpl), "storage should be instantiated with BankingImpl class");
    }

    @Test
    public void bankingBeanAnnotation() {
        Annotation annotation = null;

        try {
            annotation = BankingImpl.class.getDeclaredField("storage")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }

    @Test
    public void bankReportConfiguration() {
        assertNotNull(bankReport, "bankReport bean should be configured");
        assertTrue((bankReport instanceof BankReportServiceImpl), "bankReport should be instantiated with BankReportServiceImpl class");
    }

    @Test
    public void bankReportBeanAnnotation() {
        Annotation annotation = null;

        try {
            annotation = BankReportServiceImpl.class.getDeclaredField("storage")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }

    @Test
    public void bankFeedServiceConfiguration1() {
        assertNotNull(bankFeedService, "bankFeedService bean should be configured");
        assertTrue((bankFeedService instanceof BankFeedServiceImpl), "bankFeedService should be instantiated with BankFeedServiceImpl class");
    }

    @Test
    public void bankFeedServiceConfiguration2() {
        assertSame(banking, ((BankFeedServiceImpl) bankFeedService).getBanking(),
                "bankFeedService should use banking bean");
    }

    @Test
    public void bankFeedServiceBeanAnnotation() {
        Annotation annotation = null;

        try {
            annotation = BankFeedServiceImpl.class.getConstructor(Banking.class)
                    .getAnnotation(Autowired.class);
        } catch (NoSuchMethodException e) {
            fail("BankingImpl should contains storage field");
        }

        assertNotNull(annotation, "storage field should contain annotation @Autowired");
    }

    @Test
    public void initializationServiceConfiguration() {
        assertNotNull(initializationService, "initializationService bean should be configured");
        assertTrue((initializationService instanceof DemoBankInitializationService),
                "initializationService should be instantiated with DemoBankInitializationService class");
    }

    @Test
    public void initializationServiceBeanAnnotation1() {
        String value = null;

        try {
            Annotation annotation = DemoBankInitializationService.class.getAnnotation(Profile.class);
            value = ((Profile) annotation).value()[0];
        } catch (RuntimeException e) {
            fail("DemoBankInitializationService should contain Profile annotation");
        }

        assertEquals(value, "dev", "Profile annotation should contain value: dev");
    }


    @Test
    public void initializationServiceBeanAnnotation2() {
        Annotation annotation = null;

        try {
            annotation = DemoBankInitializationService.class.getDeclaredField("feedService")
                    .getAnnotation(Autowired.class);
        } catch (NoSuchFieldException e) {
            fail("DemoBankInitializationService should contains feedService field");
        }

        assertNotNull(annotation, "feedService field should contain annotation @Autowired");
    }

    @Test
    public void initializationClient1() {
        Client client = banking.getClient(CLIENT_NAMES[0]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[0]);

        assertEquals(2, client.getAccounts().size());
    }

    @Test
    public void client1SavingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        Account account = client.getAccount(AccountType.SAVING);

        assertNotNull(account,
                client.getName() + "should have " + AccountType.SAVING + " account");

        assertEquals(1000, account.getBalance());
    }

    @Test
    public void client1CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[0]);

        CheckingAccount account = (CheckingAccount) client.getAccount(AccountType.CHECKING);

        assertNotNull(account,
                client.getName() + "should have " + AccountType.CHECKING + " account");

        assertEquals(4000, account.getBalance());
        assertEquals(1000, account.getOverdraft());
    }

    @Test
    public void initializationClient2() {
        Client client = banking.getClient(CLIENT_NAMES[1]);
        assertNotNull(client, "banking should have client with name: " + CLIENT_NAMES[1]);

        assertEquals(1, client.getAccounts().size());
    }

    @Test
    public void client2CheckingAccount() {
        Client client = banking.getClient(CLIENT_NAMES[1]);

        CheckingAccount account = (CheckingAccount) client.getAccount(AccountType.CHECKING);

        assertNotNull(account,
                client.getName() + "should have " + AccountType.CHECKING + " account");

        assertEquals(-500, account.getBalance());
        assertEquals(1500, account.getOverdraft());
    }
}
