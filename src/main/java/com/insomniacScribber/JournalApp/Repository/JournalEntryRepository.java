package com.insomniacScribber.JournalApp.Repository;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {

    // Custom query methods (if needed)
    // List<JournalEntry> findByTitle(String title);
    // List<JournalEntry> findByTitleContaining(String keyword);
}
