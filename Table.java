public class Table {
    private int size; //number of chairs around this table
    private int availableSeatCount;

    public Table() {}
    public Table(int size) {
        this.size = size;
        this.availableSeatCount = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAvailableSeatCount() {
        return availableSeatCount;
    }

    public void setAvailableSeatCount(int availableSeatCount) {
        this.availableSeatCount = availableSeatCount;
    }
}
