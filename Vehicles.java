import java.util.*;

public class Vehicles {
    
    private ArrayList<Vehicle> list;
    private int i;
    
    public Vehicles () {
        
        list = new ArrayList<>();
        i = 0;
    }

    public void add(Vehicle v) {

        list.add(v);
    }   
    
    public Vehicle getVehicle(String VIN) throws VINNotFoundException {    // throws VINNotFoundException if no vehicle found for provided VIN 
        
        for (Vehicle v : list) {
            if (v.getVIN().equals(VIN.toUpperCase())) {
                return v;
            }
        }
        throw new VINNotFoundException ("VIN not found " + VIN);
    }
    public void reset() { i = 0; } // resets to first vehicle in list 
        
    public boolean hasNext() { return i < list.size(); } // returns true if more vehicles in list to retrieve 

    public Vehicle getNext() { return list.get(i++); }  // returns next vehicle in list 
}
