package skyscraperelevator.service;

import skyscraperelevator.domain.entities.Trip;
import skyscraperelevator.domain.enums.Status;
import skyscraperelevator.repository.TripsRepository;

public class TripServiceImpl implements TripService {
    private final TripsRepository tripsRepository;
    private final ElevatorService elevatorService;

    public TripServiceImpl(TripsRepository tripsRepository, ElevatorService elevatorService) {
        this.tripsRepository = tripsRepository;
        this.elevatorService = elevatorService;
    }

    @Override
    public void createTrip(Trip trip) {
        trip.setDesiredStatus(trip.getDestinationFloor() > trip.getStartingFloor()
                ? Status.MOVING_UP : Status.MOVING_DOWN);

        this.tripsRepository.addTrip(this.elevatorService.assignElevator(trip));
    }
}