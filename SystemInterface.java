import java.util.ArrayList;

public class SystemInterface {

	private static CurrentRates agency_rates;
	private static Vehicles agency_vehicles;
	private static Transactions transactions_history;

	// used to init static variables (in place of a constructor)
	public static void initSystem(CurrentRates r, Vehicles v, Transactions t) {
		agency_rates = r;
		agency_vehicles = v;
		transactions_history = t;
	}

	public static boolean initialized() { return agency_rates != null; }
	
	// Note that methods makeReservation, cancelReservation and updateXXXRates return an
	// acknowledgement of successful completion of the requested action (e.g. “Vehicle ABC123
	// successfully reserved”). Method processReturnedVehicle returns the final cost for the returned 
	// vehicle (e.g., “Total charge for VIN ABC123 for 3 days, 233 miles @  0.15/mile and daily
	// insurance @ 14.95/day = $xxx.xx.)
	
	// Current Rates Related Methods
	public static String[ ] getCarRates() {

		return agency_rates.getCarRates().toStringArray();
	}

	public static String[ ] getSUVRates() { 

		return agency_rates.getSUVRates().toStringArray();
	}

	public static String[ ] getMinivanRates() { 

		return agency_rates.getMinivanRates().toStringArray();
	}

	public static String[ ] updateCarRates(VehicleRates r) { 

		agency_rates.setCarRates(r);
		return new String[] {"Car rates updated successfully"};
	 }
	public static String[ ] updateSUVRates(VehicleRates r) { 

		agency_rates.setSUVRates(r);
		return new String[] {"SUV rates updated successfully"};
	 }
	public static String[ ] updateMinivanRates(VehicleRates r) { 
	
		agency_rates.setMinivanRates(r);
		return new String[] {"Minivan rates updated successfully"};
	}

	public static String[ ] calcEstimatedRentalCost(RentalDetails details) { 

		int vehicleType = details.getVehicleType();
        int veh_index = vehicleType - 1; 

        double rental_cost = agency_rates.calcEstimatedCost(veh_index, details.getRentalPeriod(), 
					details.getMilesDriven(), details.getInsuranceSelected());

        return new String[]{String.format("Estimated Rental Cost: $%.2f", rental_cost)};
	}

		
	public static String[ ] processReturnedVehicle(String vin, int num_days_used, int num_miles_driven, String credit_card) { 

		try {
            Vehicle v = agency_vehicles.getVehicle(vin);
            ReservationDetails resv = v.getReservation();
            if (resv == null) {
                return new String[]{"Vehicle is not reserved."};
            }

			if (!resv.getCreditCardNum().equals(credit_card)) {
				return new String[]{"Error: Credit card does not match reservation."};
			}
			
            VehicleRates quoted_rates = v.getQuotedRates();
            boolean insurance = resv.getInsuranceSelected();

            double cost = agency_rates.calcActualCost(quoted_rates, num_days_used, num_miles_driven, insurance);

            String summary = String.format(
                "Total charge for VIN %s for %d days, %d miles @ $%.2f/mile and daily insurance @ $%.2f/day = $%.2f",
                vin, num_days_used, num_miles_driven, quoted_rates.getMileageChrg(), quoted_rates.getDailyInsurRate(), cost
            );

            transactions_history.add(new Transaction(resv.getCreditCardNum(), resv.getCustomerName(), v.getClass().getSimpleName(),
			 resv.getRentalPeriod().toString(), String.valueOf(num_miles_driven), String.format("$%.2f", cost)));

            v.cancelReservation();

            return new String[]{summary};
        } catch (VINNotFoundException e) {
            return new String[]{"Vehicle with VIN not found."};
        } catch (UnreservedVehicleException e) {
            return new String[]{"Vehicle is not currently reserved."};
        }
	}

	// Note that the rates to be used are retrieved from the VehicleRates object stored in the specific rented
	// vehicle object, and the daily insurance option is retrieved from the Reservation object of the rented
	// vehicle

