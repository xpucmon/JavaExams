package skyscraperelevator.repository;

import skyscraperelevator.domain.entities.Trip;

import java.util.List;

public interface TripsRepository {

    List<Trip> getTrips();

    void addTrip(Trip trip);
}
