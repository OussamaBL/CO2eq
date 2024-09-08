package repositories;
import entities.User;
import config.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import interfaces.UserInterface;

public class UserRepository implements UserInterface {
    private Connection cnx;
    public UserRepository(){
        cnx=connexion.getInstance();
    }
    @Override
    public User create(User user) throws SQLException{
        String query = "insert into users (cin, name,age) values (?, ?, ?)";
        try{
            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setString(1,user.getCin());
            pst.setString(2,user.getName());
            pst.setInt(3,user.getAge());
            pst.execute();
            System.out.println("ajout Reussi de "+user );
        } catch(Exception ex){
            System.err.println( "Probleme !!!"+ex.getMessage());
        }
        return user;

    }

    @Override
    public User read(User user) throws SQLException {
        String query = "select * from users where cin = ?";
        PreparedStatement stmt = cnx.prepareStatement(query);
        stmt.setString(1, user.getCin());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()){
            user.setName(rs.getString("name"));
            user.setAge(rs.getInt("age"));
        }
        else {
            System.out.println("User not exist!");
            return null;
        }
        System.out.println("User exist!");
        return user;
    }

    @Override
    public List<User> readAll() throws SQLException{
        String query = "select * from users";
        List<User> users = new ArrayList<>();
        PreparedStatement stmt = cnx.prepareStatement(query);
        ResultSet rs=stmt.executeQuery();
        while(rs.next()){
            User user=new User(rs.getString("cin"),rs.getString("name"),rs.getInt("age"));
            users.add(user);
        }
        return users;
    }
    @Override
    public User update(User user) throws SQLException{
        String query = "update users set name=? ,age=? where cin=?";
        PreparedStatement stmt=cnx.prepareStatement(query);
        stmt.setString(1,user.getName());
        stmt.setInt(2,user.getAge());
        stmt.setString(3, user.getCin());
        stmt.executeUpdate();
        System.out.println("User updated successfully!");
        return user;
    }

    @Override
    public void delete(User user) throws SQLException {
        String query = "delete from users where cin=?";
        PreparedStatement stmt=cnx.prepareStatement(query);
        stmt.setString(1, user.getCin());
        stmt.executeUpdate();
        System.out.println("User deleted successfully.");
    }


}
