package entities;

import entities.User;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class Consumption  {

    protected LocalDate date_db ;
    protected LocalDate date_fin ;
    protected int id;
    protected double carbon ;
    protected User user;


    public Consumption(LocalDate date_db, LocalDate date_fin, double carbon,User user) {
        this.date_db = date_db;
        this.date_fin = date_fin;
        this.carbon = carbon;
        this.user=user;
    }
    public Consumption(){}
    public Consumption(int id){ this.id=id;}

    public LocalDate getDate_db() {
        return date_db;
    }

    public void setDate_db(LocalDate date_db) {
        this.date_db = date_db;
    }

    public LocalDate getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCarbon() {
        return carbon;
    }

    public void setCarbon(double carbon) {
        this.carbon = carbon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "entities.Consumption {" +
                "date_db=" + date_db +
                "id=" + id +
                ", date_fin=" + date_fin +
                ", carbon=" + carbon +
                '}';
    }
    public abstract double calculerImpact();
}
