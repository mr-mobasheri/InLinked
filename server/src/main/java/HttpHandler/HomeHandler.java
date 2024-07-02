package HttpHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.FollowController;
import controllers.PostController;
import controllers.UserController;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.json.JSONObject;

import javax.crypto.SecretKey;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class HomeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("/home".equals(path)) {
            handleHome(exchange);
        } else if (path.startsWith("/home/search")) {
            handleSearch(exchange);
        } else if (path.startsWith("/home/follow/")) {
            handleFollow(exchange);
        } else if (path.startsWith("/home/unfollow/")) {
            handleUnfollow(exchange);
        } else if (path.startsWith("/home/followers")) {
            handleFollower(exchange);
        } else if (path.startsWith("/home/followings")) {
            handleFollowing(exchange);
        } else {
            System.out.println("Unknown!");
        }
    }

    private void handleHome(HttpExchange exchange) {
        try {
            switch (exchange.getRequestMethod()) {
                case "GET":
                    PostController postController = new PostController();
                    if (postController.getFollowingsPosts(getUsernameFromJwt(exchange, "my-secret-key")) != null)
                        sendResponse(exchange,
                                postController.getFollowingsPosts(getUsernameFromJwt(exchange, "my-secret-key")), 200);
                    break;
                case "POST":
                    System.out.println("post");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void handleSearch(HttpExchange exchange) {
        switch (exchange.getRequestMethod()) {
            case "GET":
                UserController userController = new UserController();
                try {
                    sendResponse(exchange,
                            userController.searchUser(exchange.getRequestURI().getPath().split("/")[3]), 200);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "POST":
                System.out.println("POST");
                break;
        }
    }

    private void handleFollow(HttpExchange exchange) throws IOException {
        FollowController followController = new FollowController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        try {
            followController.follow(getUsernameFromJwt(exchange, "my-secret-key"), details[3]);
            sendResponse(exchange, "followed", 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleUnfollow(HttpExchange exchange) throws IOException {
        FollowController followController = new FollowController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        try {
            followController.unfollow(getUsernameFromJwt(exchange, "my-secret-key"), details[3]);
            sendResponse(exchange, "unfollowed", 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleFollower(HttpExchange exchange) throws IOException {
        FollowController followController = new FollowController();
        try {
            sendResponse(exchange,
                    followController.getFollower(getUsernameFromJwt(exchange, "my-secret-key")), 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleFollowing(HttpExchange exchange) throws IOException {
        FollowController followController = new FollowController();
        try {
            sendResponse(exchange,
                    followController.getFollowing(getUsernameFromJwt(exchange, "my-secret-key")), 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int statusCode) throws IOException {
        exchange.sendResponseHeaders(statusCode, response.getBytes(StandardCharsets.UTF_8).length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes(StandardCharsets.UTF_8));
        os.close();
    }

    private String getUsernameFromJwt(HttpExchange exchange, String secretKey) {
        String token = exchange.getRequestHeaders().getFirst("Authorization").substring(7);
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(token);
        return decodedJWT.getClaim("sub").asString();
    }
}
