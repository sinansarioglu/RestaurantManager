import java.util.*;

public class SeatingManager {

    LinkedList<CustomerGroup> waitingCustomerList = new LinkedList<>();
    HashMap<Integer, LinkedList<Table>> availableSeatMap = new HashMap<>();

    /* Constructor */
    public SeatingManager(List<Table> tables) {
        if (tables != null) {
            tables.forEach(table -> {
                LinkedList<Table> tableList = availableSeatMap.getOrDefault(table.getSize(), new LinkedList<>());
                tableList.addLast(table);
                availableSeatMap.put(table.getSize(), tableList);
            });
        }
    }

    /* Group arrives and wants to be seated. */
    public void arrives(CustomerGroup group) {
        if (group == null) return;
        //Trying to locate new group immediately
        if (locate(group) == null) {
            waitingCustomerList.addLast(group);
        }
    }

    /* Whether seated or not, the group leaves the restaurant. */
    public void leaves(CustomerGroup group) {
        if (group == null)
            return;
            //remove from waiting customer list if they leave
        else if (group.getAssignedTable() == null)
            waitingCustomerList.remove(group);
        else {
            int freeSeatOnTableBeforeLeaving = group.getAssignedTable().getAvailableSeatCount();
            //update availableSeatMap
            Table table = group.getAssignedTable();
            LinkedList<Table> tableList = availableSeatMap.get(freeSeatOnTableBeforeLeaving);
            if (tableList != null) {
                tableList.remove(table);
                availableSeatMap.put(freeSeatOnTableBeforeLeaving, tableList);
            }
            table.setAvailableSeatCount(table.getAvailableSeatCount() + group.getSize());

            locateOnLeave(table);
            if (table.getAvailableSeatCount() > 0) {
                LinkedList<Table> tables = availableSeatMap.getOrDefault(table.getAvailableSeatCount(), new LinkedList<Table>());
                tables.addLast(table);
                availableSeatMap.put(table.getAvailableSeatCount(), tables);
            }
        }
    }

    /* Return the table at which the group is seated, or null if
    they are not seated (whether they're waiting or already left). */
    public Table locate(CustomerGroup group) {
        if (group == null) return null;
        int minLimit = group.getSize();
        LinkedList<Table> availableTables, tableList;
        while (minLimit <= RestaurantConstant.MAX_GROUP_SIZE) {
            availableTables = availableSeatMap.get(minLimit);
            if (availableTables != null && availableTables.size() > 0) {
                Table table = availableTables.pollFirst();
                int freeSeatCount = table.getSize() - group.getSize();
                table.setAvailableSeatCount(freeSeatCount);
                if (freeSeatCount > 0) {
                    tableList = availableSeatMap.getOrDefault(freeSeatCount, new LinkedList<Table>());
                    tableList.addLast(table);
                    availableSeatMap.put(freeSeatCount, tableList);
                }
                if (availableTables.size() > 0) {
                    availableSeatMap.put(minLimit, availableTables);
                } else {
                    availableSeatMap.remove(minLimit);
                }
                group.setAssignedTable(table);
                return table;
            } else {
                minLimit++;
            }
        }
        return null;
    }

    private void locateOnLeave(Table table) {
        Iterator<CustomerGroup> waitingListIterator = waitingCustomerList.iterator();
        CustomerGroup waitingGroup;
        Queue<CustomerGroup> deletedGroups = new LinkedList<>(); // Located waiting groups are stored.
        while (table.getAvailableSeatCount() > 0 && waitingListIterator.hasNext()) {
            waitingGroup = waitingListIterator.next();
            if (waitingGroup.getSize() <= table.getAvailableSeatCount()) {
                deletedGroups.add(waitingGroup);
                waitingGroup.setAssignedTable(table);

                table.setAvailableSeatCount(table.getAvailableSeatCount() - waitingGroup.getSize());
            }
        }
        // Remove located waiting groups from waiting customer list
        while (deletedGroups.size() > 0) {
            waitingCustomerList.remove(deletedGroups.poll());
        }
    }
}
