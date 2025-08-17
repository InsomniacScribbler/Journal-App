package com.insomniacScribber.JournalApp.Service;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;

import java.util.List;

public interface JournalEntryService {

    List<JournalEntry> getAllJournalEntries();

    JournalEntry createJournalEntry(JournalEntry journalEntry);

    JournalEntry getJournalEntryById(String id);  // Changed from Long to String

    void deleteJournalEntryById(String id);       // Changed from Long to String

    JournalEntry updateJournalEntryById(String id, JournalEntry journalEntry);  // Changed from Long to String
}
