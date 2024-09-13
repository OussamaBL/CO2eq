package interfaces;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    T create(T entity) ;
    Optional<T> find(T t) ;
    List<T> readAll() ;
    T update(T entity) ;
    boolean delete(T entity) ;

}
