public class RentalDetails {
    
    private int vehicle_type; 
    private TimePeriod rental_period;     
    private int num_miles_driven;   
    private boolean insurance_selected; // set to true if optional daily insurance wanted

    public RentalDetails (int vehicle_type, TimePeriod rental_period, 
                                    int num_miles_driven, boolean insurance_selected) {
        
        this.vehicle_type = vehicle_type;
        this.rental_period = rental_period;
        this.num_miles_driven = num_miles_driven;
        this.insurance_selected = insurance_selected;
    }

    public int getVehicleType() { return vehicle_type; }     
    
    public TimePeriod getRentalPeriod() { return rental_period; }
    
    public int getMilesDriven() { return num_miles_driven; }     
    
    public boolean getInsuranceSelected() { return insurance_selected; }     

    public String toString() {

        return vehicle_type + ", " + rental_period + ", " + num_miles_driven + " miles" +
               (insurance_selected ? ", (insurance accepted)" : ", (insurance declined)");
    }
}
