package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;

import java.util.List;

public interface JournalEntryService {

    List<JournalEntry> getAllJournalEntries();

    JournalEntry createJournalEntry(JournalEntry journalEntry, String username);

    JournalEntry getJournalEntryById(String id);  // Changed from Long to String

    String deleteJournalEntryById(String id, String username);       // Changed from Long to String

    JournalEntry updateJournalEntryById(String id, JournalEntry journalEntry);  // Changed from Long to String
    List<JournalEntry> findJournalEntriesByKeyword(String keyword);
}
