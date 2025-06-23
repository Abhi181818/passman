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
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

@Service
public class PasswordService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, File> userFiles = new ConcurrentHashMap<>();

    public void setStorageFile(String username, String path) {
        if (!path.endsWith(".json")) {
            throw new IllegalArgumentException("Storage file must be a .json file");
        }
        userFiles.put(username, new File(path));
    }

    private File getUserFile(String username) {
        return userFiles.getOrDefault(username, new File("passwords_" + username + ".json"));
    }

    public List<PasswordEntry> getAllPasswords(String username) throws Exception {
        File file = getUserFile(username);
        if (!file.exists()) return new ArrayList<>();
        return mapper.readValue(file, new TypeReference<>() {});
    }

    public void addPassword(String username, String service, String user, String password) throws Exception {
        List<PasswordEntry> list = getAllPasswords(username);
        PasswordEntry entry = new PasswordEntry();
        entry.setId(UUID.randomUUID().toString());
        entry.setServiceName(service);
        entry.setUsername(user);
        entry.setEncryptedPassword(EncryptUtil.encrypt(password));
        list.add(entry);
        mapper.writeValue(getUserFile(username), list);
    }

    public String getPasswordForService(String username, String service) throws Exception {
        List<PasswordEntry> list = getAllPasswords(username);
        return list.stream()
                .filter(p -> p.getServiceName().equalsIgnoreCase(service))
                .findFirst()
                .map(PasswordEntry::getEncryptedPassword)
                .orElse("Service not found");
    }

    public String getOriginalPasswordForServiceAndUser(String username, String service, String user) throws Exception {
        List<PasswordEntry> list = getAllPasswords(username);
        return list.stream()
                .filter(p -> p.getServiceName().equalsIgnoreCase(service) && p.getUsername().equalsIgnoreCase(user))
                .findFirst()
                .map(p -> {
                    try {
                        return EncryptUtil.decrypt(p.getEncryptedPassword());
                    } catch (Exception e) {
                        return "Error decrypting";
                    }
                })
                .orElse("Service not found");
    }
}
