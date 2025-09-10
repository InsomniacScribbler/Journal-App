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


}
