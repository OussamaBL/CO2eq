package repositories;
import entities.*;
import config.connexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import interfaces.UserInterface;

public class UserRepository implements UserInterface {
    private Connection cnx;
    public UserRepository(){
        cnx=connexion.getInstance();
    }
    @Override
    public User create(User user) {
        String query = "insert into users (cin, name,age) values (?, ?, ?)";
        try{
                PreparedStatement pst=cnx.prepareStatement(query);
                pst.setString(1,user.getCin());
                pst.setString(2,user.getName());
                pst.setInt(3,user.getAge());
                pst.execute();
                return user;
        } catch(SQLException e){
                throw new RuntimeException(e);
        }

    }

    @Override
    public Optional<User> find(User user){
        String query = "select * from users where cin = ?";
        try {
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setString(1, user.getCin());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
            }
            else return Optional.empty();
            return Optional.of(user);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> readAll() {
        String query = "select * from users";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement stmt = cnx.prepareStatement(query);
            ResultSet rs=stmt.executeQuery();
            if (!rs.isBeforeFirst()) return null;
            else {
                while (rs.next()) {
                    User user = new User(rs.getString("cin"), rs.getString("name"), rs.getInt("age"));
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public User update(User user) {
        String query = "update users set name=? ,age=? where cin=?";
        try {
                PreparedStatement stmt = cnx.prepareStatement(query);
                stmt.setString(1,user.getName());
                stmt.setInt(2,user.getAge());
                stmt.setString(3, user.getCin());
                stmt.executeUpdate();
                return user;
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }

    }

    @Override
    public boolean delete(User user) {
        String query = "delete from users where cin=?";
            try {
                PreparedStatement stmt=cnx.prepareStatement(query);
                stmt.setString(1, user.getCin());
                stmt.executeUpdate();
                return true;
            }
            catch (SQLException e) {
                throw new RuntimeException(e);
            }

    }

    @Override
    public User getConsumptions(User entity) {
        return null;
    }

    @Override
    public List<Consumption> getAllConsumptions(User entity) {
        List<Consumption> listConsumptions=new ArrayList<>();
        String query = "select c.date_db, c.date_fin, c.carbon, c.user_cin, c.type, \n" +
                "       t.distanceParcourue, t.typeDeVehicule, \n" +
                "       l.consommationEnergie, l.typeEnergie, \n" +
                "       a.alimentationtype, a.poids \n" +
                "from consumption c \n" +
                "left join transport t ON c.id = t.id \n" +
                "left join logement l ON c.id = l.id \n" +
                "left join alimentation a ON c.id = a.id \n" +
                "where c.user_cin = ?;";
        try {
            PreparedStatement stmt = cnx.prepareStatement(query);
            stmt.setString(1, entity.getCin());
            ResultSet rs=stmt.executeQuery();
                while (rs.next()) {
                    LocalDate date_db = rs.getDate(1).toLocalDate();
                    LocalDate date_fin = rs.getDate(2).toLocalDate();
                    double carbon = rs.getDouble(3);
                    User user = new User(rs.getString(4));
                    String type = rs.getString(5);
                    Consumption consumption = null;

                    if(type.equals("TRANSPORT")){
                        double distanceParcourue = rs.getDouble(6);
                        String transportType = rs.getString(7);
                        consumption = new Transport(date_db, date_fin, carbon, user, distanceParcourue, transportType);
                    }
                    else if (type.equals("LOGEMENT")) {
                        double consommationEnergie = rs.getDouble(6);
                        String typeEnergie = rs.getString(7);
                        consumption = new Logement(date_db, date_fin, carbon, user, consommationEnergie, typeEnergie);
                    } else if (type.equals("ALIMENTATION")) {
                        String typeAliment = rs.getString(6);
                        double poids = rs.getDouble(7);
                        consumption = new Alimentation(date_db, date_fin, carbon, user, typeAliment, poids);
                    }
                    listConsumptions.add(consumption);

                }
                return listConsumptions;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
