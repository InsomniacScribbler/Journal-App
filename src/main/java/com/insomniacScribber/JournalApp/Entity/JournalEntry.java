package com.insomniacScribber.JournalApp.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "journal_entries") // Specify the MongoDB collection
public class JournalEntry {
    @Id
    private ObjectId id;      // Use String for MongoDB ObjectId
    private String title;
    private String content;
    private Date date;
}
