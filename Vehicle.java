public abstract class Vehicle {
    
    private String description;      // make-model for cars/suvs, length for minivans   
    private int mpg;    // miles per gallon   
    private String vin;    // unique vehicle identification number   
    private ReservationDetails resv;  // If null, then vehicle not reserved   
    private VehicleRates rates;   // only assigned when vehicle reserved

    public Vehicle (String description, int mpg, String vin) {

        this.description = description;    
        this.mpg = mpg;    
        this.vin = vin;    
        resv = null;  // init as “no reservation”    c
        rates = null;  // used to hold rates at the time of the reservation
    }

    public String getDescription() { return description; }

    public int getMpg() { return mpg; }   
    
    public String getVIN() { return vin; }   
    
    public ReservationDetails getReservation() { return resv; }   
    
    public VehicleRates getQuotedRates() { return rates; }
    
    
    public boolean isReserved()  { return resv != null; }

    public void setReservation(ReservationDetails resv) throws ReservedVehicleException {// throws ReservedVehicleException (if veh reserved)   

        if (isReserved()) {
            throw new ReservedVehicleException("Vehicle is already reserved.");
        }
        else {
            this.resv = resv;
        }
    } 
    
    public void setQuotedRates(VehicleRates rates) { 
        this.rates = new VehicleRates(rates); 
    }   
    
    public void cancelReservation() throws UnreservedVehicleException { // throws UnreservedVehicleException if reservation doesn’t exist   

        if (!isReserved()) {
            throw new UnreservedVehicleException("Reservation doesn't exist");
        }
        else {
            this.resv = null;
            this.rates = null;
        }
    }
    
    public abstract String toString();  // ABSTRACT METHOD – implemented in each subclass

}