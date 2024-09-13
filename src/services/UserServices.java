package services;

import Util.DateUtils;
import entities.Consumption;
import entities.User;
import repositories.UserRepository;

import java.time.LocalDate;
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
    public double consumptionTotal(User user){
        user.setConsumptions(urp.getAllConsumptions(user));
        //System.out.println(user);
        //System.out.println(user.getConsumptions());
        return user.getConsumptions().stream().mapToDouble(Consumption::calculerImpact).sum();
    }
    public List<User> filterByConsumption(double numbre){
        return this.readAll().stream().filter(e-> consumptionTotal(e)>numbre).collect(Collectors.toList());
    }

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

}
