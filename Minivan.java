public class Minivan extends Vehicle {

    private int storage;

    public Minivan (String description, int mpg, String vin, int storage) {

        super(description, mpg, vin);
        this.storage= storage;
    }

    public int getCargoStorage() {
        return storage;
    }

    public String toString() {
        return getDescription() + " (Minivan)" + " MPG: " + getMpg() + 
                        " Cargo Storage: " + storage + " cu.ft." + " VIN: " + getVIN(); 
    }
}
