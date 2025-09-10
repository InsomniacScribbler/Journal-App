package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User createUser(User user);
    User getUserById(String id);  // Changed from Long to String

    String deleteUseryByUsername(String id);
}
