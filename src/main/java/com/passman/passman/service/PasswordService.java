package com.passman.passman.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.passman.passman.model.PasswordEntry;
import com.passman.passman.util.EncryptUtil;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PasswordService {
    private final File storageFile = new File("passwords.json");
    private final ObjectMapper mapper = new ObjectMapper();

    public List<PasswordEntry> getAllPasswords() throws Exception {
        if (!storageFile.exists()) return new ArrayList<>();
        return mapper.readValue(storageFile, new TypeReference<>() {});
    }

    public void addPassword(String service, String username, String password) throws Exception {
        List<PasswordEntry> list = getAllPasswords();
        PasswordEntry entry = new PasswordEntry();
        entry.setId(UUID.randomUUID().toString());
        entry.setServiceName(service);
        entry.setUsername(username);
        entry.setEncryptedPassword(EncryptUtil.encrypt(password));
        list.add(entry);
        mapper.writeValue(storageFile, list);
    }

    public String getPasswordForService(String service) throws Exception {
        List<PasswordEntry> list = getAllPasswords();
        return list.stream()
                .filter(p -> p.getServiceName().equalsIgnoreCase(service))
                .findFirst()
                .map(PasswordEntry::getEncryptedPassword)
                .orElse("Service not found");
    }
}
