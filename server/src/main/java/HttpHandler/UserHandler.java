package HttpHandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.hibernate.exception.ConstraintViolationException;
import org.json.JSONException;
import org.json.JSONObject;
import controllers.*;
import utils.UserNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.jar.JarException;

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
                reader.close();
                // Process the user creation based on the request body.
                try {
                    JSONObject jsonObject = new JSONObject(requestBody.toString());
                    userController.createUser(
                            jsonObject.getString("username"),
                            jsonObject.getString("password"),
                            jsonObject.getString("firstname"),
                            jsonObject.getString("lastname"),
                            jsonObject.getString("email"),
                            jsonObject.getString("phoneNumber"),
                            jsonObject.getString("country"),
                            jsonObject.getLong("birthday")
                    );
                    response = "New user saved.";
                } catch (JSONException e) {
                    System.out.println("JSONException: " + e.getMessage());
                    response = "Incorrect json format: " + e.getMessage();
                } catch (ConstraintViolationException e) {
                    e.printStackTrace();
                    response = "Constraint Violation!";
                }
                break;
            case "DELETE":
                if (pathDetails.length == 2) {
                    userController.deleteAllUsers();
                    response = "All users were successfully deleted";
                } else {
                    try {
                        // get the username from the path and delete it.
                        userController.deleteUser(pathDetails[pathDetails.length - 1]);
                        response = "User deleted successfully";
                    } catch (UserNotFoundException e) {
                        System.out.println("UserNotFoundException: " + e.getMessage());
                        response = "user not found!";
                    }
                }
                break;
        }
        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
