package com.starterkit.springboot.brs.controller.v1.ui;


import com.starterkit.springboot.brs.controller.v1.request.AdminSignupRequest;
import com.starterkit.springboot.brs.dto.model.bus.AgencyDto;
import com.starterkit.springboot.brs.dto.model.user.UserDto;
import com.starterkit.springboot.brs.model.user.User;
import com.starterkit.springboot.brs.service.BusReservationService;
import com.starterkit.springboot.brs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * Created by Arpit Khandelwal.
 */

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    BusReservationService busReservationService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        return "redirect:/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
        return "redirect:/dashboard";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        ModelAndView modelAndView = new ModelAndView("/signup");
        User user = new User();
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView createNewAdmin(@Valid AdminSignupRequest userSignupRequest, BindingResult bindingResult) {
        try {
            UserDto newUser = registerAdmin(userSignupRequest);
        } catch (Exception exception) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email " + userSignupRequest.getEmail() + " provided");
        }
        return new ModelAndView("/login");
    }

    /**
     * Register a new user in the database
     *
     * @param adminSignupRequest
     * @return
     */
    private UserDto registerAdmin(AdminSignupRequest adminSignupRequest) {
        UserDto userDto = new UserDto()
                .setEmail(adminSignupRequest.getEmail())
                .setPassword(adminSignupRequest.getPassword())
                .setFirstName(adminSignupRequest.getFirstName())
                .setLastName(adminSignupRequest.getLastName())
                .setMobileNumber(adminSignupRequest.getMobileNumber())
                .setAdmin(true);
        UserDto admin = userService.signup(userDto); //register the admin
        AgencyDto agencyDto = new AgencyDto()
                .setName(adminSignupRequest.getAgencyName())
                .setDetails(adminSignupRequest.getAgencyDetails())
                .setOwner(admin);
        busReservationService.addAgency(agencyDto); //add the agency for this admin
        return admin;
    }
}
