package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.MessageDAO;
import models.Message;
import models.Post;
import utils.UserNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessageController extends Controller {
    MessageDAO messageDAO = new MessageDAO();

    public MessageController() {
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

    public String getMessagesBetweenUser(String username1, String username2) {
        List<Message> messages = messageDAO.getMessagesBetweenUsers(username1, username2);
        messages.sort(new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return Long.compare(o1.getDateOfCreat(), o2.getDateOfCreat());
            }
        });
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public void addMessage(String text, String sender, String receiver)  {
        Message message = new Message();
        message.setDateOfCreat(System.currentTimeMillis());
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setText(text);
        messageDAO.addMessage(message);
    }

    public void addFileMessage(String filePath, String sender, String receiver)  {
        Message message = new Message();
        message.setDateOfCreat(System.currentTimeMillis());
        message.setReceiver(receiver);
        message.setSender(sender);
        message.setFilePath(filePath);
        messageDAO.addMessage(message);
    }

}
