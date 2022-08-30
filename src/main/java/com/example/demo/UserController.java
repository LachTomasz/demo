package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    Map<Integer, User> idToUser = new HashMap<>();


    @PostMapping("/users")
    public User addName( @RequestBody User user) {
        idToUser.put(user.id, user);
        return user;
    }

    @PutMapping("users/updateUser")
    public User updateUser(@RequestBody User user){
        idToUser.replace(user.id, user);
        return user;
    }


    @GetMapping("/users/getAllUsers")
    public Collection<User> getAllUsers() {
        return idToUser.values();
    }

    @GetMapping("/users/getUser")
    public User getUser(@PathParam("id") Integer id) {
        return idToUser.get(id);
    }

    @DeleteMapping("users/deleteUser")
    public void deleteUser(@PathParam("id") Integer id) {
        idToUser.remove(id);
    }



}
