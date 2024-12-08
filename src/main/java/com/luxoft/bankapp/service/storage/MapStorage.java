package com.luxoft.bankapp.service.storage;

import com.luxoft.bankapp.model.Identifiable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class MapStorage<O extends Identifiable> implements Storage<O> {
    private Map<Long, O> data;

    private long currentId = 0;

    public MapStorage() {
        this.data = new HashMap<>();
    }

    @Override
    public O add(O o) {
        o.setId(currentId++);
        data.put(o.getId(), o);

        return o;
    }

    @Override
    public O get(long id) {
        return data.get(id);
    }

    @Override
    public Set<O> getAll() {
        return new HashSet<>(data.values());
    }

    @Override
    public O update(O o) {
        return data.put(o.getId(), o);
    }

    @Override
    public boolean remove(long id) {
        return data.remove(id) != null;
    }
}
