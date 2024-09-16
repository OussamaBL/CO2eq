package services;

import Util.DateUtils;
import entities.Consumption;
import entities.User;
import repositories.ConsumptionRepository;
import repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServices {
    private UserRepository urp;
    public UserServices(){
        urp=new UserRepository();
    }
    public User create(User user){
       return urp.create(user);
    }
    public User findByCin(User user){
        Optional<User> opUser=urp.find(user);
        if(opUser.isEmpty()) return null;
        else return opUser.get();
    }

    public List<User> readAll(){
        return urp.readAll();
    }
    public User update(User user){
        return urp.update(user);
    }
    public boolean delete(User user){
        return urp.delete(user);
    }

    //Filtre 1
    public double consumptionTotal(User user){
        user.setConsumptions(urp.getAllConsumptions(user));
        return user.getConsumptions().stream().mapToDouble(Consumption::calculerImpact).sum();
    }
    public List<User> filterByConsumption(double numbre){
        return this.readAll().stream().filter(e-> consumptionTotal(e)>numbre).collect(Collectors.toList());
    }

    //filtre 2
    public Double averageByPeriod(User user, LocalDate start , LocalDate endDate) {

        if (!start.isAfter(endDate)) {
            user.setConsumptions(urp.getAllConsumptions(user));
            List<Consumption> consumptions = user.getConsumptions();
            List<LocalDate> dates = DateUtils.dateListRange(start, endDate);
            return (consumptions
                    .stream()
                    .filter(e -> !DateUtils.verifydates(e.getDate_db(), e.getDate_fin(), dates))
                    .mapToDouble(Consumption::calculerImpact).sum()) / dates.size();
        }
        return null;
    }

    //filtre 3
    public void filterByInactivite(LocalDate startDate,LocalDate endDate) {
        List<User> users = this.readAll().stream()
                .filter(user -> {
                    List<Consumption> consomations = urp.getAllConsumptions(user);
                    List<LocalDate> consomationDates = consomations.stream()
                            .flatMap(consomation -> DateUtils.dateListRange(consomation.getDate_db(), consomation.getDate_fin()).stream())
                            .collect(Collectors.toList());

                    return DateUtils.verifydates(startDate, endDate, consomationDates);
                })
                .collect(Collectors.toList());
        System.out.println(users);
    }

    //filtre 4
    public void classementByTotal() {
        List<User> ClassementUsers = this.readAll()
                .stream()
                .sorted((o1, o2) -> Double.compare(ClassementConsomation(o2), ClassementConsomation(o1)))
                .collect(Collectors.toList());

        System.out.println("Classement des utilisateurs par consommation totale");
        for (User user : ClassementUsers) {
            System.out.println(user);
            System.out.println("Consommation carbon : "+this.ClassementConsomation(user));
        }
    }

    public double ClassementConsomation(User user) {
        List<Consumption> consomationList = urp.getAllConsumptions(user);
        double totalConsomation = consomationList
                .stream()
                .mapToDouble(Consumption::calculerImpact)
                .sum();
        return totalConsomation;
    }

}
