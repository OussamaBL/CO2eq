package services;

import Util.DateUtils;
import entities.Consumption;
import entities.User;
import repositories.ConsumptionRepository;
import repositories.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

    public List<Consumption> sortConsumptionsByDate(User user) {
        List<Consumption> listConsumption=  urp.getAllConsumptions(user);
        return listConsumption.stream().sorted((c1, c2)-> c1.getDate_db().compareTo(c2.getDate_db())).collect(Collectors.toList());

    }
    public void dailyRapport(User user) {
        List<Consumption> listConsumption= this.sortConsumptionsByDate(user);
        for (Consumption c : listConsumption) {
            double dailyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(c.getDate_db(), c.getDate_fin()) + 1);
            for (LocalDate date = c.getDate_db(); !date.isAfter(c.getDate_fin()); date = date.plusDays(1)) {
                System.out.println(date + " ==> " + String.format("%.2f", dailyCarbon) + " carbon");
            }
        }
    }
    public void weeklyRapport(User user) {
        List<Consumption> listConsumption= this.sortConsumptionsByDate(user);
        for (Consumption c : listConsumption) {
            double dailyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(c.getDate_db(), c.getDate_fin()) + 1);
            LocalDate startDate = c.getDate_db();
            LocalDate endDate = c.getDate_fin();
            LocalDate weekStart = startDate;

            while (!weekStart.isAfter(endDate)) {
                double weeklyCarbon;
                LocalDate weekEnd=weekStart.plusDays(6);
                if(weekEnd.isBefore(endDate))
                    weeklyCarbon = dailyCarbon * (ChronoUnit.DAYS.between(weekStart, weekEnd) + 1);
                    // weeklyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(weekStart, weekEnd) + 1);

                else weeklyCarbon = (dailyCarbon * (ChronoUnit.DAYS.between(weekStart, endDate) + 1)) / (ChronoUnit.DAYS.between(weekStart, weekEnd) + 1) ;
                //System.out.println(weekStart + " to " + weekEnd + " ==> " + weeklyCarbon + " carbon");
                //double weeklyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(weekStart, weekEnd) + 1);
                System.out.println(weekStart + " to " + weekEnd + " ==> " + String.format("%.2f", weeklyCarbon) + " carbon");
                weekStart = weekEnd.plusDays(1);
            }
        }
    }

    public void monthlyRapport(User user) {
        List<Consumption> listConsumption= this.sortConsumptionsByDate(user);
        for (Consumption c : listConsumption) {
            double dailyCarbon = c.getCarbon() / (ChronoUnit.DAYS.between(c.getDate_db(), c.getDate_fin()) + 1);
            LocalDate startDate = c.getDate_db();
            LocalDate endDate = c.getDate_fin();
            LocalDate monthStart = startDate.withDayOfMonth(1);

            while (!monthStart.isAfter(endDate)) {
                double monthlyCarbon;
                LocalDate monthEnd = monthStart.plusMonths(1).minusDays(1);

                if (monthEnd.isAfter(endDate)) {
                    monthEnd = endDate;
                }

                monthlyCarbon = dailyCarbon * (ChronoUnit.DAYS.between(monthStart, monthEnd) + 1);

                System.out.println(monthStart + " to " + monthEnd + " ==> " + String.format("%.2f", monthlyCarbon) + " carbon");

                monthStart = monthStart.plusMonths(1).withDayOfMonth(1);
            }
        }
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
