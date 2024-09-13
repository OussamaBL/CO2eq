package interfaces;
import entities.Consumption;
import entities.User;

import java.util.List;
import java.util.Optional;

public interface UserInterface extends CrudRepository<User>{
    @Override
    User create(User entity);

    @Override
    Optional<User> find(User user);

    @Override
    List<User> readAll() ;

    @Override
    User update(User entity) ;

    boolean delete(User entity);
    User getConsumptions(User entity);

    List<Consumption> getAllConsumptions(User entity);
}
