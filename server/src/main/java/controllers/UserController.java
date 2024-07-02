package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.UserDAO;
import models.*;
import utils.UserNotFoundException;

public class UserController extends Controller {
    UserDAO userDAO = new UserDAO();

    public UserController() {
    }

    public String createUser(String username, String password, String firstName, String lastName, String email,
                             String phoneNumber, String country, long birthday) {
        User user = new User(username, password, firstName, lastName, email, phoneNumber, country, birthday);
        dao.insert(user);
        return "User created successfully";
    }

    public String deleteUser(String username) throws UserNotFoundException {
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        dao.delete(user);
        return "User deleted successfully";
    }

    public void updateUser(String username, String password, String firstName, String lastName, String email,
                           String phoneNumber, String country, long birthday) {
        User user = userDAO.getUserByUsername(username);
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
        userDAO.deleteAllUsers();
    }

    public boolean isUserExists(String username) {
        return userDAO.isUserExists(username);
    }

    public String getUsers() {
        ArrayList<User> users = userDAO.getAllUsers();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String getUser(String username) {
        User user = userDAO.getUserByUsername(username);
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
        if (email == null || username == null || firstName == null || lastName == null
                || password == null || !isValidEmailAddress(email) || !isValidPass(password)) {
            return "400";
        }

        for (char c : username.toCharArray()) {
            if (!Character.isAlphabetic(c)) {
                return "400";
            }
        }

        if (userDAO.isUserExists(email)) {
            return "409";
        }

        if (userDAO.isUsernameExists(username)) {
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
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withSubject(user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .sign(algorithm);
            return token;
        } else {
            return "401";
        }
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Matcher m = (java.util.regex.Pattern.compile(ePattern)).matcher(email);
        return m.matches();
    }

    public static boolean isValidPass(String pass) {
        boolean cap = false, small = false, digit = false;
        for (char c : pass.toCharArray()) {
            if (Character.isLowerCase(c))
                small = true;
            if (Character.isUpperCase(c))
                cap = true;
            if (Character.isDigit(c))
                digit = true;
        }
        return cap && small && digit && pass.length() >= 8;
    }

    public String searchUser(String word) {
        List<User> users = userDAO.searchUsers(word);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

}