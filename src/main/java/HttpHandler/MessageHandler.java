package HttpHandler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.MessageController;
import org.json.JSONException;
import org.json.JSONObject;
import utils.UserNotFoundException;

import java.io.*;

public class MessageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        MessageController messageController = new MessageController();
        String method = exchange.getRequestMethod();
        String[] pathDetails = exchange.getRequestURI().getPath().split("/");
        String response = "";
        switch (method) {
            case "GET": //port:id/messages/person1/person2
                if (pathDetails.length == 3) { //port:id/messages/person
                    response = messageController.getReceivedMessages(pathDetails[2]);
                } else {
                    response = messageController.getMessagesBetweenUsers(pathDetails[2], pathDetails[3]);
                }
                break;
            case "POST":
                // Read the request body as String.
                InputStream requestBodyStream = exchange.getRequestBody();
                BufferedReader reader = new BufferedReader(new InputStreamReader(requestBodyStream));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                // Process the user creation based on the request body.
                JSONObject jsonObject = new JSONObject(requestBody.toString());
                try {
                    messageController.createMessage(
                            jsonObject.getString("text"),
                            jsonObject.getString("sender"),
                            jsonObject.getString("receiver")
                    );
                    response = "Done!";
                } catch (UserNotFoundException e) {
                    System.out.println("UserNotFoundException: " + e.getMessage());
                    response = "user not found!";
                } catch (JSONException e) {
                    System.out.println("JSONException: " + e.getMessage());
                    response = "Incorrect json format: " + e.getMessage();
                }
                break;
//            case "DELETE":
//                if (pathDetails.length == 2) {
//                    userController.deleteAllUsers();
//                } else {
//                    // get the username from the path and delete it.
//                    userController.deleteUser(pathDetails[pathDetails.length - 1]);
//                }
//                response = "User deleted successfully";
//                break;
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

}
