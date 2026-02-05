////package com.example.verification.controller;
////
////import com.example.verification.model.DriverDetails;
////import com.example.verification.model.User;
////import com.example.verification.repository.DriverDetailsRepository;
////import com.example.verification.repository.UserRepository;
////import com.example.verification.service.EmailService;
////import com.example.verification.service.VerificationService;
////
////import jakarta.persistence.Lob;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.util.HashMap;
////import java.util.Map;
////
////@RestController
////@RequestMapping("/api")
////@CrossOrigin(origins = "http://localhost:5173")
////public class VerificationController {
////
////    @Autowired
////    private UserRepository userRepo;
////
////    @Autowired
////    private DriverDetailsRepository driverRepo;
////
////    @Autowired
////    private EmailService emailService;
////
////    @Autowired
////    private VerificationService verificationService;
////
////    @Lob
////    private byte[] vehicleImage;
////
////   // ✅ RESET PASSWORD AND SEND NEW VERIFICATION CODE
////@PostMapping("/reset-password")
////public Map<String, Object> resetPassword(@RequestBody Map<String, String> body) {
////    Map<String, Object> response = new HashMap<>();
////    String email = body.get("email");
////    String newPassword = body.get("newPassword");
////
////    try {
////        User user = userRepo.findByEmail(email).orElse(null);
////        if (user == null) {
////            response.put("success", false);
////            response.put("message", "No user found with that email!");
////            return response;
////        }
////
////        // ✅ Update password and reset verification
////        user.setPassword(newPassword);
////        user.setVerified(false);
////        userRepo.save(user);
////
////        // ✅ Generate and send new verification code
////        String code = verificationService.generateCode(email);
////        emailService.sendVerificationEmail(email, code);
////
////        response.put("success", true);
////        response.put("message", "Password updated successfully! Verification code sent to your email.");
////    } catch (Exception e) {
////        response.put("success", false);
////        response.put("message", "Error resetting password: " + e.getMessage());
////    }
////
////    return response;
////}
////
////
////
////    // ✅ USER REGISTRATION — send verification code to email
////    @PostMapping("/register")
////    public Map<String, Object> registerUser(@RequestBody User user) {
////        Map<String, Object> response = new HashMap<>();
////        try {
////            if (user.getEmail() == null || user.getEmail().isEmpty()) {
////                response.put("success", false);
////                response.put("message", "Email is required!");
////                return response;
////            }
////
////            userRepo.save(user);
////
////            // ✅ Only send verification if the role is NOT DRIVER
////        if (!"driver".equalsIgnoreCase(user.getRole())) {
////            String code = verificationService.generateCode(user.getEmail());
////            emailService.sendVerificationEmail(user.getEmail(), code);
////            response.put("message", "Verification code sent to " + user.getEmail());
////        } else {
////            response.put("message", "Driver registered successfully. Please complete your vehicle details next.");
////        }
////            response.put("success", true);
////
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Registration failed: " + e.getMessage());
////        }
////        return response;
////    }
////
////    // ✅ USER LOGIN
////    @PostMapping("/login")
////    public Map<String, Object> loginUser(@RequestBody Map<String, String> body) {
////        Map<String, Object> response = new HashMap<>();
////        String email = body.get("email");
////        String password = body.get("password");
////        String role = body.get("role");
////
////        try {
////            User user = userRepo.findByEmail(email).orElse(null);
////            if (user == null) {
////                response.put("success", false);
////                response.put("message", "No user found with this email!");
////                return response;
////            }
////
////            if (!user.getPassword().equals(password)) {
////                response.put("success", false);
////                response.put("message", "Invalid password!");
////                return response;
////            }
////
////            if (!user.getRole().equalsIgnoreCase(role)) {
////                response.put("success", false);
////                response.put("message", "Role mismatch!");
////                return response;
////            }
////
////            if (!user.isVerified()) {
////                response.put("success", false);
////                response.put("message", "Account not verified! Please verify your email.");
////                return response;
////            }
////
////            response.put("success", true);
////            response.put("message", "Login successful!");
////            response.put("user", user.getFullName());
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Error during login: " + e.getMessage());
////        }
////
////        return response;
////    }
////
////
////
////    // ✅ VERIFY CODE
////    @PostMapping("/verify")
////    public Map<String, Object> verifyCode(@RequestBody Map<String, String> body) {
////        Map<String, Object> response = new HashMap<>();
////        String email = body.get("email");
////        String code = body.get("code");
////
////        try {
////            if (email == null || email.isEmpty()) {
////                response.put("success", false);
////                response.put("message", "Email is required for verification!");
////                return response;
////            }
////
////            if (verificationService.verifyCode(email, code)) {
////                User user = userRepo.findByEmail(email).orElseThrow();
////                user.setVerified(true);
////                userRepo.save(user);
////
////                response.put("success", true);
////                response.put("message", "User verified successfully!");
////            } else {
////                response.put("success", false);
////                response.put("message", "Invalid or expired verification code!");
////            }
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Error verifying code: " + e.getMessage());
////        }
////
////        return response;
////    }
////
////
////
////
////
////    // ✅ DRIVER DETAILS — after submit, send email verification
////@PostMapping(value = "/driver-details", consumes = "multipart/form-data")
////public Map<String, Object> saveDriverDetails(
////        @RequestParam("email") String email,
////        @RequestParam("licenseNumber") String licenseNumber,
////        @RequestParam("vehicleNumber") String vehicleNumber,
////        @RequestParam("vehicleType") String vehicleType,
////        @RequestParam("vehicleColor") String vehicleColor,
////        @RequestPart("vehicleImage") MultipartFile vehicleImage
////) {
////    Map<String, Object> response = new HashMap<>();
////    try {
////        // 🧠 Save details
////        DriverDetails details = new DriverDetails();
////        details.setEmail(email);
////        details.setLicenseNumber(licenseNumber);
////        details.setVehicleNumber(vehicleNumber);
////        details.setVehicleType(vehicleType);
////        details.setVehicleColor(vehicleColor);
////        details.setVehicleImage(vehicleImage.getBytes());
////
////        driverRepo.save(details);
////
////        // ✅ Generate verification code & send email
////        String code = verificationService.generateCode(email);
////        emailService.sendVerificationEmail(email, code);
////
////        response.put("success", true);
////        response.put("message", "Driver details saved successfully! Verification code sent to " + email);
////    } catch (Exception e) {
////        response.put("success", false);
////        response.put("message", "Failed to save driver details: " + e.getMessage());
////    }
////    return response;
////}
////
////
////    // ✅ RESEND CODE
////    @PostMapping("/resend-code")
////    public Map<String, Object> resendCode(@RequestBody Map<String, String> body) {
////        Map<String, Object> response = new HashMap<>();
////        String email = body.get("email");
////
////        try {
////            User user = userRepo.findByEmail(email).orElse(null);
////            if (user == null) {
////                response.put("success", false);
////                response.put("message", "No user found with that email.");
////                return response;
////            }
////
////            String newCode = verificationService.generateCode(email);
////            emailService.sendVerificationEmail(email, newCode);
////
////            response.put("success", true);
////            response.put("message", "New verification code sent to " + email);
////        } catch (Exception e) {
////            response.put("success", false);
////            response.put("message", "Error resending code: " + e.getMessage());
////        }
////
////        return response;
////    }
////
////}

