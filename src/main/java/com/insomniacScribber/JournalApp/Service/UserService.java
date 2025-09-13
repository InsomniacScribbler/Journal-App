package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User updateUser(User user, String username);
    User createUser(User user);
    User getUserByUsername(String username);  // Changed from Long to String


}
