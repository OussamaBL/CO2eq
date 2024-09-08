package interfaces;
import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserInterface extends CrudRepository<User>{
    @Override
    User create(User entity) throws SQLException;

    @Override
    User read(User user) throws SQLException;

    @Override
    List<User> readAll() throws SQLException;

    @Override
    User update(User entity) throws SQLException;

    void delete(User entity) throws SQLException;
}
