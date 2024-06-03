package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Message;

import java.util.ArrayList;

public class MessageController extends Controller {

    public MessageController() {
    }

    public void createMessage(String text, String sender, String receiver) {
        Message message = new Message(text);
        dao.addMessage(message , sender , receiver);
    }

    public String getSentMessages(String username , String target) {
        ArrayList<Message> temp = (ArrayList<Message>) dao.getSentMessages(username);
        ArrayList<Message> messages = new ArrayList<>();
        for (Message message : temp){
            if (message.getReceiver().getUsername().equals(target)){
                messages.add(message);
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(messages);
        } catch (JsonProcessingException e) {
            return null;
        }
    }


    public String getReceivedMessages(String username) {
        ArrayList<Message> messages = (ArrayList<Message>) dao.getReceivedMessages(username);
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


//    public static void main(String[] args) {
//        MessageController messageController = new MessageController();
//        messageController.createMessage("hello", "ali" , "mahdi");
//    }
}
