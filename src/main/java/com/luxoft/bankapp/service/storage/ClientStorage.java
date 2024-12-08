package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.Client;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ClientStorage extends MapStorage<Client> {
    @Override
    public Client getBy(String name) {
        for (Client client : getAll()) {
            if (name.equals(client.getName())) {
                return client;
            }
        }

        return null;
    }
}
