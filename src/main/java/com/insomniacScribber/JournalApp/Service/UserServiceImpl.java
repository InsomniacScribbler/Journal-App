package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Entity.User;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            throw new APIException("Failed to retrieve journal entries: " + e.getMessage());
        }
    }
    @Override
    public User createUser(User user) {
        if (user == null) {
            throw new APIException("User cannot be null");
        }

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new APIException("Username cannot be empty");
        }

        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new APIException("Failed to create User: " + e.getMessage());
        }
    }

    @Override
    public User getUserByUsername(String username) throws APIException {
        if (username== null || username.isBlank()) {
            throw new APIException("Username cannot be null or empty");
        }

        try {
            Optional<User> users = userRepository.findByUsername(username);
            if (users.isEmpty()) {
                throw new APIException("Entry with username " + username + " not found");
            }
            return users.get();
        } catch (APIException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new APIException("Failed to retrieve user " + e.getMessage());
        }
    }

    @Override
    public User updateUser(User user, String username) {
        if (user == null || username == null || username.isBlank()) {
            throw new APIException("User cannot be null");
        }
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new APIException("Updated Username cannot be empty");
        }
        try {
            User toBeUpdated = getUserByUsername(username);
            if (toBeUpdated == null) {
                throw new APIException("Entry with username " + username + " not found");
            }
            toBeUpdated.setUsername(user.getUsername());
            toBeUpdated.setPassword(user.getPassword());
            toBeUpdated.setId(user.getId());

            return userRepository.save(toBeUpdated);
        } catch (Exception e) {
            throw new APIException("Failed to update User: " + e.getMessage());
        }
    }

}
