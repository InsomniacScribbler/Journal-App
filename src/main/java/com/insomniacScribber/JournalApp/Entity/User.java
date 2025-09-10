package com.insomniacScribber.JournalApp.Entity;


import jakarta.annotation.Nonnull;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Username cannot be Blank!!")
    private String username;
    @Nonnull()
    private String password;

}
