package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Agency;
import com.starterkit.springboot.brs.model.bus.Bus;
import com.starterkit.springboot.brs.model.bus.Stop;
import com.starterkit.springboot.brs.model.bus.Trip;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Arpit Khandelwal.
 */
public interface TripRepository extends MongoRepository<Trip, String> {
    Trip findBySourceStopAndDestStopAndBus(Stop source, Stop destination, Bus bus);

    List<Trip> findAllBySourceStopAndDestStop(Stop source, Stop destination);

    List<Trip> findByAgency(Agency agency);
}
