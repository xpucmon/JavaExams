package skyscraperelevator;

import skyscraperelevator.domain.entities.Trip;
import skyscraperelevator.io.Reader;
import skyscraperelevator.io.Writer;
import skyscraperelevator.service.ElevatorService;
import skyscraperelevator.service.TripService;

import java.io.IOException;

public class Engine implements Runnable {
    private final Reader reader;
    private final Writer writer;
    private final ElevatorService elevatorService;
    private final TripService tripService;

    public Engine(Reader reader, Writer writer, ElevatorService elevatorService, TripService tripService) {
        this.reader = reader;
        this.writer = writer;
        this.elevatorService = elevatorService;
        this.tripService = tripService;
    }

    @Override
    public void run() {

        try {
            writer.write("Please proceed with building setup!\n");

            writer.write("Input desired number of floors above the ground: ");
            Integer topFloor = Integer.parseInt(reader.read());

            writer.write("Input building's number of underground levels: ");
            Integer bottomFloor = -Integer.parseInt(reader.read());

            writer.write("Input desired number of elevators in the building: ");
            Integer elevatorsCount = Integer.parseInt(reader.read());

            this.elevatorService.createBuilding(topFloor, bottomFloor, elevatorsCount);

            writer.write("Input max number of passengers per elevator: ");
            Integer elevatorMaxPassengers = Integer.parseInt(reader.read());
            Integer elevatorMaxLoadInKg = elevatorMaxPassengers * 80;

            for (int i = 1; i <= elevatorsCount; i++) {
                this.elevatorService.createElevator(elevatorMaxPassengers, elevatorMaxLoadInKg, topFloor, bottomFloor);
            }

            writer.write("Press the 'Enter' key to begin simulation or write 'end' to exit!");

            String input;
            while (!"end".equals(input = reader.read().toLowerCase())) {

                writer.write("Simulation is running...\n");

                //Getting passengers' requests
                writer.write("Press 'Enter' to proceed with new passengers' requests OR enter 'OK' to start the round!");

                while (!"OK".equals(input = reader.read().toUpperCase())) {

                    //TODO Add validation...Assumptions are this cannot be null, empty, string, etc...
                    writer.write("Passenger, please input your current floor: ");
                    Integer startFloor;
                    while (!this.elevatorService.checkFloorInput(startFloor = Integer.parseInt(reader.read()))) {
                        writer.write("Wrong floor. Please enter again: ");
                    }

                    writer.write("Passenger, please input your destination floor: ");
                    Integer destinationFloor;
                    while (!this.elevatorService.checkFloorInput(destinationFloor =  Integer.parseInt(reader.read()))
                            || destinationFloor.equals(startFloor)) {
                        writer.write("Wrong floor. Please enter again: ");
                    }

                    this.tripService.createTrip(new Trip(startFloor, destinationFloor));

                    writer.write("Press 'Enter' to proceed with new passengers' requests OR enter 'OK' to start the round!");
                }

                //Elevators proceeding with trips (1 floor movement if on a trip OR 1 round waiting if no trip)
                writer.write(this.elevatorService.completeRound());
                writer.write("Press the 'Enter' key to continue simulation or write 'end' to exit!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        writer.write("Already? That was quick... Have a nice day! :)");
    }
}