import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Consumption  {

    private static final AtomicInteger count = new AtomicInteger(0);
    private LocalDate date_db ;
    private LocalDate date_fin ;
    private int id;
    private double carbon ;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Consumption(LocalDate date_db, LocalDate date_fin, double carbon,User user) {
        this.date_db = date_db;
        this.date_fin = date_fin;
        this.id = count.incrementAndGet();
        this.carbon = carbon;
        this.user=user;
    }
    public Consumption(){

    }

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

    @Override
    public String toString() {
        return "Consumption {" +
                "date_db=" + date_db +
                "id=" + id +
                ", date_fin=" + date_fin +
                ", carbon=" + carbon +
                '}';
    }
}
