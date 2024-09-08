package interfaces;

import java.sql.SQLException;
import java.util.List;

public interface CrudRepository<T> {
    T create(T entity) throws SQLException;
    T read(T t) throws SQLException;
    List<T> readAll() throws SQLException;
    T update(T entity) throws SQLException;
    void delete(T entity) throws SQLException;;

}
