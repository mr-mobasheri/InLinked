package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataAccess.ConnectDAO;

public class ConnectController extends Controller {
    ConnectDAO connectDAO = new ConnectDAO();

    public String getConnectRequests(String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(connectDAO.getConnectRequests(username));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMyRequests(String username) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(connectDAO.getMyRequests(username));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getConnects(String myUsername) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(connectDAO.getConnects(myUsername));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void requestToConnect(String myUsername, String conUsername) {
        connectDAO.requestToConnect(myUsername, conUsername);
    }

    public void accept(String myUsername, String conUsername) {
        connectDAO.accept(myUsername, conUsername);
    }

    public void reject(String myUsername, String conUsername) {
        connectDAO.reject(myUsername, conUsername);
    }

    public void disconnect(String myUsername, String conUsername) {
        connectDAO.disConnect(myUsername, conUsername);
    }

    public int isConnected(String myUsername, String conUsername) {
        return connectDAO.isConnected(myUsername, conUsername);
    }

    public void deleteRequest(String myUsername, String conUsername) {
        connectDAO.deleteRequest(myUsername, conUsername);
    }
}
