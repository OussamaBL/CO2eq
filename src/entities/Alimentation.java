package entities;

import entities.Enum.AlimentationType;
import entities.Enum.ConsumptionType;

import java.time.LocalDate;

public class Alimentation extends Consumption {
    private AlimentationType typeAliment;
    private double poids;

    public Alimentation(LocalDate date_db, LocalDate date_fin, double carbon, User user, String typeAliment, double poids) {
        super(date_db, date_fin, carbon, user, ConsumptionType.ALIMENTATION);
        this.typeAliment = AlimentationType.valueOf(typeAliment);
        this.poids = poids;
    }

    public AlimentationType getTypeAliment() {
        return typeAliment;
    }

    public void setTypeAliment(String typeAliment) {
        this.typeAliment = AlimentationType.valueOf(typeAliment);
    }

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    @Override
    public String toString() {
        return super.toString() + "entities.Alimentation{" +
                "typeAliment='" + typeAliment + '\'' +
                ", poids=" + poids +
                '}';
    }

    @Override
    public double calculerImpact() {
        double impactConsumption=0;
        if(this.typeAliment.equals("VIANDE")) impactConsumption=5.0;
        else impactConsumption=0.5;
        return super.getCarbon()*this.poids*impactConsumption;
    }
}
