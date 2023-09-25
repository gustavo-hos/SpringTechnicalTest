package me.gustavo.springordermanager.service.intf;

import java.util.List;
import java.util.Optional;

public interface CRUDService<E, ID> {

    E create(E entity);

    Optional<E> findById(ID id);

    List<E> findAll();

    Optional<E> update(ID id, E entity);

    Optional<E> update(E entity);

    void delete(ID id);
}
