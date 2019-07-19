package skyscraperelevator.repository;

import skyscraperelevator.domain.entities.Elevator;
import skyscraperelevator.domain.entities.Trip;

import java.util.List;

public interface ElevatorsRepository {
    List<Elevator> getElevators();

    void addElevator(Elevator elevator);

    Integer getElevatorId(Elevator elevator);

    List<Elevator> getAllWaiting();

    List<Elevator> getAllMovingSameDirection(Trip trip);

    List<Elevator> getAllMovingWrongDirection(Trip trip);
}
