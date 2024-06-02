package HttpHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONObject;
import controllers.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;

public class UserHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        UserController userController = new UserController();
        String method = exchange.getRequestMethod();
        String[] pathDetails = exchange.getRequestURI().getPath().split("/");
        String response = "";
        switch (method) {
            case "GET":
                if (pathDetails.length == 2) {
                    response = userController.getUsers();
                } else {
                    String userId = pathDetails[pathDetails.length - 1];
                    response = userController.getUser(userId);
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
                requestBodyStream.close();
                // Process the user creation based on the request body.
                JSONObject jsonObject = new JSONObject(requestBody);
                userController.createUser(jsonObject.getString("username"), jsonObject.getString("password"),
                        jsonObject.getString("firstname"), jsonObject.getString("lastname"),
                        jsonObject.getString("email"), jsonObject.getString("phoneNumber"),
                        jsonObject.getString("country"), Integer.parseInt(jsonObject.getString("birthday")));
                response = "New user saved!";
                break;
            case "DELETE":
                if (pathDetails.length == 2) {
                    userController.deleteAllUsers();
                } else {
                    // get the username from the path and delete it.
                    userController.deleteUser(pathDetails[pathDetails.length - 1]);
                }
                response = "User deleted successfully";
                break;
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
