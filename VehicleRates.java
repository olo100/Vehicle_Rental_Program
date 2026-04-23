public class VehicleRates {

    private double daily_rate;   
    private double weekly_rate;   
    private double monthly_rate;   
    private double per_mile_charge;   
    private double daily_insurance;

    public VehicleRates (double daily_rate, double weekly_rate, double monthly_rate, 
                                double per_mile_charge, double daily_insurance) {
        
        this.daily_rate = daily_rate;
        this.weekly_rate = weekly_rate;
        this.monthly_rate = monthly_rate;
        this.per_mile_charge = per_mile_charge;
        this.daily_insurance = daily_insurance;

    }

    public VehicleRates(VehicleRates other) {

        this.daily_rate = other.daily_rate;
        this.weekly_rate = other.weekly_rate;
        this.monthly_rate = other.monthly_rate;
        this.per_mile_charge = other.per_mile_charge;
        this.daily_insurance = other.daily_insurance;
    }

    public double getDailyRate() { return daily_rate; } // cost per day       
    
    public double getWeeklyRate() { return weekly_rate; }    // cost per week       
    
    public double getMonthlyRate() { return monthly_rate; }        // cost per month   
    
    public double getMileageChrg() { return per_mile_charge; }       // cost per mile, based on vehicle type     
    
    public double getDailyInsurRate() { return daily_insurance; }    // insurance cost (per day)   
    
    public String[] toStringArray() {

        return new String[] {
            String.format("Daily Rate: $%.2f", daily_rate),
            String.format("Weekly Rate: $%.2f", weekly_rate),
            String.format("Monthly Rate: $%.2f", monthly_rate),
            String.format("Per Mile Charge: $%.2f", per_mile_charge),
            String.format("Daily Insurance Rate: $%.2f", daily_insurance)
        };
    }

}