package com.example.verification.controller;

import com.example.verification.model.DriverDetails;
import com.example.verification.model.User;
import com.example.verification.repository.DriverDetailsRepository;
import com.example.verification.repository.UserRepository;
import com.example.verification.service.EmailService;
import com.example.verification.service.VerificationService;

import jakarta.persistence.Lob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class VerificationController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DriverDetailsRepository driverRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VerificationService verificationService;

    @Autowired
    private PasswordEncoder passwordEncoder; // ✅ BCrypt encoder

    @Lob
    private byte[] vehicleImage;

    // ✅ RESET PASSWORD AND SEND NEW VERIFICATION CODE
    @PostMapping("/reset-password")
    public Map<String, Object> resetPassword(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");
        String newPassword = body.get("newPassword");

        try {
            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                response.put("success", false);
                response.put("message", "No user found with that email!");
                return response;
            }

            // 🔑 Hash the new password
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setVerified(false);
            userRepo.save(user);

            // ✅ Generate and send new verification code
            String code = verificationService.generateCode(email);
            emailService.sendVerificationEmail(email, code);

            response.put("success", true);
            response.put("message", "Password updated successfully! Verification code sent to your email.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error resetting password: " + e.getMessage());
        }

        return response;
    }

    // ✅ USER REGISTRATION — send verification code to email
    @PostMapping("/register")
    public Map<String, Object> registerUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required!");
                return response;
            }

            // 🔑 Hash the password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);

            // ✅ Only send verification if the role is NOT DRIVER
            if (!"driver".equalsIgnoreCase(user.getRole())) {
                String code = verificationService.generateCode(user.getEmail());
                emailService.sendVerificationEmail(user.getEmail(), code);
                response.put("message", "Verification code sent to " + user.getEmail());
            } else {
                response.put("message", "Driver registered successfully. Please complete your vehicle details next.");
            }
            response.put("success", true);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Registration failed: " + e.getMessage());
        }
        return response;
    }

    // ✅ USER LOGIN
    @PostMapping("/login")
    public Map<String, Object> loginUser(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");
        String password = body.get("password");
        String role = body.get("role");

        try {
            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                response.put("success", false);
                response.put("message", "No user found with this email!");
                return response;
            }

            // 🔑 Use BCrypt to check password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                response.put("success", false);
                response.put("message", "Invalid password!");
                return response;
            }

            if (!user.getRole().equalsIgnoreCase(role)) {
                response.put("success", false);
                response.put("message", "Role mismatch!");
                return response;
            }

            if (!user.isVerified()) {
                response.put("success", false);
                response.put("message", "Account not verified! Please verify your email.");
                return response;
            }

            response.put("success", true);
            response.put("message", "Login successful!");
            response.put("user", user.getFullName());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error during login: " + e.getMessage());
        }

        return response;
    }

    // ✅ VERIFY CODE
    @PostMapping("/verify")
    public Map<String, Object> verifyCode(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");
        String code = body.get("code");

        try {
            if (email == null || email.isEmpty()) {
                response.put("success", false);
                response.put("message", "Email is required for verification!");
                return response;
            }

            if (verificationService.verifyCode(email, code)) {
                User user = userRepo.findByEmail(email).orElseThrow();
                user.setVerified(true);
                userRepo.save(user);

                response.put("success", true);
                response.put("message", "User verified successfully!");
            } else {
                response.put("success", false);
                response.put("message", "Invalid or expired verification code!");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error verifying code: " + e.getMessage());
        }

        return response;
    }

    // ✅ DRIVER DETAILS — after submit, send email verification
    @PostMapping(value = "/driver-details", consumes = "multipart/form-data")
    public Map<String, Object> saveDriverDetails(
            @RequestParam("email") String email,
            @RequestParam("licenseNumber") String licenseNumber,
            @RequestParam("vehicleNumber") String vehicleNumber,
            @RequestParam("vehicleType") String vehicleType,
            @RequestParam("vehicleColor") String vehicleColor,
            @RequestPart("vehicleImage") MultipartFile vehicleImage
    ) {
        Map<String, Object> response = new HashMap<>();
        try {
            DriverDetails details = new DriverDetails();
            details.setEmail(email);
            details.setLicenseNumber(licenseNumber);
            details.setVehicleNumber(vehicleNumber);
            details.setVehicleType(vehicleType);
            details.setVehicleColor(vehicleColor);
            details.setVehicleImage(vehicleImage.getBytes());

            driverRepo.save(details);

            String code = verificationService.generateCode(email);
            emailService.sendVerificationEmail(email, code);

            response.put("success", true);
            response.put("message", "Driver details saved successfully! Verification code sent to " + email);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Failed to save driver details: " + e.getMessage());
        }
        return response;
    }

    // ✅ RESEND CODE
    @PostMapping("/resend-code")
    public Map<String, Object> resendCode(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        String email = body.get("email");

        try {
            User user = userRepo.findByEmail(email).orElse(null);
            if (user == null) {
                response.put("success", false);
                response.put("message", "No user found with that email.");
                return response;
            }

            String newCode = verificationService.generateCode(email);
            emailService.sendVerificationEmail(email, newCode);

            response.put("success", true);
            response.put("message", "New verification code sent to " + email);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error resending code: " + e.getMessage());
        }

        return response;
    }
}
