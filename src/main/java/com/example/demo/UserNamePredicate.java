package com.example.demo;

import java.util.function.Predicate;

public class UserNamePredicate implements Predicate<User> {

    String name;

    public UserNamePredicate(String name) {
        this.name = name;
    }

    @Override
    public boolean test(User user) {
        return user.getName().equals(name);
    }
}
