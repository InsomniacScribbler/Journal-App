package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryServiceImpl implements JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Override
    public List<JournalEntry> getAllJournalEntries() {
        try {
            return journalEntryRepository.findAll();
        } catch (Exception e) {
            throw new APIException("Failed to retrieve journal entries: " + e.getMessage());
        }
    }

    @Override
    public JournalEntry createJournalEntry(JournalEntry journalEntry) {
        // Validation
        if (journalEntry == null) {
            throw new APIException("Journal entry cannot be null");
        }

        if (journalEntry.getTitle() == null || journalEntry.getTitle().isBlank()) {
            throw new APIException("Title cannot be empty");
        }

        try {
            return journalEntryRepository.save(journalEntry);
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
    public void deleteJournalEntryById(String id) {
        if (id == null || id.isBlank()) {
            throw new APIException("Journal entry ID cannot be null or empty");
        }

        try {
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }
            journalEntryRepository.deleteById(id);
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
}
