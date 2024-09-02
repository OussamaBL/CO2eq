import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String cin;
    private String name;
    private int age;
    private List<Consumption> consumptions;

    public User(String cin, String name, int age) {
        this.cin = cin;
        this.name = name;
        this.age = age;
        this.consumptions = new ArrayList<>();
    }

    public User() {
        this.consumptions = new ArrayList<>();
    }



    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void addConsumption(Consumption consumption) {
        for (Consumption c : this.consumptions) {
            if (consumption.getDate_db().isBefore(c.getDate_fin()) && consumption.getDate_fin().isAfter(c.getDate_db())) {
                System.out.println("the consumption overlaps with an existing consumption. addition cancelled.");
                return;
            }
        }
        this.consumptions.add(consumption);
        System.out.println("Consumption added successfully");
    }

    public void dailyRapport() {
        for (Consumption c : this.consumptions) {
            double dailyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(c.getDate_db(), c.getDate_fin()) + 1);
            for (LocalDate date = c.getDate_db(); !date.isAfter(c.getDate_fin()); date = date.plusDays(1)) {
                System.out.println(date + " : " + String.format("%.2f", dailyCarbon) + " carbon");
            }
        }
    }



    public String ShowAllConsumption(){
        String details="";
        for(Consumption c : this.consumptions) details += c.toString();
        return details;
    }
    public Consumption find(int id){
        for (Consumption c : this.consumptions) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }
    public void deleteConsumption(int id){
        Consumption c=this.find(id);
        if(c!=null){
            this.consumptions.remove(c);
            System.out.println("Consumption with id " + id + " removed successfully.");
        }
        else System.out.println("Consumption not found");
    }

    @Override
    public String toString() {
        return "User {" +
                "cin=" + cin +
                ",name='" + name + '\'' +
                ",age=" + age +
                '}';
    }
}
