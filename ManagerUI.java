import java.util.Scanner;

public class ManagerUI implements UserInterface {
    
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

    private void execute(int selection, Scanner input) {

        String[] display_lines;;
        int vehicleType;

        switch (selection) {
            case 1:
                vehicleType = getVehicleType(input);
                display_lines = displayAndUpdateRates(vehicleType, input);
                displayResults(display_lines);
                break;

            case 2:
                display_lines = SystemInterface.getAllVehicles();
                displayResults(display_lines);
                break;

            case 3:
                display_lines = SystemInterface.getAllReservations();
                displayResults(display_lines);
                break;

            case 4:
                display_lines = SystemInterface.getAllTransactions();
                displayResults(display_lines);
                break;

            case 5:
                quit = true;
        }

    }

    private void displayMenu() {
		System.out.println("\nManager Menu:");
		System.out.println("1 - Update Current Rates");
		System.out.println("2 - Display All Vehicles");
		System.out.println("3 - Display All Reservations");
		System.out.println("4 - Display All Transactions");
		System.out.println("5 - Quit Menu");
	}
    
    private int getSelection(Scanner input) {
		int selection;
				do {
					System.out.print("Enter a number 1 through 5: ");

					while (!input.hasNextInt()) {
						System.out.print("Invaid selection. Try again: ");
						input.next();
					}
                    selection = input.nextInt();
                    input.nextLine();
		
				} while (selection < 1 || selection > 5);

		input.nextLine();
		return selection;
	}

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

    private String[] displayAndUpdateRates(int veh_type, Scanner input) {
        String[] curr_rates;
        double daily, weekly, monthly, per_mile_charge, insurance;
        switch (veh_type) {
            case 1: curr_rates = SystemInterface.getCarRates(); break;
            case 2: curr_rates = SystemInterface.getSUVRates(); break;
            case 3: curr_rates = SystemInterface.getMinivanRates(); break;
            default: return new String[]{"Invalid vehicle type"};
        }

        System.out.println("\nCurrent Rates:");
        displayResults(curr_rates);

        System.out.print("Update rates? (y/n): ");
        if (!input.nextLine().equalsIgnoreCase("y")) {
            return new String[]{"Rates not updated."};
        }

        System.out.print("Enter new daily rate: ");
        daily = input.nextDouble();

        System.out.print("Enter new weekly rate: ");
        weekly = input.nextDouble();

        System.out.print("Enter new monthly rate: ");
        monthly = input.nextDouble();

        System.out.print("Enter new per mile charge: ");
        per_mile_charge = input.nextDouble();

        System.out.print("Enter new daily insurance rate: ");
        insurance = input.nextDouble();

        input.nextLine();

        VehicleRates update_veh_rates = new VehicleRates(daily, weekly, monthly, per_mile_charge, insurance);

        switch (veh_type) {
            case 1: SystemInterface.updateCarRates(update_veh_rates); break;
            case 2: SystemInterface.updateSUVRates(update_veh_rates); break;
            case 3: SystemInterface.updateMinivanRates(update_veh_rates); break;
        }

        return new String[]{"Rate update failed"};
    }

    private void displayResults(String[] lines) {
        System.out.println();
        for (String line : lines) {
            System.out.println(line);
        }
    }

}
