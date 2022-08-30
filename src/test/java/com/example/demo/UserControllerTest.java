package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldAddUser(){
        //Given
        User user1 = new User(1,"Michał");

        //When
        ResponseEntity<User> result = restTemplate.postForEntity("http://localhost:" + port + "/users/addUser", user1, User.class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), user1);

    }

    @Test
    public void shouldUpdateUser() {
        //Given
        String url = "http://localhost:" + port + "/users/updateUser";
        User user1 = new User(1, "Bartek");
        restTemplate.postForEntity("http://localhost:" + port + "/users/users", user1, User.class);
        User updateUser = new User (user1.getId(), "Franek");

        //Naglowek - potrzebny do exchange
        HttpEntity<User> requestUpdate = new HttpEntity<>(updateUser);


        //When

        ResponseEntity<User> result = restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, User.class, updateUser);

        //bo zwracane jest nic - void
//        restTemplate.put("http://localhost:" + port + "users/updateUser", updateUser, User.class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), updateUser);

    }

    @Test
    public void shouldGetAllUsers(){
        //Given
        try {
        String url = "http://loclahost:" + port + "/users/getAllUsers";
        User user1 = new User(1, "Michał");
        User user2 = new User(2, "Tomek");
        User user3 = new User(3, "Kasia");
        User[] users = {user1, user2, user3};
        restTemplate.postForEntity(url, users, User.class);
//        restTemplate.postForEntity(url, user1, User.class);
//        restTemplate.postForEntity(url, user2, User.class);
//        restTemplate.postForEntity(url, user3, User.class);

        //When
        ResponseEntity<User[]> result = restTemplate.getForEntity(url, User[].class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), users);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void shouldGetUser(){
            //Given
        try {
            String url = "http://loclahost:" + port + "/users/getUser";
            User user1 = new User(1, "Michał");
            restTemplate.postForEntity("http://localhost:" + port + "/users", user1, User.class);

            HttpEntity<User> request = new HttpEntity<>(user1);


            //When
            ResponseEntity<User> result = restTemplate.exchange(url, HttpMethod.GET, request, User.class, user1, 1);
//        ResponseEntity<User> result = restTemplate.getForEntity(url, User.class,1);


            //Then
            Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
            Assertions.assertEquals(result.getBody(), user1);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void shouldDeleteUser(){
        //Given
        try {
            String url = "http://loclahost:";
            String metoda = "/users/deleteUser";
            User user1 = new User(1, "Paulina");

            HttpEntity<User> requestDelete = new HttpEntity<>(null);

            //When
            ResponseEntity<User> result = restTemplate.exchange(url + port + metoda, HttpMethod.DELETE, requestDelete, User.class);

//        restTemplate.delete(url + port + metoda, user1);

            //Then
            Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
            Assertions.assertNull(result.getBody());
        }catch (Exception e){
            System.out.println(e);
        }

    }

}