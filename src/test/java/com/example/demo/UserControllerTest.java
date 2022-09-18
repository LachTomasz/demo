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
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldAddUser() {
        //Given
        User user1 = new User(1, "Michał");

        //When
        ResponseEntity<User> result = restTemplate.postForEntity("http://localhost:" + port + "/users", user1, User.class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), user1);
    }

    @Test
    public void shouldUpdateUser() {
        //Given
        String url = "http://localhost:" + port + "/users";
        User user1 = new User(1, "Bartek");
        restTemplate.postForEntity(url, user1, User.class);
        User updateUser = new User(user1.getId(), "Franek");

        //When
        ResponseEntity<User> result = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(updateUser), User.class, updateUser);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), updateUser);
    }

    @Test
    public void shouldDeleteUser() {
        //Given
        String urlCreated = "http://localhost:" + port + "/users";
        String urlDelete = "http://localhost:" + port + "/users/1";
        User user1 = new User(1, "Paulina");
        restTemplate.postForEntity(urlCreated, user1, User.class);

        //When
        ResponseEntity<User> result = restTemplate.exchange(urlDelete, HttpMethod.DELETE, new HttpEntity<>(null), User.class);
//        restTemplate.delete(urlDelete, user1);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertNull(result.getBody());
    }

    @Test
    public void shouldGetUser() {
        //Given
        String urlCreate = "http://localhost:" + port + "/users";
        String urlGet = "http://localhost:" + port + "/users/1";
        User user1 = new User(1, "Michał");
        restTemplate.postForEntity(urlCreate, user1, User.class);

        //When
//        ResponseEntity<User> result = restTemplate.exchange(urlGet, HttpMethod.GET, new HttpEntity<>(user1), User.class, user1, 1);
        ResponseEntity<User> result = restTemplate.getForEntity(urlGet, User.class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(result.getBody(), user1);
    }

    @Test
    public void shouldGetAllUsers() {
        //Given
        String url = "http://localhost:" + port + "/users";
        User user1 = new User(1, "Michał");
        User user2 = new User(2, "Tomek");
        User user3 = new User(3, "Kasia");
        restTemplate.postForEntity(url, user1, User.class);
        restTemplate.postForEntity(url, user2, User.class);
        restTemplate.postForEntity(url, user3, User.class);
        User[] users = new User[]{user1, user2, user3};

        //When
        ResponseEntity<User[]> result = restTemplate.getForEntity(url, User[].class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertArrayEquals(result.getBody(), users);
    }

    @Test
    void shouldGetAllUserByName() {
        //Given
        String urlCreated = "http://localhost:" + port + "/users";
        URI uri = UriComponentsBuilder.fromHttpUrl(urlCreated).queryParam("name","Franek").build().toUri();
        User user1 = new User(1, "Bartek");
        User user2 = new User(2, "Franek");
        User user3 = new User(3, "Franek");
        restTemplate.postForEntity(urlCreated, user1, User.class);
        restTemplate.postForEntity(urlCreated, user2, User.class);
        restTemplate.postForEntity(urlCreated, user3, User.class);
        User[] users = new User[]{user2, user3};

        //When
        ResponseEntity<User[]> result = restTemplate.getForEntity(uri,User[].class);

        //Then
        Assertions.assertTrue(result.getStatusCode().is2xxSuccessful());
        Assertions.assertArrayEquals(result.getBody(), users);
    }
}