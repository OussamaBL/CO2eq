package entities;

import entities.Enum.ConsumptionType;
import entities.Enum.LogementType;

import java.time.LocalDate;

public class Logement extends Consumption {
    private double consommationEnergie;
    private LogementType typeEnergie;

    public Logement(LocalDate date_db, LocalDate date_fin, double carbon, User user, double consommationEnergie, String typeEnergie) {
        super(date_db, date_fin, carbon, user, ConsumptionType.LOGEMENT);
        this.consommationEnergie = consommationEnergie;
        this.typeEnergie = LogementType.valueOf(typeEnergie);
    }

    public double getConsommationEnergie() {
        return consommationEnergie;
    }

    public void setConsommationEnergie(double consommationEnergie) {
        this.consommationEnergie = consommationEnergie;
    }

    public LogementType getTypeEnergie() {
        return typeEnergie;
    }

    public void setTypeEnergie(String typeEnergie) {
        this.typeEnergie = LogementType.valueOf(typeEnergie);
    }

    @Override
    public String toString() {
        return super.toString() + "entities.Logement{" +
                "consommationEnergie=" + consommationEnergie +
                ", typeEnergie='" + typeEnergie + '\'' +
                '}';
    }

    @Override
    public double calculerImpact() {
        double impactConsumption=0;
        if(this.typeEnergie.equals("ELECTRICITÉ")) impactConsumption=1.5;
        else impactConsumption=2.0;
        return super.getCarbon()*this.consommationEnergie*impactConsumption;
    }
}
