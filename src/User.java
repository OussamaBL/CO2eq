import java.util.ArrayList;
import java.util.List;

public class User {
    private String cin;
    private String name;
    private int age;
    private List carbon;

    public User(String cin, String name, int age) {
        this.cin = cin;
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    @Override
    public String toString() {
        return "User{" +
                "cin=" + cin +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
