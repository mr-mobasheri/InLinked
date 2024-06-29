package controllers;

import java.util.ArrayList;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import utils.UserNotFoundException;

public class UserController extends Controller {

    public UserController() {
    }

    public String createUser(String username, String password, String firstName, String lastName, String email,
                             String phoneNumber, String country, long birthday) {
        User user = new User(username, password, firstName, lastName, email, phoneNumber, country, birthday);
        dao.insert(user);
        return "User created successfully";
    }

    public String deleteUser(String username) throws UserNotFoundException {
        User user = dao.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        dao.delete(user);
        return "User deleted successfully";
    }

    public void updateUser(String username, String password, String firstName, String lastName, String email,
                           String phoneNumber, String country, long birthday) {
        User user = dao.getUserByUsername(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(email);
        user.setPhoneNumber(phoneNumber);
        user.setCountry(country);
        user.setBirthday(birthday);
        dao.update(user);
    }

    public void deleteAllUsers() {
        dao.deleteAllUsers();
    }

    public void deleteAllMessages() {
        dao.deleteAllMessages();
    }

    public boolean isUserExists(String username) {
        return dao.isUserExists(username);
    }

    public String getUsers() {
        ArrayList<User> users = dao.getAllUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getUser(String username) {
        User user = dao.getUserByUsername(username);
        if (user == null)
            return "user not found!";
        ObjectMapper objectMapper = new ObjectMapper();
        String response = null;
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String register(String email, String username, String firstName, String lastName, String password) {
        if (email == null || username == null || firstName == null || lastName == null || password == null) {
            return "400";
        }

        if (dao.isUserExists(email)) {
            return "409";
        }

        if(dao.isUsernameExists(username)) {
            return "406";
        }

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setDateOfCreat(System.currentTimeMillis());
        dao.insert(user);
        return login(email, password, Algorithm.HMAC256("my-secret-key"));
    }

    public String login(String email, String password, Algorithm algorithm) {
        if (email == null || password == null) {
            return "400";
        }
        User user = dao.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(email)
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .sign(algorithm);
            return token;
        } else {
            return "401";
        }
    }
}