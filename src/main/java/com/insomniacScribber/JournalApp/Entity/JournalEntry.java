package com.insomniacScribber.JournalApp.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "journal_entries")
public class JournalEntry {
    @Id
    private String id;
    @NotBlank(message = "Title can't be Blank!")
    private String title;
    @NotBlank(message = "There needs to be content in the journal Entry!")
    private String content;
    @CreatedDate
    private LocalDateTime date;
    @LastModifiedDate
    private LocalDateTime lastModified;
}
