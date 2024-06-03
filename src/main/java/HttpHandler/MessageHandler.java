package HttpHandler;


import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.MessageController;
import org.json.JSONObject;

import java.io.*;

public class MessageHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        MessageController messageController = new MessageController();
        String method = exchange.getRequestMethod();
        String[] pathDetails = exchange.getRequestURI().getPath().split("/");
        String response = "";
        switch (method) {
            case "GET": //port:id/direct/person1/person2

                if (pathDetails.length == 3) { //port:id/direct/person
                    response = messageController.getReceivedMessages(pathDetails[2]);
                    break;
                }

                response = messageController.getSentMessages(pathDetails[2], pathDetails[3]);

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
                requestBodyStream.close();
                // Process the user creation based on the request body.
                JSONObject jsonObject = new JSONObject(requestBody);
                messageController.createMessage(jsonObject.getString("text"), jsonObject.getString("sender"), jsonObject.getString("receiver"));
                response = "Done!";
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
