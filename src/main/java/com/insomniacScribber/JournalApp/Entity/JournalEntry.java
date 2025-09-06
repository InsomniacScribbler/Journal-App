package com.insomniacScribber.JournalApp.Entity;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "journal_entries") // Specify the MongoDB collection
public class JournalEntry {
    @Id
    private String id;      // Use String for MongoDB ObjectId
    @NotBlank(message = "Title can't be Blank!")
    private String title;
    @NotBlank(message = "There needs to be content in the journal Entry!")
    private String content;
    private LocalDateTime date;

    //Your JournalEntry entity doesn't automatically set the creation date.
    @CreatedDate
    public void createDate() {
        if (this.date == null) {
            this.date = LocalDateTime.now();
        }
    }

}
