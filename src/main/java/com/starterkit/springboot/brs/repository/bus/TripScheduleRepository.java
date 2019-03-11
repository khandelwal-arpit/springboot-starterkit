package com.starterkit.springboot.brs.repository.bus;

import com.starterkit.springboot.brs.model.bus.Trip;
import com.starterkit.springboot.brs.model.bus.TripSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Arpit Khandelwal.
 */
public interface TripScheduleRepository extends MongoRepository<TripSchedule, String> {
    TripSchedule findByTripDetailAndTripDate(Trip tripDetail, String tripDate);
}