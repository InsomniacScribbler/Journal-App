package com.insomniacScribber.JournalApp.Repository;

import com.insomniacScribber.JournalApp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
