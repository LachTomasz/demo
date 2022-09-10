package com.example.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    Map<Integer, User> idToUser = new HashMap<>();

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {
        idToUser.put(user.id, user);
        return user;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        idToUser.replace(user.id, user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        idToUser.remove(id);
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") Integer id) {
        return idToUser.get(id);
    }

    @GetMapping("/users")
    public Collection<User> getAllUser(@RequestParam(value = "name", required = false) String name) {

        /*  To jest przykład na pętli    */
//        if(name!=null) {
//            List<User> namesOfUser = new LinkedList<>();
//            for (User user : idToUser.values()) {if (user.getName().equals(name)) namesOfUser.add(user);}
//            return namesOfUser;}
//        else
//            return idToUser.values();

/*  to jest przykład "jawnego"/"nie anonimowej" funkcji LAMBDA
        w tym przypadku stworzylem odpowiednia klase gdzie implementowałem interfejs "PREDICATE"
        patrz do def. klasy UserNamePredicate   */
//        UserNamePredicate testUserNAmePredicate = new UserNamePredicate(name);
//        if(name != null)        return idToUser.values().stream()
//                .filter(testUserNAmePredicate)
//                .collect(Collectors.toList());
//        else return idToUser.values();

        /*  to jest przykład z anonimową funkcją - LAMBDA   */
        if (name != null) return idToUser.values().stream()
                .filter((user) -> user.getName().equals(name))
                .collect(Collectors.toList());
        else return idToUser.values();
    }
}
