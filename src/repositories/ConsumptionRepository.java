package repositories;

import config.connexion;
import entities.Alimentation;
import entities.Consumption;
import entities.Enum.LogementType;
import entities.Logement;
import entities.Transport;
import interfaces.ConsumptionInterface;

import java.sql.*;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

public class ConsumptionRepository implements ConsumptionInterface<Consumption> {

    private Connection cnx;
    public ConsumptionRepository(){
        cnx= connexion.getInstance();
    }
    @Override
    public Consumption create(Consumption entity) {
        try{
            cnx.setAutoCommit(false);
            String query="";
            // Determine the query based on the type of entity
            if (entity instanceof Logement) {
                query = "insert into logement (date_db, date_fin, carbon, user_cin, type, consommationEnergie, typeEnergie) values (?, ?, ?, ?, ?, ?,?)";
            } else if (entity instanceof Transport) {
                query = "insert into transport (date_db, date_fin, carbon, user_cin, type, distanceParcourue, typeDeVehicule) values (?, ?, ?, ?, ?, ?,?)";
            } else if (entity instanceof Alimentation) {
                query = "insert into alimentation (date_db, date_fin, carbon, user_cin, type, typeAliment, poids) values (?, ?, ?, ?, ?, ?,?)";
            }

            PreparedStatement pst=cnx.prepareStatement(query);
            pst.setDate(1, Date.valueOf(entity.getDate_db()));
            pst.setDate(2, Date.valueOf(entity.getDate_fin()));
            pst.setDouble(3,entity.getCarbon());
            pst.setString(4,entity.getUser().getCin());
            pst.setString(5,entity.getType().name());
            if (entity instanceof Logement) {
                pst.setDouble(6,((Logement) entity).getConsommationEnergie());
                pst.setString(7, ((Logement) entity).getTypeEnergie().name());
            } else if (entity instanceof Transport) {
                pst.setDouble(6,((Transport) entity).getDistanceParcourue());
                pst.setString(7, ((Transport) entity).getTypeDeVehicule().name());
            } else if (entity instanceof Alimentation) {
                pst.setString(6,((Alimentation) entity).getTypeAliment().name());
                pst.setDouble(7, ((Alimentation) entity).getPoids());
            }
            pst.executeUpdate();
            cnx.commit();
            return entity;
        } catch (SQLException ex) {
            try {
                cnx.rollback();
            } catch (SQLException rollbackEx) {
                throw new RuntimeException("Error during transaction rollback", rollbackEx);
            }
            throw new RuntimeException("Error executing insert", ex);
        }
        finally {
            try {
                if (cnx != null) {
                    cnx.setAutoCommit(true);
                }
            } catch (SQLException finalEx) {
                throw new RuntimeException("Error resetting auto-commit", finalEx);
            }
        }
    }

    @Override
    public Optional<Consumption> find(Consumption entity) {
        return null;
    }

    @Override
    public List<Consumption> readAll() {
        return null;
    }

    @Override
    public Consumption update(Consumption entity) {
        return null;
    }

    @Override
    public boolean delete(Consumption entity) {
        return false;
    }

}
