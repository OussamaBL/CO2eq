import config.connexion;
import entities.*;
import repositories.UserRepository;
import services.ConsumptionServices;
import services.UserServices;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        HashMap<String, User> users = new HashMap<String, User>();
        UserServices userServices=new UserServices();
        ConsumptionServices consumptionServices=new ConsumptionServices();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome in GreenPulse !!!!");
        System.out.println("//////////////");
        int choice;
        do {
            System.out.println("Options");
            System.out.println("1 - Add User");
            System.out.println("2 - Delete User");
            System.out.println("3 - Update User");
            System.out.println("4 - Consumption");
            System.out.println("5 - Show all the  users");
            System.out.println("6 - Rapport");
            System.out.println("7 - filtrer les utilisateurs en fonction de la consommation totale");
            System.out.println("8 - Close");
            System.out.println("////////");
            System.out.println("Choose an option");
            choice = scanner.nextInt();
            switch (choice){
                case 1:

                    System.out.println("\n ///// Add User /////");
                    User u=new User();
                    System.out.println("\n Enter the cin : ");
                    String cin = scanner.next();
                    scanner.nextLine();
                    if (userServices.findByCin(new User(cin))==null) {
                        u.setCin(cin);
                        System.out.println("\n Enter the name : ");
                        u.setName(scanner.next());
                        System.out.println("\n Enter the Age : ");
                        u.setAge(scanner.nextInt());
                        scanner.nextLine();
                        //users.put(u.getCin(),u);
                        userServices.create(u);
                        System.out.println("User added");
                    }
                    else System.out.println("User exist");
                    break;

                case 2:
                    System.out.println("\n ///// Delete User /////");
                    System.out.println("\n Enter the Cin : ");
                    String cin_delete= scanner.next();
                    if (userServices.findByCin(new User(cin_delete))!=null) {
                        System.out.println("Are you sure you want to delete the user? (yes/no)");
                        String confirmation = scanner.next();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            if(userServices.delete(new User(cin_delete))) System.out.println("User deleted");
                        } else {
                            System.out.println("User deletion canceled.");
                        }
                    }
                    else System.out.println("User not exist");
                    break;
                case 3:
                    System.out.println("\n ///// Update User /////");
                    System.out.println("Enter the cin of the user to update:");
                    String updateCin = scanner.next();
                    if (userServices.findByCin(new User(updateCin))!=null) {
                        User user = new User(updateCin);
                        System.out.println("Enter the new name:");
                        user.setName(scanner.next());
                        System.out.println("Enter the new age:");
                        user.setAge(scanner.nextInt());
                        scanner.nextLine();
                        //users.put(updateCin, user); // Update
                        userServices.update(user);
                        System.out.println("User updated");
                    }
                    else System.out.println("User not exist");
                    break;
                case 4:
                    System.out.println("\n ///// Consumptions /////");
                    int option;
                    do {
                        System.out.println("Options");
                        System.out.println("1 - add Consumption of transport");
                        System.out.println("2 - add Consumption of logement");
                        System.out.println("3 - add Consumption of alimentation");
                        System.out.println("4 - back");
                        System.out.println("////////");
                        System.out.println("Choose an option");
                        option= scanner.nextInt();
                        scanner.nextLine();
                        switch (option){
                            case 1:
                                System.out.println("Add Consumption of transport");
                                System.out.println("\n Enter the cin : ");
                                String cin_consumption = scanner.next();
                                scanner.nextLine();
                                User user = userServices.findByCin(new User(cin_consumption));
                                if (user!=null) {
                                    System.out.println("\nEnter the start date (yyyy-MM-dd): ");
                                    String start_date = scanner.nextLine();
                                    LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                                    System.out.println("\nEnter the end date (yyyy-MM-dd): ");
                                    String end_date = scanner.nextLine();
                                    LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    while(endDate.isBefore(startDate)){
                                        System.out.println("\nEnter the end date (yyyy-MM-dd): After = "+ start_date);
                                        end_date = scanner.nextLine();
                                        endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    }

                                    System.out.println("\n Enter the amount carbon  ");
                                    double carbon=scanner.nextDouble();

                                    System.out.println("\n Enter the distance traveled  ");
                                    double distanceParcourue=scanner.nextDouble();

                                    System.out.println("\n Choose the type of Vehicle");
                                    System.out.println("\n enter 1 for type : Voiture");
                                    System.out.println("\n enter 2 for type : Train");
                                    int i=0;
                                    String type="";
                                    while(i != 1 && i != 2) {
                                        System.out.println("\nChoose: ");
                                        i = scanner.nextInt();

                                        if(i == 1) {
                                            type = "VOITURE";
                                        } else if(i == 2) {
                                            type = "TRAIN";
                                        } else {
                                            System.out.println("Invalid choice. Please enter 1 for VOITURE or 2 for TRAIN.");
                                        }
                                    }
                                    Consumption consumption=new Transport(startDate,endDate,carbon,user,distanceParcourue,type);
                                    consumptionServices.create(consumption);
                                    System.out.println("Consumption of transport added");
                                }
                                else System.out.println("User not exist");
                                break;
                            case 2:
                                System.out.println("Add Consumption of Logement");
                                System.out.println("\n Enter the cin : ");
                                String cin_cons = scanner.next();
                                scanner.nextLine();
                                User us = userServices.findByCin(new User(cin_cons));
                                if (us!=null) {
                                    System.out.println("\nEnter the start date (yyyy-MM-dd): ");
                                    String start_date = scanner.nextLine();
                                    LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                                    System.out.println("\nEnter the end date (yyyy-MM-dd): ");
                                    String end_date = scanner.nextLine();
                                    LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    while(endDate.isBefore(startDate)){
                                        System.out.println("\nEnter the end date (yyyy-MM-dd): After = "+ start_date);
                                        end_date = scanner.nextLine();
                                        endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    }

                                    System.out.println("\n Enter the amount carbon  ");
                                    double carbon=scanner.nextDouble();

                                    System.out.println("\n Enter the consommation Energie ");
                                    double consommationEnergie=scanner.nextDouble();

                                    System.out.println("\n Choose the type of Accommodation ");
                                    System.out.println("\n enter 1 for type : Electricité");
                                    System.out.println("\n enter 2 for type : Gaz");
                                    int i=0;
                                    String type="";
                                    while(i != 1 && i != 2) {
                                        System.out.println("\nChoose: ");
                                        i = scanner.nextInt();

                                        if(i == 1) {
                                            type = "ELECTRICITÉ";
                                        } else if(i == 2) {
                                            type = "GAZ";
                                        } else {
                                            System.out.println("Invalid choice. Please enter 1 for ELECTRICITÉ or 2 for GAZ.");
                                        }
                                    }
                                    Consumption consumption=new Logement(startDate,endDate,carbon,us,consommationEnergie,type);
                                    consumptionServices.create(consumption);
                                    System.out.println("Consumption of Accommodation added");
                                }
                                else System.out.println("User not exist");
                                break;
                            case 3:
                                System.out.println("Add Consumption of Food");
                                System.out.println("\n Enter the cin : ");
                                String cin_con = scanner.next();
                                scanner.nextLine();
                                User ur = userServices.findByCin(new User(cin_con));
                                if (ur!=null) {
                                    System.out.println("\nEnter the start date (yyyy-MM-dd): ");
                                    String start_date = scanner.nextLine();
                                    LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                                    System.out.println("\nEnter the end date (yyyy-MM-dd): ");
                                    String end_date = scanner.nextLine();
                                    LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    while(endDate.isBefore(startDate)){
                                        System.out.println("\nEnter the end date (yyyy-MM-dd): After = "+ start_date);
                                        end_date = scanner.nextLine();
                                        endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                                    }

                                    System.out.println("\n Enter the amount carbon  ");
                                    double carbon=scanner.nextDouble();

                                    System.out.println("\n Enter the weight ");
                                    double poids=scanner.nextDouble();

                                    System.out.println("\n Choose the type of Food ");
                                    System.out.println("\n enter 1 for type : viande");
                                    System.out.println("\n ente&r 2 for type : Légume");
                                    int i=0;
                                    String type="";
                                    while(i != 1 && i != 2) {
                                        System.out.println("\nChoose: ");
                                        i = scanner.nextInt();

                                        if(i == 1) {
                                            type = "ELECTRICITÉ";
                                        } else if(i == 2) {
                                            type = "GAZ";
                                        } else {
                                            System.out.println("Invalid choice. Please enter 1 for ELECTRICITÉ or 2 for GAZ.");
                                        }
                                    }
                                    Consumption consumption=new Alimentation(startDate,endDate,carbon,ur,type,poids);
                                    consumptionServices.create(consumption);
                                    System.out.println("Consumption of Food added");
                                }
                                else System.out.println("User not exist");
                                break;
                            case 4:
                                System.out.println("Close!");
                                break;
                            default:
                                System.out.println("\n invalid Option");
                                break;
                        }
                    }
                    while (option!=3);
                    break;
                case 5:
                    System.out.println("\n ///// All the Users /////");
                    List<User> listUsers=userServices.readAll();
                    if(listUsers!=null){
                        for (User user : listUsers){
                            System.out.println(user);
                            System.out.println("////////////////////////////////");
                        }
                    }
                    else System.out.println("Users not found");

                    break;
                case 6:
                    System.out.println("\n ///// Rapport /////");
                    int choix;
                    do {
                        System.out.println("Options");
                        System.out.println("1 - Rapport daily");
                        System.out.println("2 - Rapport Weekly");
                        System.out.println("3 - Rapport monthly");
                        System.out.println("4 - close");
                        System.out.println("////////");
                        System.out.println("Choose an option");
                        choix= scanner.nextInt();
                        scanner.nextLine();
                        switch (choix){
                            case 1:
                                System.out.println("Rapport daily");
                                for (User user : users.values()){
                                    System.out.println(user.toString());
                                    userServices.dailyRapport(user);
                                }
                                break;
                            case 2:
                                System.out.println("Rapport weekly");
                                for (User user : users.values()){
                                    System.out.println(user.toString());
                                    userServices.weeklyRapport(user);
                                }
                                break;
                            case 3:
                                System.out.println("Rapport monthly");
                                for (User user : users.values()){
                                    System.out.println(user.toString());
                                    userServices.monthlyRapport(user);
                                }
                                break;
                            case 4:
                                System.out.println("Close!");
                                break;
                            default:
                                System.out.println("\n invalid Option");
                                break;
                        }
                    }
                    while (choix!=4);
                    break;
                case 7:
                    System.out.println("filtrer les utilisateurs en fonction de la consommation totale");
                    System.out.println("Saisie nombre de consommation total pour filtrer");
                    int number= scanner.nextInt();
                    scanner.nextLine();
                    List<User> list_users= userServices.filterByConsumption(number);
                    System.out.println(list_users);
                    break;
                case 8:
                    System.out.println("fla consommation moyenne de carbone par utilisateur pour une période donnée.");
                    System.out.println("\n Enter the cin : ");
                    String cin_cons = scanner.next();
                    scanner.nextLine();
                    User us = userServices.findByCin(new User(cin_cons));
                    if (us!=null) {
                        System.out.println("\nEnter the start date (yyyy-MM-dd): ");
                        String start_date = scanner.nextLine();
                        LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        System.out.println("\nEnter the end date (yyyy-MM-dd): ");
                        String end_date = scanner.nextLine();
                        LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        while (endDate.isBefore(startDate)) {
                            System.out.println("\nEnter the end date (yyyy-MM-dd): After = " + start_date);
                            end_date = scanner.nextLine();
                            endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                        }
                        System.out.println("la consommation moyenne de carbone de "+us.getCin()+" pour la période "+ startDate+" ==> "+endDate + " = "+ userServices.averageByPeriod(us,startDate,endDate));
                    }
                    else System.out.println("user not exist");
                    break;
                case 9:
                    System.out.println("les utilisateurs n'ayant pas enregistré de consommation de carbone pendant une période spécifiée.");
                    System.out.println("\nEnter the start date (yyyy-MM-dd): ");
                    String start_date = scanner.nextLine();
                    LocalDate startDate = LocalDate.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    System.out.println("\nEnter the end date (yyyy-MM-dd): ");
                    String end_date = scanner.nextLine();
                    LocalDate endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    while (endDate.isBefore(startDate)) {
                        System.out.println("\nEnter the end date (yyyy-MM-dd): After = " + start_date);
                        end_date = scanner.nextLine();
                        endDate = LocalDate.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    }
                    userServices.filterByInactivite(startDate,endDate);
                    break;
                case 10:
                    System.out.println("Trier les utilisateurs en fonction de leur consommation totale de carbone.");
                    userServices.classementByTotal();
                    break;
                case 11:
                    System.out.println("Close!");
                    break;

                default:
                    System.out.println("\n invalid Option");
                    break;
            }
        }
        while (choice!=11);


    }
}