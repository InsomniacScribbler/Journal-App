package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Entity.User;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.JournalEntryRepository;
import com.insomniacScribber.JournalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public List<JournalEntry> getAllJournalEntries() {
        try {
            return journalEntryRepository.findAll();
        } catch (Exception e) {
            throw new APIException("Failed to retrieve journal entries: " + e.getMessage());
        }
    }

    @Override
    public JournalEntry createJournalEntry(JournalEntry journalEntry, String username) {
        // Validation
        if (journalEntry == null) {
            throw new APIException("Journal entry cannot be null");
        }

        if (journalEntry.getTitle() == null || journalEntry.getTitle().isBlank()) {
            throw new APIException("Title cannot be empty");
        }
        if (username == null || username.isBlank()) {
            throw new APIException("Username cannot be null or empty");
        }

        try {
            User user = userRepository.findByUsername(username);
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            String id = savedEntry.getId();
            user.getJournalEntryList().add(savedEntry);
            userService.updateUser(user, user.getUsername());
            return savedEntry;
        } catch (Exception e) {
            throw new APIException("Failed to create journal entry: " + e.getMessage());
        }
    }

    @Override
    public JournalEntry getJournalEntryById(String id) {
        if (id == null || id.isBlank()) {
            throw new APIException("Journal entry ID cannot be null or empty");
        }

        try {
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }
            return journalEntry.get();
        } catch (APIException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new APIException("Failed to retrieve journal entry: " + e.getMessage());
        }
    }

    @Override
    public String deleteJournalEntryById(String id, String username) {
        if (id == null || id.isBlank()) {
            throw new APIException("Journal entry ID cannot be null or empty");
        }

        try {
            User user = userRepository.findByUsername(username);
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }
            String tobeDeleted = journalEntry.get().getTitle();
            journalEntryRepository.deleteById(id);
            user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
            return "Journal entry " + tobeDeleted + " deleted";
        } catch (APIException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new APIException("Failed to delete journal entry: " + e.getMessage());
        }
    }

    @Override
    public JournalEntry updateJournalEntryById(String id, JournalEntry journalEntry) {
        if (id == null || id.isBlank()) {
            throw new APIException("Journal entry ID cannot be null or empty");
        }

        if (journalEntry == null) {
            throw new APIException("Journal entry cannot be null");
        }

        if (journalEntry.getTitle() == null || journalEntry.getTitle().isBlank()) {
            throw new APIException("Title cannot be empty");
        }

        if (journalEntry.getContent() == null || journalEntry.getContent().isBlank()) {
            throw new APIException("Content cannot be empty");
        }

        try {
            Optional<JournalEntry> existingEntry = journalEntryRepository.findById(id);
            if (existingEntry.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }

            journalEntry.setId(id);
            return journalEntryRepository.save(journalEntry);
        } catch (APIException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new APIException("Failed to update journal entry: " + e.getMessage());
        }
    }
    @Override
    public List<JournalEntry> findJournalEntriesByKeyword(String keyword) {
        // Input validation
        if (keyword == null || keyword.isBlank()) {
            throw new APIException("Search keyword cannot be null or empty");
        }

        try {
            List<JournalEntry> entries = journalEntryRepository.findByTitleContaining(keyword);

            // Optional: You can add additional logic here
            if (entries.isEmpty()) {
                // You can either return empty list or throw exception
                // For search operations, empty list is usually preferred
                return entries;
            }

            return entries;

        } catch (APIException e) {
            throw e;
        } catch (Exception e) {
            throw new APIException("Failed to search journal entries: " + e.getMessage());
        }
    }
}

