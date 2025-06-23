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
    public String addPassword(@RequestParam String username, @RequestBody AddPasswordRequest request) {
        try {
            passwordService.addPassword(username, request.getService(), request.getUsername(), request.getPassword());
            return "Password added successfully!";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/get")
    public String getPassword(@RequestParam String username, @RequestParam String service) {
        try {
            return passwordService.getPasswordForService(username, service);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
     @GetMapping("/all")
    public Object getAllPasswords(@RequestParam String username) {
        try {
            return passwordService.getAllPasswords(username);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    @GetMapping("/get-original")
    public String getOriginalPassword(@RequestParam String username, @RequestParam String service, @RequestParam String user) {
        try {
            return passwordService.getOriginalPasswordForServiceAndUser(username, service, user);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
    @PostMapping("/set-storage-file")
    public String setStorageFile(@RequestParam String username, @RequestParam String path) {
        try {
            passwordService.setStorageFile(username, path);
            return "Storage file location set to: " + path;
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @PostMapping("/load-passwords")
    public Object loadPasswords(@RequestParam String username, @RequestParam String path) {
        try {
            passwordService.setStorageFile(username, path);
            return passwordService.getAllPasswords(username);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}