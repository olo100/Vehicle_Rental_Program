public class Car extends Vehicle{
    
    private int capacity;

    public Car (String description, int mpg, String vin, int capacity) {

        super(description, mpg, vin);
        this.capacity = capacity;
    }

    public int getSeatingCapacity() {
        return capacity;
    }

    public String toString() {
        return getDescription() + " (Car)" + " MPG: " + getMpg() + 
                        " Seating: " + capacity + " VIN: " + getVIN(); 
    }

}
