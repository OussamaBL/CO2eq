package services;

import Util.DateUtils;
import entities.*;
import repositories.ConsumptionRepository;
import repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ConsumptionServices {
    private ConsumptionRepository crp;
    private UserRepository urp;
    public ConsumptionServices(){
        crp=new ConsumptionRepository();
        urp=new UserRepository();
    }
    public Consumption create(Consumption consumption){
        if(VerifyAddConsumptionByTypes(consumption)) return crp.create(consumption);
        else return null;
    }
    public boolean VerifyAddConsumptionByTypes(Consumption consumption){
        List<Consumption> listConsumptionUser=urp.getAllConsumptions(consumption.getUser());
        List<Consumption> lstfiltre=listConsumptionUser.stream().filter(c->c.getType().equals(consumption.getType())).collect(Collectors.toList());
        if(consumption instanceof Transport){
            lstfiltre = lstfiltre.stream()
                    .filter(c -> c instanceof Transport)
                    .map(c -> (Transport) c) // Cast to Transport
                    .filter(con -> con.getTypeDeVehicule().name().equals(((Transport) consumption).getTypeDeVehicule().name()))
                    .collect(Collectors.toList());
        }
        if(consumption instanceof Alimentation){
            lstfiltre = lstfiltre.stream()
                    .filter(c -> c instanceof Alimentation)
                    .map(c -> (Alimentation) c) // Cast to Alimentation
                    .filter(con -> con.getTypeAliment().name().equals(((Alimentation) consumption).getTypeAliment().name()))
                    .collect(Collectors.toList());
        }
        if(consumption instanceof Logement){
            lstfiltre = lstfiltre.stream()
                    .filter(c -> c instanceof Logement)
                    .map(c -> (Logement) c) // Cast to Logement
                    .filter(con -> con.getTypeEnergie().name().equals(((Logement) consumption).getTypeEnergie().name()))
                    .collect(Collectors.toList());
        }
        for (Consumption c: lstfiltre){
            List<LocalDate> dates = DateUtils.dateListRange(consumption.getDate_db(), consumption.getDate_fin());
            boolean verify=DateUtils.verifydates(c.getDate_db(),c.getDate_fin(),dates);
            if(!verify) return false;
        }
        return true;
    }
    public boolean delete(Consumption consumption){
        return crp.delete(consumption);
    }




}
