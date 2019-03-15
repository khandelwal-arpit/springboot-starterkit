package com.starterkit.springboot.brs.controller.v1.api;

import com.starterkit.springboot.brs.controller.v1.request.BookTicketRequest;
import com.starterkit.springboot.brs.controller.v1.request.GetTripSchedulesRequest;
import com.starterkit.springboot.brs.dto.model.bus.TicketDto;
import com.starterkit.springboot.brs.dto.model.bus.TripDto;
import com.starterkit.springboot.brs.dto.model.bus.TripScheduleDto;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.dto.response.Response;
import com.starterkit.springboot.brs.service.BusReservationService;
import com.starterkit.springboot.brs.service.UserService;
import com.starterkit.springboot.brs.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Created by Arpit Khandelwal.
 */
@RestController
@RequestMapping("/api/v1/reservation")
@Api(value = "brs-application", description = "Operations pertaining to agency management and ticket issue in the BRS application")
public class BusReservationController {
    @Autowired
    private BusReservationService busReservationService;

    @Autowired
    private UserService userService;

    @GetMapping("/stops")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getAllStops() {
        return Response
                .ok()
                .setPayload(busReservationService.getAllStops());
    }

    @GetMapping("/tripsbystops")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getTripsByStops(@RequestBody @Valid GetTripSchedulesRequest getTripSchedulesRequest) {
        List<TripDto> tripDtos = busReservationService.getAvailableTripsBetweenStops(
                getTripSchedulesRequest.getSourceStop(),
                getTripSchedulesRequest.getDestinationStop());
        if (!tripDtos.isEmpty()) {
            return Response.ok().setPayload(tripDtos);
        }
        return Response.notFound()
                .setErrors(String.format("No trips between source stop - '%s' and destination stop - '%s' are available at this time.", getTripSchedulesRequest.getSourceStop(), getTripSchedulesRequest.getDestinationStop()));
    }

    @GetMapping("/tripschedules")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response getTripSchedules(@RequestBody @Valid GetTripSchedulesRequest getTripSchedulesRequest) {
        List<TripScheduleDto> tripScheduleDtos = busReservationService.getAvailableTripSchedules(
                getTripSchedulesRequest.getSourceStop(),
                getTripSchedulesRequest.getDestinationStop(),
                DateUtils.formattedDate(getTripSchedulesRequest.getTripDate()));
        if (!tripScheduleDtos.isEmpty()) {
            return Response.ok().setPayload(tripScheduleDtos);
        }
        return Response.notFound()
                .setErrors(String.format("No trips between source stop - '%s' and destination stop - '%s' on date - '%s' are available at this time.", getTripSchedulesRequest.getSourceStop(), getTripSchedulesRequest.getDestinationStop(), DateUtils.formattedDate(getTripSchedulesRequest.getTripDate())));
    }

    @PostMapping("/bookticket")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey")})
    public Response bookTicket(@RequestBody @Valid BookTicketRequest bookTicketRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) auth.getPrincipal();
        Optional<UserDto> userDto = Optional.ofNullable(userService.findUserByEmail(email));
        if (userDto.isPresent()) {
            Optional<TripDto> tripDto = Optional
                    .ofNullable(busReservationService.getTripById(bookTicketRequest.getTripID()));
            if (tripDto.isPresent()) {
                Optional<TripScheduleDto> tripScheduleDto = Optional
                        .ofNullable(busReservationService.getTripSchedule(tripDto.get(), DateUtils.formattedDate(bookTicketRequest.getTripDate()), true));
                if (tripScheduleDto.isPresent()) {
                    Optional<TicketDto> ticketDto = Optional
                            .ofNullable(busReservationService.bookTicket(tripScheduleDto.get(), userDto.get()));
                    if (ticketDto.isPresent()) {
                        return Response.ok().setPayload(ticketDto.get());
                    }
                }
            }
        }
        return Response.badRequest().setErrors("Unable to process ticket booking.");
    }
}
