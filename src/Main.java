import java.util.HashMap;
import java.util.Scanner;

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
            System.out.println("4 - Show all the Users");
            System.out.println("5 - Close");
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
                    System.out.println("\n ///// All the Users /////");
                    if(!users.isEmpty()){
                        for (User user : users.values()){
                            System.out.println(user.toString());
                        }
                    }
                    else System.out.println("Users not found!");
                    break;
                default:
                    System.out.println("\n invalid Option");
                    break;
            }
        }
        while (choice!=5);


    }
}