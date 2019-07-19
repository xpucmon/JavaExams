package skyscraperelevator.domain.entities;

import skyscraperelevator.domain.enums.Status;

import java.util.*;

public class Elevator {
    private Integer topFloor;
    private Integer bottomFloor;
    private Integer currentFloor;
    private Status status;
    private List<Trip> trips;
    private List<Integer> destinationFloors;
    private Integer maxPassengersCapacity;
    private Integer currentPassengersCount;
    private Integer pendingPassengersCount;
    private Integer maxLoad;
    private Integer currentWeightLoad;
    private boolean isEmpty;

    public Elevator(Integer maxPassengersCapacity, Integer maxLoad, Integer topFloor, Integer bottomFloor) {
        this.topFloor = topFloor;
        this.bottomFloor = bottomFloor;
        this.maxPassengersCapacity = maxPassengersCapacity;
        this.maxLoad = maxLoad;
        this.setCurrentWeightLoad(0);
        this.setCurrentFloor(0);
        this.setStatus(Status.WAITING);
        this.trips = new ArrayList<>();
        this.destinationFloors = new LinkedList<>();
        this.setCurrentPassengersCount(0);
        this.setPendingPassengersCount(0);
    }

    public Integer getTopFloor() {
        return topFloor;
    }

    public void setTopFloor(Integer topFloor) {
        this.topFloor = topFloor;
    }

    public Integer getBottomFloor() {
        return bottomFloor;
    }

    public void setBottomFloor(Integer bottomFloor) {
        this.bottomFloor = bottomFloor;
    }

    public Integer getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Integer currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Trip> getTrips() {
        return Collections.unmodifiableList(this.trips);
    }

    public void setTrips(List<Trip> trips) {
        this.trips = trips;
    }

    public List<Integer> getDestinationFloors() {
        return destinationFloors;
    }

    public void setDestinationFloors(List<Integer> destinationFloors) {
        this.destinationFloors = Collections.unmodifiableList(this.destinationFloors);
    }

    public Integer getMaxPassengersCapacity() {
        return maxPassengersCapacity;
    }

    public void setMaxPassengersCapacity(Integer maxPassengersCapacity) {
        this.maxPassengersCapacity = maxPassengersCapacity;
    }

    public Integer getCurrentPassengersCount() {
        return currentPassengersCount;
    }

    public void setCurrentPassengersCount(Integer currentPassengersCount) {
        this.currentPassengersCount = currentPassengersCount;
    }

    public Integer getPendingPassengersCount() {
        return pendingPassengersCount;
    }

    public void setPendingPassengersCount(Integer pendingPassengersCount) {
        this.pendingPassengersCount = pendingPassengersCount;
    }

    public Integer getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Integer maxLoad) {
        this.maxLoad = maxLoad;
    }

    public Integer getCurrentWeightLoad() {
        return currentWeightLoad;
    }

    public void setCurrentWeightLoad(Integer currentWeightLoad) {
        this.currentWeightLoad = currentWeightLoad;
    }

    public boolean isEmpty() {
        return this.getMaxPassengersCapacity()
                - this.getCurrentPassengersCount()
                - this.getPendingPassengersCount() >= 1;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public void addDestinationFloor(Integer floor) {
        this.destinationFloors.add(floor);
    }

    public void removeDestinationFloor(Integer floor) {
        if (destinationFloors.contains(floor)) {
            this.destinationFloors.remove(this.destinationFloors.indexOf(floor));
        }
    }

    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public Integer getCurrentDestination() {
        Integer destinationFloor = this.getCurrentFloor();

        for (int i = 0; i < this.trips.size(); i++) {
            if (!trips.get(i).getCurrentStatus().equals(Status.COMPLETED)){
                destinationFloor = trips.get(i).getStartingFloor();
                break;
            }
        }

        return destinationFloor;
    }
}