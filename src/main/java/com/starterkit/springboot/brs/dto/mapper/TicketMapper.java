package com.starterkit.springboot.brs.dto.mapper;

import com.starterkit.springboot.brs.dto.model.bus.TicketDto;
import com.starterkit.springboot.brs.model.bus.Ticket;

/**
 * Created by Arpit Khandelwal.
 */
public class TicketMapper {
    public static TicketDto toTicketDto(Ticket ticket) {
        return new TicketDto()
                .setId(ticket.getId())
                .setBusCode(ticket.getTripSchedule().getTripDetail().getBus().getCode())
                .setSeatNumber(ticket.getSeatNumber())
                .setSourceStop(ticket.getTripSchedule().getTripDetail().getSourceStop().getName())
                .setDestinationStop(ticket.getTripSchedule().getTripDetail().getDestStop().getName())
                .setCancellable(false)
                .setJourneyDate(ticket.getJourneyDate())
                .setPassengerName(ticket.getPassenger().getFullName())
                .setPassengerMobileNumber(ticket.getPassenger().getMobileNumber());
    }
}
