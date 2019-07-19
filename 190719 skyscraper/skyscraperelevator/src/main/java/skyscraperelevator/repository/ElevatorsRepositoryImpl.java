package skyscraperelevator.repository;

import skyscraperelevator.domain.entities.Elevator;
import skyscraperelevator.domain.entities.Trip;
import skyscraperelevator.domain.enums.Status;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ElevatorsRepositoryImpl implements ElevatorsRepository {
    private List<Elevator> elevators;

    public ElevatorsRepositoryImpl() {
        this.elevators = new LinkedList<>();

    }

    @Override
    public List<Elevator> getElevators() {
            return Collections.unmodifiableList(this.elevators);
    }

    @Override
    public void addElevator(Elevator elevator) {
        this.elevators.add(elevator);
    }

    @Override
    public Integer getElevatorId(Elevator elevator) {
        return this.elevators.indexOf(elevator);
    }

    @Override
    public List<Elevator> getAllWaiting() {
        return this.elevators
                .stream()
                .filter(elevator -> (elevator.getStatus().equals(Status.WAITING)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Elevator> getAllMovingSameDirection(Trip trip) {
        return this.elevators
                .stream()
                .filter(elevator -> (elevator.getStatus().equals(trip.getDesiredStatus())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Elevator> getAllMovingWrongDirection(Trip trip) {
        return this.elevators
                .stream()
                .filter(elevator -> (!elevator.getStatus().equals(Status.WAITING)
                        && !elevator.getStatus().equals(trip.getDesiredStatus())))
                .collect(Collectors.toList());
    }
}
