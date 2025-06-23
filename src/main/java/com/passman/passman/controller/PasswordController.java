package com.passman.passman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.passman.passman.dto.AddPasswordRequest;
import com.passman.passman.service.PasswordService;

@RestController
@RequestMapping("/api/passwords")
@CrossOrigin(origins = "*") 
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/add")
    public String addPassword(@RequestBody AddPasswordRequest request) {
        try {
            passwordService.addPassword(request.getService(), request.getUsername(), request.getPassword());
            return "Password added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public String getPassword(@RequestParam String service) {
        try {
            return passwordService.getPasswordForService(service);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
     @GetMapping("/all")
    public Object getAllPasswords() {
        try {
            return passwordService.getAllPasswords();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    @GetMapping("/get-original")
    public String getOriginalPassword(@RequestParam String service, @RequestParam String username) {
        try {
            return passwordService.getOriginalPasswordForServiceAndUser(service, username);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}