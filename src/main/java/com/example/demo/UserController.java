package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    Map<Integer, User> idToUser = new HashMap<>();

    @PostMapping("/users") //to do poprawne sciezki do endpoins "/users" we wszystkich endpoints
    public User addUser(@RequestBody User user) {
        idToUser.put(user.id, user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        idToUser.replace(user.id, user);
        return user;
    }

    @GetMapping("/users")
    public Collection<User> getAllUsers() {
        return idToUser.values();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return idToUser.get(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        idToUser.remove(id);
    }
}
