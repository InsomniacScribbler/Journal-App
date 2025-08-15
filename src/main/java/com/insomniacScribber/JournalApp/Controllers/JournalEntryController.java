package com.insomniacScribber.JournalApp.Controllers;


import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Exceptions.APIException;
import com.insomniacScribber.JournalApp.Repository.JournalEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    JournalEntryRepository journalEntryRepository;


    private Map<Long, JournalEntry> journalEntryMap = new HashMap<>();


    @GetMapping("/getAllEntries")
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryRepository.findAll();
    }


    @PostMapping("/createEntry")
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry) {
        if(journalEntry.getTitle() == null || journalEntry.getTitle().isBlank()) {
            throw new APIException("Title cannot be empty");
        }

        return journalEntryRepository.save(journalEntry);

    }
    @GetMapping("/getEntryById/{id}")
    public JournalEntry getJournalEntryById(@PathVariable Long id) {
        if(!journalEntryMap.containsKey(id)) {
            throw new APIException("Entry with id " + id + " not found");
        }
        return journalEntryMap.get(id);
    }

    @DeleteMapping("/deleteEntryById/{id}")
    public void deleteJournalEntryById(@PathVariable Long id) {
        if(!journalEntryMap.containsKey(id)) {
            throw new APIException("Entry with id " + id + " not found");
        }
        journalEntryRepository.deleteById(id);
    }

    @PutMapping("/updateEntryById/{id}")
    public JournalEntry  updateJournalEntryById(@RequestBody JournalEntry journalEntry, @PathVariable Long id) {
        if(!journalEntryMap.containsKey(id)) {
            throw new APIException("Entry with id " + id + " not found");
        }
        journalEntry.setId(id);
        return journalEntryRepository.save(journalEntry);
    }



}
