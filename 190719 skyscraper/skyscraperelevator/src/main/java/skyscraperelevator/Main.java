package skyscraperelevator;

import skyscraperelevator.io.ConsoleReader;
import skyscraperelevator.io.ConsoleWriter;
import skyscraperelevator.io.Reader;
import skyscraperelevator.io.Writer;
import skyscraperelevator.repository.ElevatorsRepositoryImpl;
import skyscraperelevator.repository.TripsRepositoryImpl;
import skyscraperelevator.service.ElevatorService;
import skyscraperelevator.service.ElevatorServiceImpl;
import skyscraperelevator.service.TripService;
import skyscraperelevator.service.TripServiceImpl;

public class Main {
    public static void main(String[] args) {

        Reader reader = new ConsoleReader();
        Writer writer = new ConsoleWriter();

        ElevatorService elevatorService = new ElevatorServiceImpl(new ElevatorsRepositoryImpl());
        TripService tripService = new TripServiceImpl(new TripsRepositoryImpl(), elevatorService);

        Runnable engine = new Engine(reader, writer, elevatorService, tripService);

        engine.run();
    }
}
