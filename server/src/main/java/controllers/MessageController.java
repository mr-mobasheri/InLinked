package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Message;
import utils.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends Controller {

    public MessageController() {
    }

    public void createMessage(String text, String sender, String receiver) throws UserNotFoundException {
        Message message = new Message(text);
        dao.addMessage(message , sender , receiver);
    }

    public String getMessagesBetweenUsers(String sender , String target) {
        List<Message> messages = dao.getMessagesBetweenUsers(sender, target);
        if(messages == null) {
            return "user not found!";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    public String getReceivedMessages(String username) {
        List<Message> messages = dao.getReceivedMessages(username);
        if(messages == null) {
            return "user not found!";
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void deleteAllMessages() {
        dao.deleteAllMessages();
    }

}
