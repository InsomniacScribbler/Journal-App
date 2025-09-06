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
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable String id) {
        JournalEntry journalEntry = journalEntryService.getJournalEntryById(id);
        return ResponseEntity.ok().header("Message", "Journal Entry").body(journalEntry);
    }

    @DeleteMapping("/deleteEntryById/{id}")
    public ResponseEntity<String> deleteJournalEntryById(@PathVariable String id) {
        String msg = journalEntryService.deleteJournalEntryById(id);
        return ResponseEntity.ok().body(msg);
    }

    @PutMapping("/updateEntryById/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@Valid @RequestBody JournalEntry journalEntry, @PathVariable String id) {
        JournalEntry  updatedJournalEntry = journalEntryService.updateJournalEntryById(id, journalEntry);
        return ResponseEntity.ok().header("Message", "Journal Entry Updated").body(updatedJournalEntry);
    }
    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchJournalEntries(@RequestParam String keyword) {
        List<JournalEntry> entries = journalEntryService.findJournalEntriesByKeyword(keyword);
        return ResponseEntity.ok().header("Message", "Journal Entry List with "+keyword+".").body(entries);
    }
}
