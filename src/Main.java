import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<String, User>();

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
            System.out.println("5 - Show all the details of users");
            System.out.println("6 - Rapport");
            System.out.println("7 - Close");
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
                    if(!users.containsKey(cin)){
                        u.setCin(cin);
                        System.out.println("\n Enter the name : ");
                        u.setName(scanner.next());
                        System.out.println("\n Enter the Age : ");
                        u.setAge(scanner.nextInt());
                        scanner.nextLine();
                        users.put(u.getCin(),u); //Insert
                        System.out.println("\n /// User Added successfully /// ");
                    }
                    else System.out.println("Cin already exist !!!");
                    break;

                case 2:
                    System.out.println("\n ///// Delete User /////");
                    System.out.println("\n Enter the Cin : ");
                    String cin_delete= scanner.next();
                    if (users.containsKey(cin_delete)) {
                        System.out.println("Are you sure you want to delete the user? (yes/no)");
                        String confirmation = scanner.next();
                        if (confirmation.equalsIgnoreCase("yes")) {
                            users.remove(cin_delete); // Remove the user
                            System.out.println("User deleted successfully.");
                        } else {
                            System.out.println("User deletion canceled.");
                        }
                    }
                    else System.out.println("User not found!");
                    break;
                case 3:
                    System.out.println("\n ///// Update User /////");
                    System.out.println("Enter the cin of the user to update:");
                    String updateCin = scanner.next();
                    if (users.containsKey(updateCin)) {
                        User user = users.get(updateCin);
                        System.out.println("Enter the new name:");
                        user.setName(scanner.next());
                        System.out.println("Enter the new age:");
                        user.setAge(scanner.nextInt());
                        scanner.nextLine();
                        users.put(updateCin, user); // Update
                        System.out.println("User updated successfully!");

                    } else System.out.println("User not found!");
                    break;
                case 4:
                    System.out.println("\n ///// Consumptions /////");
                    int option;
                    do {
                        System.out.println("Options");
                        System.out.println("1 - Add Consumption");
                        System.out.println("2 - Delete Consumption");
                        System.out.println("3 - Back");
                        System.out.println("////////");
                        System.out.println("Choose an option");
                        option= scanner.nextInt();
                        scanner.nextLine();
                        switch (option){
                            case 1:
                                System.out.println("Add Consumption");
                                System.out.println("\n Enter the cin : ");
                                String cin_consumption = scanner.next();
                                scanner.nextLine();
                                if(users.containsKey(cin_consumption)){
                                    User user_consumption=users.get(cin_consumption);
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

                                    Consumption consumption=new Consumption(startDate,endDate,carbon,user_consumption);
                                    System.out.println(consumption.toString());

                                    user_consumption.addConsumption(consumption);
                                }
                                else System.out.println("Cin not found !!!");
                                break;
                            case 2:
                                System.out.println("Delete Consumption");
                                System.out.println("\n Enter the cin : ");
                                String cin_consumption_delete = scanner.next();
                                scanner.nextLine();
                                if(users.containsKey(cin_consumption_delete)){
                                    User user_consumption=users.get(cin_consumption_delete);
                                    System.out.println("\n Enter the id of consumption : ");
                                    user_consumption.deleteConsumption(scanner.nextInt());
                                    scanner.nextLine();
                                }
                                else System.out.println("Cin not found !!!");
                                break;
                            case 3:
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
                    if(!users.isEmpty()){
                        for (User user : users.values()){
                            System.out.println(user.toString());
                            System.out.println("/// Consumption ///");
                            System.out.println(user.ShowAllConsumption());
                            System.out.println("////////////////////////////////");
                        }
                    }
                    else System.out.println("Users not found!");
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
                                    user.dailyRapport();
                                }
                                break;
                            case 2:
                                System.out.println("Rapport weekly");
                                for (User user : users.values()){
                                    System.out.println(user.toString());
                                    user.weeklyRapport();
                                }
                                break;
                            case 3:
                                System.out.println("Rapport monthly");
                                for (User user : users.values()){
                                    System.out.println(user.toString());
                                    user.dailyRapport();
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
                    System.out.println("Close!");
                    break;
                default:
                    System.out.println("\n invalid Option");
                    break;
            }
        }
        while (choice!=7);


    }
}