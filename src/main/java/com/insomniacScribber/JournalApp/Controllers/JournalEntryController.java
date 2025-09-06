package com.insomniacScribber.JournalApp.Controllers;

import com.insomniacScribber.JournalApp.Entity.JournalEntry;
import com.insomniacScribber.JournalApp.Service.JournalEntryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping("/getAllEntries")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntries() {
        List<JournalEntry> entries = journalEntryService.getAllJournalEntries();
//        return new ResponseEntity<>(entries, HttpStatus.OK);
        return ResponseEntity.ok().header("Message", "Journal Entry List").body(entries);
    }

    @PostMapping("/createEntry")
    public ResponseEntity<JournalEntry> createJournalEntry(@Valid @RequestBody JournalEntry journalEntry) {
        JournalEntry createdJournalEntry = journalEntryService.createJournalEntry(journalEntry);
        return ResponseEntity.status(HttpStatus.CREATED).header("Message", "New Entry Created").body(createdJournalEntry);
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
    public JournalEntry updateJournalEntryById(@Valid @RequestBody JournalEntry journalEntry, @PathVariable String id) {
        return journalEntryService.updateJournalEntryById(id, journalEntry);
    }
    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchJournalEntries(@RequestParam String keyword) {
        List<JournalEntry> entries = journalEntryService.findJournalEntriesByKeyword(keyword);
        return ResponseEntity.ok(entries);
    }
}
