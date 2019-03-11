package com.starterkit.springboot.brs.controller.v1.ui;

import com.starterkit.springboot.brs.controller.v1.request.AddTripRequest;
import com.starterkit.springboot.brs.controller.v1.request.CreateBusRequest;
import com.starterkit.springboot.brs.controller.v1.request.UpdateProfileRequest;
import com.starterkit.springboot.brs.dto.model.bus.AgencyDto;
import com.starterkit.springboot.brs.dto.model.bus.BusDto;
import com.starterkit.springboot.brs.dto.model.bus.StopDto;
import com.starterkit.springboot.brs.dto.model.bus.TripDto;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.service.BusReservationService;
import com.starterkit.springboot.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Created by Arpit Khandelwal.
 */
@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private BusReservationService busReservationService;

    @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("/dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/agency", method = RequestMethod.GET)
    public ModelAndView agencyDetails() {
        ModelAndView modelAndView = new ModelAndView("/agency");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/agency", method = RequestMethod.POST)
    public ModelAndView updateAgency(@RequestParam("agencyName") String agencyName, @RequestParam("agencyDetails") String agencyDetails) {
        ModelAndView modelAndView = new ModelAndView("/agency");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        if (agencyDto != null) {
            agencyDto.setName(agencyName)
                    .setDetails(agencyDetails);
            busReservationService.updateAgency(agencyDto, null);
            modelAndView.addObject("agency", agencyDto);
            modelAndView.addObject("user", userDto);
            modelAndView.addObject("userName", userDto.getFullName());
        }
        return modelAndView;
    }

    @RequestMapping(value = "/bus", method = RequestMethod.GET)
    public ModelAndView busDetails() {
        ModelAndView modelAndView = new ModelAndView("/bus");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/bus", method = RequestMethod.POST)
    public ModelAndView addNewBus(@Valid CreateBusRequest createBusRequest, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("/bus");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        BusDto busDto = new BusDto()
                .setCode(createBusRequest.getCode())
                .setCapacity(createBusRequest.getCapacity())
                .setMake(createBusRequest.getMake());
        AgencyDto updatedAgencyDto = busReservationService.updateAgency(agencyDto, busDto);
        modelAndView.addObject("agency", updatedAgencyDto);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/trip", method = RequestMethod.GET)
    public ModelAndView tripDetails() {
        ModelAndView modelAndView = new ModelAndView("/trip");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        Set<StopDto> stops = busReservationService.getAllStops();
        List<TripDto> trips = busReservationService.getAgencyTrips(agencyDto.getCode());
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("stops", stops);
        modelAndView.addObject("trips", trips);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/trip", method = RequestMethod.POST)
    public ModelAndView addNewTrip(@Valid AddTripRequest addTripRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        TripDto tripDto = new TripDto()
                .setSourceStopCode(addTripRequest.getSourceStop())
                .setDestinationStopCode(addTripRequest.getDestinationStop())
                .setBusCode(addTripRequest.getBusCode())
                .setJourneyTime(addTripRequest.getTripDuration())
                .setFare(addTripRequest.getTripFare())
                .setAgencyCode(agencyDto.getCode());
        busReservationService.addTrip(tripDto);

        Set<StopDto> stops = busReservationService.getAllStops();
        List<TripDto> trips = busReservationService.getAgencyTrips(agencyDto.getCode());
        ModelAndView modelAndView = new ModelAndView("/trip");
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("user", userDto);
        modelAndView.addObject("stops", stops);
        modelAndView.addObject("trips", trips);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView("/profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public ModelAndView updateProfile(@Valid UpdateProfileRequest updateProfileRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        userDto.setFirstName(updateProfileRequest.getFirstName())
                .setLastName(updateProfileRequest.getLastName())
                .setMobileNumber(updateProfileRequest.getMobileNumber());

        ModelAndView modelAndView = new ModelAndView("/profile");
        modelAndView.addObject("currentUser", userService.updateProfile(userDto));
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @RequestMapping(value = "/password", method = RequestMethod.POST)
    public ModelAndView changePassword(@RequestParam("password") String newPassword) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        userService.changePassword(userDto, newPassword);
        return new ModelAndView("/login");
    }

}
