package com.insomniacScribber.JournalApp.Repository;

import com.insomniacScribber.JournalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByUsername(String username);
}
