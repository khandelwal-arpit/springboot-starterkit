package com.starterkit.springboot.brs.controller.v1.ui;

import com.starterkit.springboot.brs.controller.v1.command.*;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping(value = "/dashboard")
    public ModelAndView dashboard() {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("currentUser", userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @GetMapping(value = "/agency")
    public ModelAndView agencyDetails() {
        ModelAndView modelAndView = new ModelAndView("agency");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        AgencyFormCommand agencyFormCommand = new AgencyFormCommand()
                .setAgencyName(agencyDto.getName())
                .setAgencyDetails(agencyDto.getDetails());
        modelAndView.addObject("agencyFormData", agencyFormCommand);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/agency")
    public ModelAndView updateAgency(@Valid @ModelAttribute("agencyFormData") AgencyFormCommand agencyFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("agency");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            if (agencyDto != null) {
                agencyDto.setName(agencyFormCommand.getAgencyName())
                        .setDetails(agencyFormCommand.getAgencyDetails());
                busReservationService.updateAgency(agencyDto, null);
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/bus")
    public ModelAndView busDetails() {
        ModelAndView modelAndView = new ModelAndView("bus");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("busFormData", new BusFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/bus")
    public ModelAndView addNewBus(@Valid @ModelAttribute("busFormData") BusFormCommand busFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("bus");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("agency", agencyDto);
        if (!bindingResult.hasErrors()) {
            try {
                BusDto busDto = new BusDto()
                        .setCode(busFormCommand.getCode())
                        .setCapacity(busFormCommand.getCapacity())
                        .setMake(busFormCommand.getMake());
                AgencyDto updatedAgencyDto = busReservationService.updateAgency(agencyDto, busDto);
                modelAndView.addObject("agency", updatedAgencyDto);
                modelAndView.addObject("busFormData", new BusFormCommand());
            } catch (Exception ex) {
                bindingResult.rejectValue("code", "error.busFormCommand", ex.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/trip")
    public ModelAndView tripDetails() {
        ModelAndView modelAndView = new ModelAndView("trip");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        Set<StopDto> stops = busReservationService.getAllStops();
        List<TripDto> trips = busReservationService.getAgencyTrips(agencyDto.getCode());
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("stops", stops);
        modelAndView.addObject("trips", trips);
        modelAndView.addObject("tripFormData", new TripFormCommand());
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/trip")
    public ModelAndView addNewTrip(@Valid @ModelAttribute("tripFormData") TripFormCommand tripFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("trip");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        AgencyDto agencyDto = busReservationService.getAgency(userDto);
        Set<StopDto> stops = busReservationService.getAllStops();
        List<TripDto> trips = busReservationService.getAgencyTrips(agencyDto.getCode());

        modelAndView.addObject("stops", stops);
        modelAndView.addObject("agency", agencyDto);
        modelAndView.addObject("userName", userDto.getFullName());
        modelAndView.addObject("trips", trips);

        if (!bindingResult.hasErrors()) {
            try {
                TripDto tripDto = new TripDto()
                        .setSourceStopCode(tripFormCommand.getSourceStop())
                        .setDestinationStopCode(tripFormCommand.getDestinationStop())
                        .setBusCode(tripFormCommand.getBusCode())
                        .setJourneyTime(tripFormCommand.getTripDuration())
                        .setFare(tripFormCommand.getTripFare())
                        .setAgencyCode(agencyDto.getCode());
                busReservationService.addTrip(tripDto);

                trips = busReservationService.getAgencyTrips(agencyDto.getCode());
                modelAndView.addObject("trips", trips);
                modelAndView.addObject("tripFormData", new TripFormCommand());
            } catch (Exception ex) {
                bindingResult.rejectValue("sourceStop", "error.tripFormData", ex.getMessage());
            }
        }
        return modelAndView;
    }

    @GetMapping(value = "/profile")
    public ModelAndView getUserProfile() {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setMobileNumber(userDto.getMobileNumber());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("profileForm", profileFormCommand);
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        return modelAndView;
    }

    @PostMapping(value = "/profile")
    public ModelAndView updateProfile(@Valid @ModelAttribute("profileForm") ProfileFormCommand profileFormCommand, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        PasswordFormCommand passwordFormCommand = new PasswordFormCommand()
                .setEmail(userDto.getEmail())
                .setPassword(userDto.getPassword());
        modelAndView.addObject("passwordForm", passwordFormCommand);
        modelAndView.addObject("userName", userDto.getFullName());
        if (!bindingResult.hasErrors()) {
            userDto.setFirstName(profileFormCommand.getFirstName())
                    .setLastName(profileFormCommand.getLastName())
                    .setMobileNumber(profileFormCommand.getMobileNumber());
            userService.updateProfile(userDto);
            modelAndView.addObject("userName", userDto.getFullName());
        }
        return modelAndView;
    }

    @PostMapping(value = "/password")
    public ModelAndView changePassword(@Valid @ModelAttribute("passwordForm") PasswordFormCommand passwordFormCommand, BindingResult bindingResult) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto userDto = userService.findUserByEmail(auth.getName());
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("profile");
            ProfileFormCommand profileFormCommand = new ProfileFormCommand()
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            modelAndView.addObject("profileForm", profileFormCommand);
            modelAndView.addObject("userName", userDto.getFullName());
            return modelAndView;
        } else {
            userService.changePassword(userDto, passwordFormCommand.getPassword());
            return new ModelAndView("login");
        }
    }

}
