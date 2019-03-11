package com.starterkit.springboot.brs.dto.mapper;

import com.starterkit.springboot.brs.dto.model.bus.TripDto;
import com.starterkit.springboot.brs.model.bus.Trip;

/**
 * Created by Arpit Khandelwal.
 */
public class TripMapper {
    public static TripDto toTripDto(Trip trip) {
        return new TripDto()
                .setId(trip.getId())
                .setAgencyCode(trip.getAgency().getCode())
                .setSourceStopCode(trip.getSourceStop().getCode())
                .setSourceStopName(trip.getSourceStop().getName())
                .setDestinationStopCode(trip.getDestStop().getCode())
                .setDestinationStopName(trip.getDestStop().getName())
                .setBusCode(trip.getBus().getCode())
                .setJourneyTime(trip.getJourneyTime())
                .setFare(trip.getFare());
    }
}
