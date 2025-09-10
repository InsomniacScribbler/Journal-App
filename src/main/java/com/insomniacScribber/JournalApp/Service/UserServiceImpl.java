package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.User;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {
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


}
