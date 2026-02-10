package com.example.verification.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "rides")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Driver
    private String driverEmail;

    // Route
    private String sourceCity;
    private String destinationCity;

    // Pricing
    private double baseFare;
    private double farePerKm;
    private double distance;
    private double totalFare;

    // Date
    private LocalDate rideDate;

    // Locations (stored as JSON string for now)
    @Column(columnDefinition = "TEXT")
    private String pickupLocations;

    @Column(columnDefinition = "TEXT")
    private String dropLocations;

    // Vehicle
    private String vehicleNumber;
    private String licenseNumber;
    private int availableSeats;

    // Images (paths or URLs)
    @ElementCollection
    @CollectionTable(name = "ride_images", joinColumns = @JoinColumn(name = "ride_id"))
    @Column(name = "image_url")
    private List<String> vehicleImages;

    // Metadata
    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public double getFarePerKm() {
        return farePerKm;
    }

    public void setFarePerKm(double farePerKm) {
        this.farePerKm = farePerKm;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getVehicleImages() {
        return vehicleImages;
    }

    public void setVehicleImages(List<String> vehicleImages) {
        this.vehicleImages = vehicleImages;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public String getDropLocations() {
        return dropLocations;
    }

    public void setDropLocations(String dropLocations) {
        this.dropLocations = dropLocations;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public LocalDate getRideDate() {
        return rideDate;
    }

    public void setRideDate(LocalDate rideDate) {
        this.rideDate = rideDate;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public String getPickupLocations() {
        return pickupLocations;
    }

    public void setPickupLocations(String pickupLocations) {
        this.pickupLocations = pickupLocations;
    }
}
