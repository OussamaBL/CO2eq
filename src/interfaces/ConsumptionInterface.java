package interfaces;

import entities.Consumption;

import java.util.List;
import java.util.Optional;

public interface ConsumptionInterface<T extends Consumption> extends CrudRepository<T>{
    @Override
    T create(T entity);

    @Override
    Optional<T> find(T t);

    @Override
    List<T> readAll();

    @Override
    T update(T entity);

    @Override
    boolean delete(T entity);
}
