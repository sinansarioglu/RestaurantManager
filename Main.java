import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Table> tableList = new ArrayList<>();
        tableList.add(new Table(2));
        tableList.add(new Table(3));
        tableList.add(new Table(4));
        tableList.add(new Table(5));
        tableList.add(new Table(6));

        CustomerGroup group1 = new CustomerGroup(5);
        CustomerGroup group2 = new CustomerGroup(1);
        CustomerGroup group3 = new CustomerGroup(4);
        CustomerGroup group4 = new CustomerGroup(2);
        CustomerGroup group5 = new CustomerGroup(6);
        CustomerGroup group6 = new CustomerGroup(6);
        CustomerGroup group7 = new CustomerGroup(6);
        CustomerGroup group8 = new CustomerGroup(3);
        CustomerGroup group9 = new CustomerGroup(4);
        CustomerGroup group10 = new CustomerGroup(2);
        CustomerGroup group11 = new CustomerGroup(2);
        SeatingManager seatingManager = new SeatingManager(tableList);
        seatingManager.arrives(group1);
        seatingManager.arrives(group2);
        seatingManager.arrives(group3);
        seatingManager.arrives(group4);
        seatingManager.arrives(group5);
        seatingManager.arrives(group6);
        seatingManager.arrives(group7);
        seatingManager.arrives(group8);
        seatingManager.leaves(group7);
        seatingManager.arrives(group9);
        seatingManager.arrives(group10);
        seatingManager.leaves(group1);
        seatingManager.arrives(group11);
    }
}
