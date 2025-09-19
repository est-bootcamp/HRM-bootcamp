package com.example.est_bootcamp.core;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseController<T, ID> {
    protected abstract BaseService<T, ID> getService();

    @GetMapping
    public List<T> list() {
        return getService().findAll();
    }

    @GetMapping("/{id}")
    public T get(@PathVariable ID id) {
        return getService().findById(id);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return getService().save(entity);
    }

    @PutMapping("/{id}")
    public T update(@PathVariable ID id, @RequestBody T entity) {
        return getService().save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        getService().delete(id);
    }
}