		// Vehicle Related Methods
	public static String[ ] getAvailCars() { 

		ArrayList<String> results = new ArrayList<>();
		agency_vehicles.reset();

		while (agency_vehicles.hasNext()) {
			Vehicle v = agency_vehicles.getNext();
			if (v instanceof Car && !v.isReserved()) {
				results.add(v.toString());
			}
		}

		return results.toArray(new String[0]);
	}
	
	public static String[ ] getAvailSUVs() { 
		
		ArrayList<String> results = new ArrayList<>();
		agency_vehicles.reset();

		while (agency_vehicles.hasNext()) {
			Vehicle v = agency_vehicles.getNext();
			if (v instanceof SUV && !v.isReserved()) {
				results.add(v.toString());
			}
		}

		return results.toArray(new String[0]);
	}

	public static String[ ] getAvailMinivans() { 

		ArrayList<String> results = new ArrayList<>();
		agency_vehicles.reset();

		while (agency_vehicles.hasNext()) {
			Vehicle v = agency_vehicles.getNext();
			if (v instanceof Minivan && !v.isReserved()) {
				results.add(v.toString());
			}
		}

		return results.toArray(new String[0]);
	}

	public static String[ ] getAllVehicles() { 

		ArrayList<String> results = new ArrayList<>();
		agency_vehicles.reset();

		while (agency_vehicles.hasNext()) {
			Vehicle v = agency_vehicles.getNext();
			results.add(v.toString() + (v.isReserved() ? " (reserved)" : " (available)"));
		}
		return results.toArray(new String[0]);
	}

	public static String[ ] makeReservation(ReservationDetails details) { 

		try {
            Vehicle v = agency_vehicles.getVehicle(details.getVIN());
            v.setReservation(details);

            if (v instanceof Car) {
				v.setQuotedRates(agency_rates.getCarRates());
			}
            else if (v instanceof SUV) {
				v.setQuotedRates(agency_rates.getSUVRates());
			}
            else if (v instanceof Minivan) {
				v.setQuotedRates(agency_rates.getMinivanRates());
			}

            return new String[]{"Vehicle " + v.getVIN() + " reserved succesfully."};

        } catch (VINNotFoundException e) {
            return new String[]{"Vehicle with VIN not found."};
        } catch (ReservedVehicleException e) {
            return new String[]{"Vehicle is already reserved."};
        }
	}

	public static String[ ] cancelReservation(String vin) { 

		try {
            Vehicle v = agency_vehicles.getVehicle(vin);
            v.cancelReservation();
            return new String[]{"Reservation for vehicle " + vin + " is canceled."};
        } catch (VINNotFoundException e) {
            return new String[]{"Vehicle with VIN not found."};
        } catch (UnreservedVehicleException e) {
            return new String[]{"Vehicle is not reserved."};
        }
	}

	public static String[ ] getReservation(String vin) { 

		try {
            Vehicle v = agency_vehicles.getVehicle(vin);
            if (v.isReserved()) {
                return new String[]{v.getReservation().toString()};
            } else {
                return new String[]{"Vehicle is not reserved."};
            }
        } catch (VINNotFoundException e) {
            return new String[]{"Vehicle with VIN not found."};
        }
	}

	public static String[ ] getAllReservations() { 

		ArrayList<String> results = new ArrayList<>();
		agency_vehicles.reset();

		while (agency_vehicles.hasNext()) {
			Vehicle v = agency_vehicles.getNext();
			if (v.isReserved()) {
				results.add(v.getReservation().toString());
			}
		}

		return results.toArray(new String[0]);
	}

	// transactions-related methods
	public static String[ ] addTransaction(String credit_card, String customer_name, String vehicle_type, String rental_period, String miles_driven, String rental_cost) { 

		transactions_history.add(new Transaction(credit_card, customer_name, vehicle_type, rental_period, miles_driven, rental_cost));
        
		return new String[]{"Transaction recorded."};
	}

	public static String[ ] getAllTransactions() { 
		
		ArrayList<String> results = new ArrayList<>();
   		transactions_history.reset();

    	while (transactions_history.hasNext()) {
			Transaction t = transactions_history.getNext();
			results.add(t.toString());
    	}

    	return results.toArray(new String[0]);
	}  

}