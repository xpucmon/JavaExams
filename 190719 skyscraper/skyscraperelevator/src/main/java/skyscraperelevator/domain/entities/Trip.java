package skyscraperelevator.domain.entities;

import skyscraperelevator.domain.enums.Status;

public class Trip {
    private Integer startingFloor;
    private Integer destinationFloor;
    private Status currentStatus;
    private Status desiredStatus;
    private Elevator assignedElevator;

    public Trip(Integer startingFloor, Integer destinationFloor) {
        this.startingFloor = startingFloor;
        this.destinationFloor = destinationFloor;
        this.setCurrentStatus(Status.WAITING);
        this.setDesiredStatus(Status.WAITING);
    }

    public Integer getStartingFloor() {
        return startingFloor;
    }

    public void setStartingFloor(Integer startingFloor) {
        this.startingFloor = startingFloor;
    }

    public Integer getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(Integer destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(Status currentStatus) {
        this.currentStatus = currentStatus;
    }

    public Status getDesiredStatus() {
        return desiredStatus;
    }

    public void setDesiredStatus(Status desiredStatus) {
        this.desiredStatus = desiredStatus;
    }

    public Elevator getAssignedElevator() {
        return assignedElevator;
    }

    public void setAssignedElevator(Elevator assignedElevator) {
        this.assignedElevator = assignedElevator;
    }
}
