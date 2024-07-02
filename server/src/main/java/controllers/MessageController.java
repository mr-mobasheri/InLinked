package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.MessageDAO;
import models.Message;
import utils.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class MessageController extends Controller {
    MessageDAO messageDAO = new MessageDAO();

    public MessageController() {
    }

    public void createMessage(String text, String sender, String receiver) throws UserNotFoundException {
        Message message = new Message(text);
        messageDAO.addMessage(message , sender , receiver);
    }

    public String getMessagesBetweenUsers(String sender , String target) {
        List<Message> messages = messageDAO.getMessagesBetweenUsers(sender, target);
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
        List<Message> messages = messageDAO.getReceivedMessages(username);
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
        messageDAO.deleteAllMessages();
    }

}
