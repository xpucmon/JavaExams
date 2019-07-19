package skyscraperelevator.repository;

import skyscraperelevator.domain.entities.Trip;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TripsRepositoryImpl implements TripsRepository {
    private List<Trip> trips;

    public TripsRepositoryImpl() {
        this.trips = new LinkedList<>();
    }

    @Override
    public List<Trip> getTrips() {
        return Collections.unmodifiableList(this.trips);
    }

    @Override
    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }
}
