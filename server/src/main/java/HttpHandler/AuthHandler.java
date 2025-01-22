package HttpHandler;

import controllers.UserController;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import org.hibernate.exception.ConstraintViolationException;
import utils.UserNotFoundException;

public class AuthHandler implements HttpHandler {
    private UserController userController = new UserController();
    private final String secret = "my-secret-key";
    private final Algorithm algorithm = Algorithm.HMAC256(secret);
    private BufferedReader reader;


    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        if (method.equals("POST")) {
            if ("/auth/register".equals(path)) {
                handleRegister(exchange);
            } else if ("/auth/login".equals(path)) {
                handleLogin(exchange);
            }
        } else {
            sendResponse(exchange, "Method not allowed", 405);
        }

        reader.close();

    }

    private void handleRegister(HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        try {
            JSONObject jsonObject = new JSONObject(requestBody.toString());
            String email = jsonObject.getString("email");
            String username = jsonObject.getString("username");
            String firstName = jsonObject.getString("firstname");
            String lastName = jsonObject.getString("lastname");
            String password = jsonObject.getString("password");
            String response = userController.register(email, username, firstName, lastName, password);
            if ("400".equals(response)) {
                sendResponse(exchange, "Email or Password is missing", 400);
            } else if ("406".equals(response)) {
                sendResponse(exchange, "username is already taken", 406);
            } else if ("409".equals(response)) {
                sendResponse(exchange, "this email is already registered", 409);
            } else {
                sendResponse(exchange, response, 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "Internal server error", 500);
        }
    }

    private void handleLogin(HttpExchange exchange) throws IOException {
        StringBuilder requestBody = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        reader.close();

        try {
            JSONObject jsonObject = new JSONObject(requestBody.toString());
            String email = jsonObject.getString("email");
            String password = jsonObject.getString("password");
            String response = userController.login(email, password, algorithm);
            if ("400".equals(response)) {
                sendResponse(exchange, "Email or Password is missing", 400);
            } else if ("401".equals(response)) {
                sendResponse(exchange, "email or password is incorrect", 401);
            } else {
                sendResponse(exchange, response, 200);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "Internal server error", 500);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

}