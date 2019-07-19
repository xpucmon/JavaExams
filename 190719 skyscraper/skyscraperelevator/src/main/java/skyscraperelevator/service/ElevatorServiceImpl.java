package skyscraperelevator.service;

import skyscraperelevator.domain.entities.Building;
import skyscraperelevator.domain.entities.Elevator;
import skyscraperelevator.domain.entities.Trip;
import skyscraperelevator.domain.enums.Status;
import skyscraperelevator.repository.ElevatorsRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ElevatorServiceImpl implements ElevatorService {
    //Assuming 1 stop's waiting time equals moving through 5 floors
    private static final int DOORS_OPEN_CLOSE_WAITING_TIME = 5;

    private Building building;
    private final ElevatorsRepository elevatorsRepository;

    public ElevatorServiceImpl(ElevatorsRepository elevatorsRepository) {
        this.elevatorsRepository = elevatorsRepository;
    }

    @Override
    public void createBuilding(Integer buildingTopFloor, Integer buildingBottomFloor, Integer elevatorsCount) {
        this.building = new Building(buildingTopFloor, buildingBottomFloor, elevatorsCount);
    }

    @Override
    public void createElevator(Integer elevatorMaxPassengers,
                               Integer elevatorMaxLoadInKg,
                               Integer topFloor, Integer bottomFloor) {
        this.elevatorsRepository
                .addElevator(new Elevator(elevatorMaxPassengers, elevatorMaxLoadInKg, topFloor, bottomFloor));
    }

    @Override
    public boolean checkFloorInput(Integer floor) {
        return floor >= this.building.getBuildingBottomFloor() && floor <= this.building.getBuildingTopFloor();
    }

    @Override
    public Trip assignElevator(Trip trip) {
        Elevator bestElevator = this.elevatorsRepository.getElevators().get(0);
        Integer minDistance = Integer.MAX_VALUE;
        Integer distanceToTripStart = Integer.MAX_VALUE;

        for (Elevator elevator : this.elevatorsRepository.getAllWaiting()) {
            distanceToTripStart = trip.getStartingFloor() > elevator.getCurrentFloor()
                    ? trip.getStartingFloor() - elevator.getCurrentFloor()
                    : elevator.getCurrentFloor() - trip.getStartingFloor();

            if (distanceToTripStart < minDistance && elevator.isEmpty()) {
                minDistance = distanceToTripStart;
                bestElevator = elevator;
            }
        }

        //TODO add waiting times (door open & close) to the next 2 moving elevators' formulas...
        for (Elevator elevator : this.elevatorsRepository.getAllMovingSameDirection(trip)) {
            if (elevator.getStatus().equals(Status.MOVING_UP)) {
                distanceToTripStart = elevator.getCurrentFloor() < trip.getStartingFloor()
                        ? trip.getStartingFloor() - elevator.getCurrentFloor()
                        : elevator.getCurrentDestination() - elevator.getCurrentFloor()
                        + elevator.getCurrentDestination() - trip.getStartingFloor();
            } else if (elevator.getStatus().equals(Status.MOVING_DOWN)) {
                distanceToTripStart = elevator.getCurrentFloor() > trip.getStartingFloor()
                        ? elevator.getCurrentFloor() - trip.getStartingFloor()
                        : elevator.getCurrentFloor() - elevator.getCurrentDestination()
                        + trip.getStartingFloor() - elevator.getCurrentDestination();

            }

            if (distanceToTripStart < minDistance && elevator.isEmpty()) {
                minDistance = distanceToTripStart;
                bestElevator = elevator;
            }
        }

        for (Elevator elevator : this.elevatorsRepository.getAllMovingWrongDirection(trip)) {
            if (trip.getDesiredStatus().equals(Status.MOVING_DOWN)) {
                distanceToTripStart = trip.getStartingFloor() > elevator.getCurrentDestination()
                        ? elevator.getCurrentDestination() - elevator.getCurrentFloor()
                        + elevator.getCurrentDestination() - trip.getStartingFloor()
                        : elevator.getCurrentDestination() - elevator.getCurrentFloor()
                        + elevator.getCurrentDestination() - trip.getStartingFloor();
            } else if (trip.getDesiredStatus().equals(Status.MOVING_UP)) {
                distanceToTripStart = trip.getStartingFloor() > elevator.getCurrentDestination()
                        ? elevator.getCurrentFloor() - elevator.getCurrentDestination()
                        + elevator.getCurrentDestination() - trip.getStartingFloor()
                        : elevator.getCurrentFloor() - elevator.getCurrentDestination()
                        + elevator.getCurrentDestination() - trip.getStartingFloor();
            }

            if (distanceToTripStart < minDistance && elevator.isEmpty()) {
                minDistance = distanceToTripStart;
                bestElevator = elevator;
            }
        }

        trip.setAssignedElevator(bestElevator);
        if (bestElevator.getStatus().equals(Status.WAITING)) {
            bestElevator.setStatus(trip.getStartingFloor() > bestElevator.getCurrentFloor()
                    ? Status.MOVING_UP : Status.MOVING_DOWN);
        }

        Integer bestElevatorId = this.elevatorsRepository.getElevatorId(bestElevator);

        this.addTripToElevator(bestElevatorId, trip);

        System.out.printf("Please wait for elevator number: %d%n", bestElevatorId);

        return trip;
    }

    @Override
    public void addTripToElevator(Integer elevatorId, Trip trip) {
        this.elevatorsRepository.getElevators().get(elevatorId)
                .addTrip(trip);

        this.elevatorsRepository.getElevators().get(elevatorId)
                .addDestinationFloor(trip.getStartingFloor());

        this.elevatorsRepository.getElevators().get(elevatorId)
                .addDestinationFloor(trip.getDestinationFloor());
    }

    @Override
    public List<Trip> findNotCompletedTrips(Elevator elevator) {
        return elevator.getTrips()
                .stream()
                .filter(trip -> !trip.getCurrentStatus().equals(Status.COMPLETED))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkElevatorCurrentLoad(Integer weight, Elevator elevator) {
        return weight <= elevator.getMaxLoad();
    }

    @Override
    public String completeRound() {
        StringBuilder roundStatus = new StringBuilder();

        for (Elevator elevator : this.elevatorsRepository.getElevators()) {

            elevator.removeDestinationFloor(elevator.getCurrentFloor());

            if (this.findNotCompletedTrips(elevator).isEmpty()) {
                elevator.setCurrentPassengersCount(0);
                elevator.setPendingPassengersCount(0);
                elevator.setStatus(Status.WAITING);
            }

            if (elevator.getStatus().equals(Status.MOVING_UP)
                    && elevator.getCurrentFloor() < elevator.getTopFloor()) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
            } else if (elevator.getStatus().equals(Status.MOVING_DOWN)
                    && elevator.getCurrentFloor() > elevator.getBottomFloor()) {
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
            }

            for (Trip trip : this.findNotCompletedTrips(elevator)) {
                if (elevator.getCurrentFloor().equals(trip.getStartingFloor())){
                    elevator.setCurrentPassengersCount(elevator.getCurrentPassengersCount() + 1);
                    trip.setCurrentStatus(trip.getDesiredStatus());
                    trip.setDesiredStatus(Status.COMPLETED);
                    elevator.setStatus(trip.getCurrentStatus());
                } else if (elevator.getCurrentFloor().equals(trip.getDestinationFloor())
                        && !trip.getCurrentStatus().equals(Status.WAITING)){
                    elevator.removeDestinationFloor(trip.getDestinationFloor());
                    elevator.setCurrentPassengersCount(elevator.getCurrentPassengersCount() - 1);
                    trip.setCurrentStatus(Status.COMPLETED);

                    if (elevator.getCurrentDestination() > elevator.getCurrentFloor()){
                        elevator.setStatus(Status.MOVING_UP);
                    } else if (elevator.getCurrentDestination() < elevator.getCurrentFloor()){
                        elevator.setStatus(Status.MOVING_DOWN);
                    } else elevator.setStatus(Status.WAITING);
                }
            }

            roundStatus.append(String.format("Elevator %d status: %s%n", elevatorsRepository.getElevatorId(elevator), elevator.getStatus()));
            roundStatus.append(String.format("--Current floor: %d%n", elevator.getCurrentFloor()));
            roundStatus.append(String.format("--Passengers count: %d%n", elevator.getCurrentPassengersCount()));
        }

        return roundStatus.toString();
    }
}
