package com.example.verification.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.verification.model.Ride;
import com.example.verification.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;


import java.util.List;

@RestController
@RequestMapping("/v1/rides")
@CrossOrigin(origins = "http://localhost:5173")
public class RideController {
    @Autowired
    private RideRepository rideRepository;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> addRide(
            @RequestParam String driverEmail,
            @RequestParam String sourceCity,
            @RequestParam String destinationCity,

            @RequestParam double baseFare,
            @RequestParam double farePerKm,
            @RequestParam double distance,
            @RequestParam double totalFare,
            @RequestParam String date,
            @RequestParam String pickupLocations,
            @RequestParam String dropLocations,
            @RequestParam String vehicleNumber,
            @RequestParam String licenseNumber,
            @RequestParam int availableSeats,
            @RequestParam(required = false) List<MultipartFile> vehicleImages
    ) {

        Ride ride = new Ride();
        ride.setDriverEmail(driverEmail);
        ride.setSourceCity(sourceCity);
        ride.setDestinationCity(destinationCity);

        ride.setBaseFare(baseFare);
        ride.setFarePerKm(farePerKm);
        ride.setDistance(distance);
        ride.setTotalFare(totalFare);
        ride.setRideDate(LocalDate.parse(date));
        ride.setPickupLocations(pickupLocations);
        ride.setDropLocations(dropLocations);
        ride.setVehicleNumber(vehicleNumber);
        ride.setLicenseNumber(licenseNumber);
        ride.setAvailableSeats(availableSeats);

        // TODO: save images and set paths
        ride.setVehicleImages(new ArrayList<>());

        rideRepository.save(ride);

        return ResponseEntity.ok("Ride added successfully");
    }

}

