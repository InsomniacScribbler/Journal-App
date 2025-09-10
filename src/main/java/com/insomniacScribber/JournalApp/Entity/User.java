package com.insomniacScribber.JournalApp.Entity;


import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Data
public class User {

    /*
    *userName : Name
    * password : Hashed Password
    * journalEntries : [ Array of Objects of Journal Entries
    * ]
    */
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;

}
