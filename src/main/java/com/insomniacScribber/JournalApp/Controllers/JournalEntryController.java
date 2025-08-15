package com.insomniacScribber.JournalApp.Controllers;


import com.insomniacScribber.JournalApp.Entity.JournalEntry;
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


    @PostMapping("createEntry")
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }



}
