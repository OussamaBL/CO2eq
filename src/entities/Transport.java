package entities;

import java.time.LocalDate;

public class Transport extends Consumption {
    private double distanceParcourue;
    private String typeDeVehicule;

    public Transport(LocalDate date_db, LocalDate date_fin, double carbon, User user, double distanceParcourue, String typeDeVehicule) {
        super(date_db, date_fin, carbon, user);
        this.distanceParcourue = distanceParcourue;
        this.typeDeVehicule = typeDeVehicule;
    }

    public double getDistanceParcourue() {
        return distanceParcourue;
    }

    public void setDistanceParcourue(double distanceParcourue) {
        this.distanceParcourue = distanceParcourue;
    }

    public String getTypeDeVehicule() {
        return typeDeVehicule;
    }

    public void setTypeDeVehicule(String typeDeVehicule) {
        this.typeDeVehicule = typeDeVehicule;
    }

    @Override
    public String toString() {
        return super.toString() + "entities.Transport{" +
                "distanceParcourue=" + distanceParcourue +
                ", typeDeVehicule='" + typeDeVehicule + '\'' +
                '}';
    }

    @Override
    public double calculerImpact() {
        double impactConsumption=0;
        if(this.typeDeVehicule=="voiture") impactConsumption=0.5;
        else impactConsumption=0.1;
        return super.getCarbon()*this.distanceParcourue*impactConsumption;
    }
}
