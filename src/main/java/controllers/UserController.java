package controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.*;
import models.*;

public class UserController extends Controller {

    public UserController() {
    }

    public void createUser(String username, String password, String firstName, String lastName, String email,
                           String phoneNumber, String country, Date birthday) {
        User user = new User(username, password, firstName, lastName, email, phoneNumber, country, birthday);
        dao.insert(user);
    }

    public void deleteUser(String username) {
        User user = dao.getUser(username);
        dao.delete(user);
    }

    public void updateUser(String username, String password, String firstName, String lastName, String email,
                           String phoneNumber, String country, Date birthday) throws SQLException {
        User user = dao.getUser(username);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(email);
        user.setPhoneNumber(phoneNumber);
        user.setCountry(country);
        user.setBirthday(birthday);
        dao.update(user);
    }
    public void deleteAllUsers(){
        dao.deleteAllUsers();
    }

    public boolean isUserExists(String username) {
        if (username == null)
            return false;
        return dao.getUser(username) != null;
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
        User user = dao.getUser(username);
        if (user == null)
            return "No User";
        ObjectMapper objectMapper = new ObjectMapper();
        String response = null;
        try {
            return objectMapper.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        UserController userController = new UserController();
        userController.deleteAllUsers();
        userController.createUser("mrm1223", "12345678", "mohammad", "mobash", "mrm268", "0913", "Iran", new Date(2004, 11, 16));
        userController.createUser("ali2020" , "123" , "ali" , "nour" , "alinour2030@gmail.com" , "09964103631" , "Iran" , new Date(2005 , 5 , 21) );
//        User user = new User("mrm1223", "mohammad", "mobash", "mrm268", "0913",
//                "12345678", "Iran", new Date(2004, 11, 16));
//        userController.deleteUser("mrm1223");
//        System.out.println(userController.isUserExists("mrm1223"));
    }

}