package com.insomniacScribber.JournalApp.Repository;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalEntryRepository extends MongoRepository<JournalEntry, String> {

    // Custom query methods (if needed)
    List<JournalEntry> findByTitleContaining(String keyword);

    List<JournalEntry> findByUsernameAndTitleContainingIgnoreCase(String username, String keyword);
    List<JournalEntry> findByUsernameAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
            String username, String titleKeyword, String contentKeyword);
    // List<JournalEntry> findByTitle(String title);
    // List<JournalEntry> findByTitleContaining(String keyword);
}
