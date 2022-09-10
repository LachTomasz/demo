package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @GetMapping("/users/byName")
    public List<User> getAllUserParam(@RequestParam("name") String name) {
        List<User> namesOfUser = new LinkedList<>();
        for (User user : idToUser.values()) {
            if (user.getName().equals(name)) namesOfUser.add(user);
        }
        return namesOfUser;
    }
}
