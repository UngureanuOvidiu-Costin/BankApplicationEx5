package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.Identifiable;

import java.util.Set;

public interface Storage<O extends Identifiable> {
    O add(O o);

    O get(long id);

    O getBy(String name);

    Set<O> getAll();

    O update(O o);

    boolean remove(long id);
}
