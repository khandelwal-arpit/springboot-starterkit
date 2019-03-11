package com.starterkit.springboot.brs;

import com.starterkit.springboot.brs.model.bus.*;
import com.starterkit.springboot.brs.model.user.Role;
import com.starterkit.springboot.brs.model.user.User;
import com.starterkit.springboot.brs.repository.bus.*;
import com.starterkit.springboot.brs.repository.user.RoleRepository;
import com.starterkit.springboot.brs.repository.user.UserRepository;
import com.starterkit.springboot.brs.util.DateUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BusReservationSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusReservationSystemApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, UserRepository userRepository,
                           StopRepository stopRepository, AgencyRepository agencyRepository,
                           BusRepository busRepository, TripRepository tripRepository,
                           TripScheduleRepository tripScheduleRepository) {
        return args -> {
            //Create Admin and Passenger Roles
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setRole("ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByRole("PASSENGER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setRole("PASSENGER");
                roleRepository.save(userRole);
            }

            //Create an Admin user
            User admin = userRepository.findByEmail("admin.agencya@gmail.com");
            if (admin == null) {
                admin = new User()
                        .setEmail("admin.agencya@gmail.com")
                        .setPassword("$2a$10$7PtcjEnWb/ZkgyXyxY1/Iei2dGgGQUbqIIll/dt.qJ8l8nQBWMbYO") // "123456"
                        .setFirstName("John")
                        .setLastName("Doe")
                        .setMobileNumber("9425094250")
                        .setRoles(new HashSet<>(Arrays.asList(adminRole)));
                userRepository.save(admin);
            }

            //Create four stops
            Stop stopA = stopRepository.findByCode("STPA");
            if (stopA == null) {
                stopA = new Stop()
                        .setName("Stop A")
                        .setDetail("Near hills")
                        .setCode("STPA");
                stopRepository.save(stopA);
            }

            Stop stopB = stopRepository.findByCode("STPB");
            if (stopB == null) {
                stopB = new Stop()
                        .setName("Stop B")
                        .setDetail("Near river")
                        .setCode("STPB");
                stopRepository.save(stopB);
            }

            Stop stopC = stopRepository.findByCode("STPC");
            if (stopC == null) {
                stopC = new Stop()
                        .setName("Stop C")
                        .setDetail("Near desert")
                        .setCode("STPC");
                stopRepository.save(stopC);
            }

            Stop stopD = stopRepository.findByCode("STPD");
            if (stopD == null) {
                stopD = new Stop()
                        .setName("Stop D")
                        .setDetail("Near lake")
                        .setCode("STPD");
                stopRepository.save(stopD);
            }

            //Create an Agency
            Agency agencyA = agencyRepository.findByCode("AGENCYA");
            if (agencyA == null) {
                agencyA = new Agency()
                        .setName("Green Mile Agency")
                        .setCode("AGENCYA")
                        .setDetails("Reaching desitnations with ease")
                        .setOwner(admin);
                agencyRepository.save(agencyA);
            }

            //Create a bus
            Bus busA = busRepository.findByCode("AGENCYA-1");
            if (busA == null) {
                busA = new Bus()
                        .setCode("AGENCYA-1")
                        .setAgency(agencyA)
                        .setCapacity(60);
                busRepository.save(busA);
            }

            //Add busA to set of buses owned by Agency 'AGENCYA'
            if (agencyA.getBuses() == null) {
                Set<Bus> buses = new HashSet<>();
                agencyA.setBuses(buses);
                agencyA.getBuses().add(busA);
                agencyRepository.save(agencyA);
            }

            //Create a Trip
            Trip trip = tripRepository.findBySourceStopAndDestStopAndBus(stopA, stopB, busA);
            if (trip == null) {
                trip = new Trip()
                        .setSourceStop(stopA)
                        .setDestStop(stopB)
                        .setBus(busA)
                        .setAgency(agencyA)
                        .setFare(100)
                        .setJourneyTime(60);
                tripRepository.save(trip);
            }

            //Create a trip schedule
            TripSchedule tripSchedule = tripScheduleRepository.findByTripDetailAndTripDate(trip, DateUtils.todayStr());
            if (tripSchedule == null) {
                tripSchedule = new TripSchedule()
                        .setTripDetail(trip)
                        .setTripDate(DateUtils.todayStr())
                        .setAvailableSeats(trip.getBus().getCapacity());
                tripScheduleRepository.save(tripSchedule);
            }
        };
    }
}
