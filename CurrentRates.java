public class CurrentRates {

    private VehicleRates[ ]  rates = new VehicleRates[3]; // array of size 3 to store rates for three types of vehicles

    public CurrentRates (VehicleRates CarRates, VehicleRates SUVRates, VehicleRates MinivanRates) {

        rates[0] = CarRates;
        rates[1] = SUVRates;
        rates[2] = MinivanRates;
    }

    public VehicleRates getCarRates() { return rates[0]; }  
    public void setCarRates(VehicleRates r) { rates[0] = r; }   
    
    public VehicleRates getSUVRates() { return rates[1]; }  
    public void setSUVRates(VehicleRates r) { rates[1] = r; }   
    
    public VehicleRates getMinivanRates() { return rates[2]; }  
    public void setMinivanRates(VehicleRates r) { rates[2] = r; }

    public double calcEstimatedCost(int vehicleType, TimePeriod estimatedRentalPeriod, 
                                int estimatedNumMiles,  boolean dailyInsur) {

        VehicleRates r = rates[vehicleType];
        double baseCost = 0;
        char unit = estimatedRentalPeriod.getUnit();
        int qty = estimatedRentalPeriod.getQuantity();

        switch (unit) {
            case 'd': baseCost = qty * r.getDailyRate(); break;
            case 'w': baseCost = qty * r.getWeeklyRate(); break;
            case 'm': baseCost = qty * r.getMonthlyRate(); break;
        }

        double mileageCost = estimatedNumMiles * r.getMileageChrg();
        double insurCost = dailyInsur ? qty * r.getDailyInsurRate() : 0;

        return baseCost + mileageCost + insurCost;
    }

    public double  calcActualCost(VehicleRates rates, int num_days_used, 
                            int NumMilesDriven, boolean dailyInsur) {
                                
        int months = num_days_used / 31;
        int remainingDays = num_days_used % 31;

        double cost = months * rates.getMonthlyRate();
        cost += remainingDays * (rates.getMonthlyRate() / 31);
        cost += NumMilesDriven * rates.getMileageChrg();
        
        if (dailyInsur) cost += num_days_used * rates.getDailyInsurRate();
        return cost;
    }

}