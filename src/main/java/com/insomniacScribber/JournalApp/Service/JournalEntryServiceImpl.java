package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Entity.User;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.JournalEntryRepository;
import com.insomniacScribber.JournalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Transactional
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

    @Transactional
    @Override
    public String deleteJournalEntry(String id, String username) {
        if (id == null || id.isBlank()) {
            throw new APIException("Journal entry ID cannot be null or empty");
        }
        if (username == null || username.isBlank()) {
            throw new APIException("Username cannot be null or empty");
        }

        try {
            User user = userRepository.findByUsername(username);
            Optional<JournalEntry> journalEntry = journalEntryRepository.findById(id);
            if (journalEntry.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }
            if (user == null) {
                throw new APIException("User with username " + username + " not found");
            }
            String tobeDeleted = journalEntry.get().getTitle();
            journalEntryRepository.deleteById(id);
            user.getJournalEntryList().removeIf(x -> x.getId().equals(id));
            userService.updateUser(user, user.getUsername());
            return "Journal entry " + tobeDeleted + " deleted";
        } catch (APIException e) {
            throw e; // Re-throw our custom exception
        } catch (Exception e) {
            throw new APIException("Failed to delete journal entry: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public JournalEntry updateJournalEntry(String id, JournalEntry journalEntry, String username) {
        // Input validation (same as yours)
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
        if (username == null || username.isBlank()) {
            throw new APIException("Username cannot be null or empty");
        }

        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new APIException("User with username " + username + " not found");
            }

            Optional<JournalEntry> existingEntryOpt = journalEntryRepository.findById(id);
            if (existingEntryOpt.isEmpty()) {
                throw new APIException("Entry with id " + id + " not found");
            }

            JournalEntry existingEntry = existingEntryOpt.get();
            boolean isOwner = user.getJournalEntryList().stream()
                    .anyMatch(userEntry -> userEntry.getId().equals(id));

            if (!isOwner) {
                throw new APIException("You don't have permission to update this entry");
            }

            // ✅ FIX: Actually update the fields!
            existingEntry.setTitle(journalEntry.getTitle());
            existingEntry.setContent(journalEntry.getContent());
            // lastModified will be auto-updated by @LastModifiedDate

            JournalEntry updatedEntry = journalEntryRepository.save(existingEntry);

            // Update the entry in user's list as well
            user.getJournalEntryList().stream()
                    .filter(entry -> entry.getId().equals(id))
                    .findFirst()
                    .ifPresent(entry -> {
                        entry.setTitle(updatedEntry.getTitle());
                        entry.setContent(updatedEntry.getContent());
                        entry.setLastModified(updatedEntry.getLastModified()); // ✅ FIX: Use lastModified, not updatedDate
                    });

            userRepository.save(user);
            return updatedEntry;

        } catch (APIException e) {
            throw e;
        } catch (Exception e) {
            throw new APIException("Failed to update journal entry: " + e.getMessage());
        }
    }

    @Override
    public List<JournalEntry> findJournalEntriesByKeyword(String keyword, String username) {
        if (keyword == null || keyword.isBlank()) {
            throw new APIException("Search keyword cannot be null or empty");
        }
        if (username == null || username.isBlank()) {
            throw new APIException("Username cannot be null or empty");
        }

        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                throw new APIException("User with username " + username + " not found");
            }

            // Filter user's entries by keyword (in-memory)
            List<JournalEntry> entries = user.getJournalEntryList().stream()
                    .filter(entry -> {
                        String title = entry.getTitle() != null ? entry.getTitle().toLowerCase() : "";
                        String content = entry.getContent() != null ? entry.getContent().toLowerCase() : "";
                        String searchKeyword = keyword.toLowerCase();

                        return title.contains(searchKeyword) || content.contains(searchKeyword);
                    })
                    .collect(Collectors.toList());

            return entries;
        } catch (APIException e) {
            throw e;
        } catch (Exception e) {
            throw new APIException("Failed to search journal entries: " + e.getMessage());
        }
    }

}

