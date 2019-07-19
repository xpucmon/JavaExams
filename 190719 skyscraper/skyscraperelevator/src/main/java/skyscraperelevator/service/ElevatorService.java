package skyscraperelevator.service;

import skyscraperelevator.domain.entities.Elevator;
import skyscraperelevator.domain.entities.Trip;

import java.util.List;

public interface ElevatorService {

    void createBuilding(Integer buildingTopFloor, Integer buildingBottomFloor, Integer elevatorsCount);

    void createElevator(Integer elevatorMaxPassengers, Integer elevatorMaxLoadInKg, Integer topFloor, Integer bottomFloor);

    boolean checkFloorInput(Integer floor);

    Trip assignElevator(Trip trip);

    void addTripToElevator(Integer elevatorId, Trip trip);

    List<Trip> findNotCompletedTrips(Elevator elevator);

    boolean checkElevatorCurrentLoad(Integer weight, Elevator elevator);

    String completeRound();
}