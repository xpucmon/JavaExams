package skyscraperelevator.domain.entities;

public class Building {
    private Integer buildingTopFloor;
    private Integer buildingBottomFloor;
    private Integer elevatorsCount;

    public Building(Integer buildingTopFloor, Integer buildingBottomFloor, Integer elevatorsCount) {
        this.buildingTopFloor = buildingTopFloor;
        this.buildingBottomFloor = buildingBottomFloor;
        this.elevatorsCount = elevatorsCount;
    }

    public Integer getBuildingTopFloor() {
        return buildingTopFloor;
    }

    public Integer getBuildingBottomFloor() {
        return buildingBottomFloor;
    }

    public Integer getElevatorsCount() {
        return elevatorsCount;
    }
}
