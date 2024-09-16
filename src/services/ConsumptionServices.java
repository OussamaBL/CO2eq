package services;

import entities.Consumption;
import entities.User;
import repositories.ConsumptionRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
