package services;

import entities.Consumption;
import repositories.ConsumptionRepository;

public class ConsumptionServices {
    private ConsumptionRepository crp;
    public ConsumptionServices(){
        crp=new ConsumptionRepository();
    }
    public Consumption create(Consumption consumption){
       return crp.create(consumption);
    }
    public boolean delete(Consumption consumption){
        return crp.delete(consumption);
    }



}
