package com.insomniacScribber.JournalApp.Controllers;

import com.insomniacScribber.JournalApp.Entity.User;
import com.insomniacScribber.JournalApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserService userService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();
        return ResponseEntity.ok().header("message", "Success").body(users);
    }

    @PostMapping( "/createUser")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User user1 = userService.createUser(user);
        return ResponseEntity.ok().header("message", "User " +user1.getUsername()+ "Created !").body(user1);
    }
    @GetMapping("/getByUsername")
    public ResponseEntity<User> getByUsername(@RequestParam String username){
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok().header("message", "Success").body(user);
    }

    @PostMapping("/updateUser/{username}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String username){
        User user1 = userService.updateUser(user, username);
        return ResponseEntity.ok().header("message", "Successfully Updated User!!").body(user1);
    }
}