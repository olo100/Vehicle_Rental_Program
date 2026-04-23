import java.util.Scanner;

public class EmployeeUI implements UserInterface {
	
	// No constructor needed, calls static methods of the SystemInterface.
	// Method start begins a command loop that repeatedly: (a) displays a menu of options, 
      // (b) gets the selected option from the user, and (c) executes the corresponding command.

	private boolean quit = false;
 	public void start(Scanner input) {

		int selection;

		// command loop
		while(!quit) {
			displayMenu();
			selection = getSelection(input);
			execute(selection, input);
		}
    }
	
     // ------- private methods

    private void execute(int selection, Scanner input) {

        int veh_type;
		String vin, creditcard_num;  
		String[] display_lines = null;
		RentalDetails rental_details;  
		ReservationDetails reserv_details;
		int num_day_used;
		int num_miles_driven;

		switch(selection) {

			// display rental rates
			case 1: veh_type = getVehicleType(input);
					switch(veh_type) {
						case 1: display_lines = SystemInterface.getCarRates(); break;
						case 2: display_lines = SystemInterface.getSUVRates(); break;
						case 3: display_lines = SystemInterface.getMinivanRates(); break;
					}
					displayResults(display_lines);
					break;

			// display available vehicles
			case 2:	veh_type = getVehicleType(input);
					switch(veh_type) {
						case 1: display_lines = SystemInterface.getAvailCars(); break;
						case 2: display_lines = SystemInterface.getAvailSUVs(); break;
						case 3: display_lines = SystemInterface.getAvailMinivans(); break;
					}
					displayResults(display_lines);
					break;
			// display estimated rental cost
			case 3:	rental_details = getRentalDetails(input);
					display_lines = SystemInterface.calcEstimatedRentalCost(rental_details);
					displayResults(display_lines);
					break;
					
			// make a reservation
			case 4:	reserv_details = getReservationDetails(input);
					display_lines = SystemInterface.makeReservation(reserv_details);
					displayResults(display_lines);
					break;

			case 5: vin = getVIN(input);
					display_lines = SystemInterface.getReservation(vin);
					displayResults(display_lines);

			
			// cancel a reservation
			case 6:	vin = getVIN(input);
					display_lines = SystemInterface.cancelReservation(vin);
					displayResults(display_lines);
					break;

			// process returned vehicle
			case 7:	creditcard_num = getCreditCardNum(input);
					vin = getVIN(input);

					System.out.print("Enter number of days used: ");
					num_day_used = input.nextInt();

					System.out.print("Enter number of miles driven: ");
					num_miles_driven = input.nextInt();

					input.nextLine();

					display_lines = SystemInterface.processReturnedVehicle(vin, 
										num_day_used,num_miles_driven, creditcard_num);
					displayResults(display_lines);
					break;

			// quit program
			case 8: quit = true;
		}
	}

	// displays the user options
	private void displayMenu() {
		System.out.println("\nEmployee Menu:");
		System.out.println("1 - Display Current Rates");
		System.out.println("2 - Display Available Vehicles");
		System.out.println("3 - Display Estimated Rental Cost");
		System.out.println("4 - Make a Reservation");
		System.out.println("5 - Display a Reservation");
		System.out.println("6 - Cancel a Reservation");
		System.out.println("7 - Process a Returned Vehicle");
		System.out.println("8 - Quit Menu");
	} 

	// prompts user for selection from menu (continues to prompt if selection < 1 or selection > 8)
	private int getSelection(Scanner input) {
		int selection;
			do {
				System.out.print("Enter a number 1 through 8: ");

				while (!input.hasNextInt()) {
					System.out.print("Invaid selection. Enter a number 1-8: ");
					input.next();
				}
				selection = input.nextInt();
				input.nextLine();
	
			} while (selection < 1 || selection > 8);

		return selection;
	}
	
	// prompts user to enter VIN for a given vehicle (does not do any error checking on the input)
	private String getVIN(Scanner input) {
		System.out.print("Enter VIN: ");
		return input.nextLine();
	}

	private String getCreditCardNum(Scanner input) {
		System.out.print("Enter credit card number: ");
		return input.nextLine();
	}
	
	// prompts user to enter 1, 2, or 3, and returns (continues to prompt user if invalid input given)
	private int getVehicleType(Scanner input) {
		int vehicle_type;
		do {
			System.out.print("Enter vehicle type- Car(1), SUV(2), Minivan(3): ");

			while (!input.hasNextInt()) {
				System.out.print("Invaid selection. Enter 1, 2, or 3: ");
				input.next();
			}
			vehicle_type = input.nextInt();
			input.nextLine();

		} while (vehicle_type < 1 || vehicle_type > 3);
		
		return vehicle_type;
	}
	
	// prompts user to enter required information for an estimated rental cost (vehicle type, estimated  
 	// number of miles expected to be driven, expected rental period and optional insuranc, returning the
 	// result packaged as a RentalDetails object (to pass in method calls to the SystemInterface)
	private RentalDetails getRentalDetails(Scanner input) {
		int veh_type = getVehicleType(input);
		System.out.print("Rental unit (d/w/m): ");
		char unit = input.nextLine().toLowerCase().charAt(0);

		System.out.print("Quantity: ");
		int quantity = input.nextInt();

		System.out.print("Estimated miles: ");
		int miles = input.nextInt();

		input.nextLine();

		System.out.print("Insurance? (y/n): ");
		boolean insurance = input.nextLine().equalsIgnoreCase("y");

		TimePeriod period = new TimePeriod(unit, quantity);

		return new RentalDetails(veh_type, period, miles, insurance);
	}
	
	// prompts user to enter required information for making a reservation (VIN of vehicle to reserve, 
 	// credit card num, rental period, and optional insurance), returning the result packaged as a 
 	// ReservationDetails object (to pass in method calls to the SystemInterface)
	private ReservationDetails getReservationDetails(Scanner input) {
		System.out.print("Enter customer name: ");
		String name = input.nextLine();

		System.out.print("Enter credit card number: ");
		String credit_card_num = input.nextLine();
		String vin = getVIN(input);

		System.out.print("Rental unit (d/w/m): ");
		char unit = input.nextLine().toLowerCase().charAt(0);

		System.out.print("Quantity: ");
		int quantity = input.nextInt();

		input.nextLine();

		System.out.print("Has Insurance? (y/n): ");
		boolean insurance = input.nextLine().equalsIgnoreCase("y");

		TimePeriod period = new TimePeriod(unit, quantity);
		return new ReservationDetails(name, credit_card_num, period, insurance, vin);
	}
	
	// displays the array of strings passed, one string per screen line
	private void displayResults(String[] lines) {
		System.out.println();
		for (String line : lines) {
			System.out.println(line);
		}
	}
}

