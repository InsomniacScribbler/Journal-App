package com.insomniacScribber.JournalApp.Controllers;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Service.JournalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/getAllEntries")
    public List<JournalEntry> getAllJournalEntries() {
        return journalEntryService.getAllJournalEntries();
    }

    @PostMapping("/createEntry")
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntry) {
        return journalEntryService.createJournalEntry(journalEntry);
    }

    @GetMapping("/getEntryById/{id}")
    public JournalEntry getJournalEntryById(@PathVariable String id) {
        return journalEntryService.getJournalEntryById(id);
    }

    @DeleteMapping("/deleteEntryById/{id}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String id) {
        String message = journalEntryService.deleteJournalEntryById(id);
        return ResponseEntity.ok().body(message);
    }

    @PutMapping("/updateEntryById/{id}")
    public JournalEntry updateJournalEntryById(@RequestBody JournalEntry journalEntry, @PathVariable String id) {
        return journalEntryService.updateJournalEntryById(id, journalEntry);
    }
}
