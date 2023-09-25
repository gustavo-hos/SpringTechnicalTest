package me.gustavo.springordermanager.service.impl;

import me.gustavo.springordermanager.service.intf.CRUDService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public abstract class BaseCRUDService<E, ID, R extends CrudRepository<E, ID>> implements CRUDService<E, ID> {

    protected R repository;

    public BaseCRUDService(R repository) {
        this.repository = repository;
    }

    @Override
    public E create(E entity) {
        return this.repository.save(entity);
    }

    @Override
    public Optional<E> findById(ID id) {
        return this.repository.findById(id);
    }

    @Override
    public List<E> findAll() {
        return StreamSupport.stream(this.repository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<E> update(ID id, E obj) {
        return Optional.of(this.findById(id))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(repository::save);
    }

    @Override
    public void delete(ID id) {
        this.findById(id).ifPresent(repository::delete);
    }
}
