public class SUV extends Vehicle {

    private int capacity;
    private int storage;

    public SUV (String description, int mpg, String vin, int capacity, int storage) {

        super(description, mpg, vin);
        this.capacity = capacity;
        this.storage = storage;
    }

    public int getSeatingCapacity() {
        return capacity;
    }

    public int getCargoStorage() {
        return storage;
    }

    public String toString() {
        return getDescription() + " (SUV)" + " MPG: " + getMpg() + 
                        " Seating: " + capacity + " Cargo Storage: " + storage + " cu.ft." + " VIN: " + getVIN(); 
    }
}