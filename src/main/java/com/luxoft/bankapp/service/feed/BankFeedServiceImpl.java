package com.luxoft.bankapp.service.feed;

import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.AccountType;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.service.Banking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BankFeedServiceImpl implements BankFeedService {
    private Banking banking;

    @Autowired
    public BankFeedServiceImpl(Banking banking) {
        this.banking = banking;
    }

    @Override
    public void loadFeed(String folder) {
        File[] files = new File(folder).listFiles(p -> p.getName().endsWith(".feed"));

        if (files != null) {
            Arrays.stream(files).forEach(this::loadFeed);
        }
    }

    @Override
    public void loadFeed(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader("feeds/" + file.getName()))) {
            while (reader.ready()) {
                Map<String, String> map = parseLine(reader.readLine());
                banking.parseFeed(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, String> parseLine(String line) {
        Map<String, String> result = new HashMap<>();

        String[] properties = line.split(";");

        for (String property : properties) {
            String[] values = property.split("=");
            result.put(values[0], values[1]);
        }

        return result;
    }

    @Override
    public void saveFeed(String file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feeds/" + file))) {
            for (Client client : banking.getClients()) {
                String clientInfo = collectFeedInfo(client, client.getClass()).delete(0, 1).toString();

                for (Account account : client.getAccounts()) {
                    StringBuilder accountInfo = new StringBuilder(clientInfo);
                    accountInfo.append(collectFeedInfo(account, account.getClass()));
                    writer.write(accountInfo.append("\n").toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private StringBuilder collectFeedInfo(Object object, Class<?> clazz) throws IllegalAccessException, InvocationTargetException {
        StringBuilder builder = new StringBuilder();

        Field[] declaredFields = clazz.getDeclaredFields();

        //Search for fields
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Feed.class) && !"accounts".equals(field.getName())) {
                field.setAccessible(true);

                //Iterate recursively through collection field
                if (Collection.class.isAssignableFrom(field.getType())) {
                    for (Object obj : (Collection) field.get(object)) {
                        builder.append(collectFeedInfo(obj, obj.getClass()));
                    }
                    continue;
                }

                //Object field
                if (!field.getType().isPrimitive() && !String.class.equals(field.getType())) {
                    builder.append(collectFeedInfo(field.get(object), field.getType()));
                }

                //Primitive field
                Feed annotation = field.getAnnotation(Feed.class);
                String name = annotation.value();
                if (name.isEmpty()) {
                    name = field.getName();
                }
                builder.append(";")
                        .append(name)
                        .append("=")
                        .append(field.get(object));
            }
        }

        //Search for methods
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(Feed.class)) {
                Feed annotation = method.getAnnotation(Feed.class);
                String name = annotation.value();
                if (name.isEmpty()) {
                    name = method.getName().substring(3).toLowerCase();
                }
                builder.append(";")
                        .append(name)
                        .append("=")
                        .append(method.invoke(object));
            }
        }

        //Search for fields in superclasses
        if (clazz.getSuperclass() != null && !Object.class.equals(clazz.getSuperclass())) {
            builder.append(collectFeedInfo(object, clazz.getSuperclass()));
        }

        return builder;
    }

    public void setBanking(Banking banking) {
        this.banking = banking;
    }

    public Banking getBanking() {
        return banking;
    }
}
