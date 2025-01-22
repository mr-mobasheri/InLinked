package HttpHandler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controllers.*;
import dataAccess.*;
import models.*;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        if ("/home".equals(path)) {
            handleHome(exchange);
        } else if (path.startsWith("/home/search/post")) {
            handleSearchPost(exchange);
        }else if (path.startsWith("/home/search")) {
            handleSearch(exchange);
        } else if (path.startsWith("/home/follow/")) {
            handleFollow(exchange);
        } else if (path.startsWith("/home/unfollow/")) {
            handleUnfollow(exchange);
        } else if (path.startsWith("/home/followers")) {
            handleFollower(exchange);
        } else if (path.startsWith("/home/followings")) {
            handleFollowing(exchange);
        } else if (path.startsWith("/home/post/like")) {
            handleLike(exchange);
        } else if (path.startsWith("/home/post/unlike")) {
            handleUnlike(exchange);
        } else if (path.startsWith("/home/post/comment")) {
            handleComment(exchange);
        } else if (path.startsWith("/home/post")) {
            handlePost(exchange);
        } else if (path.startsWith("/home/profile/image")) {
            handleImageProfile(exchange);
        } else if (path.startsWith("/home/profile/myInfo")) {
            handleMyInfo(exchange);
        } else if (path.startsWith("/home/profile/education")) {
            handleEducation(exchange);
        } else if (path.startsWith("/home/profile/contactInfo")) {
            handleContactInfo(exchange);
        } else if (path.startsWith("/home/profile")) {
            handleProfile(exchange);
        } else if (path.startsWith("/home/user")) {
            handleUser(exchange);
        } else if (path.startsWith("/home/connect")) {
            handleConnect(exchange);
        } else if (path.startsWith("/home/disconnect")) {
            handleDisconnect(exchange);
        } else if (path.startsWith("/home/message/file")) {
            handleFileMessage(exchange);
        } else if (path.startsWith("/home/message")) {
            handleMessage(exchange);
        } else {
            System.out.println("Unknown!");
        }
    }

    private void handleSearchPost(HttpExchange exchange) {
        try {
            PostDAO postDAO = new PostDAO();
            List<Post> posts = postDAO.search(exchange.getRequestURI().getPath().split("/")[4]);
            ObjectMapper objectMapper = new ObjectMapper();
            sendResponse(exchange, objectMapper.writeValueAsString(posts), 200);
        } catch (Exception e) {
            e.printStackTrace();
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
                    else {
                        sendResponse(exchange, "not found", 406);
                    }
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
                            userController.searchUser(exchange.getRequestURI().getPath().split("/")[3], getUsernameFromJwt(exchange, "my-secret-key")), 200);
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

    private void handlePost(HttpExchange exchange) throws IOException {
        PostController postController = new PostController();
        if (exchange.getRequestMethod().equals("GET")) {
            try {
                if (exchange.getRequestURI().getPath().length() == 3) {
                    sendResponse(exchange,
                            postController.getPosts(getUsernameFromJwt(exchange, "my-secret-key")), 200);
                } else {
                    String[] details = exchange.getRequestURI().getPath().split("/");
                    sendResponse(exchange, postController.getPosts(details[3]), 200);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (exchange.getRequestMethod().equals("POST")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                ObjectMapper objectMapper = new ObjectMapper();
                Post post = objectMapper.readValue(requestBody.toString(), Post.class);
                postController.addPost(post, getUsernameFromJwt(exchange, "my-secret-key"));
                sendResponse(exchange, "new post saved", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        }
    }

    private void handleProfile(HttpExchange exchange) throws IOException {
        UserController userController = new UserController();
        try {
            sendResponse(exchange,
                    userController.getUser(getUsernameFromJwt(exchange, "my-secret-key")), 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleImageProfile(HttpExchange exchange) throws IOException {
        UserController userController = new UserController();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            reader.close();
            userController.updateImage(getUsernameFromJwt(exchange, "my-secret-key"), requestBody.toString());
            sendResponse(exchange, "image updated", 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleMyInfo(HttpExchange exchange) throws IOException {
        MyInfoDAO myInfoDAO = new MyInfoDAO();
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                JSONObject jsonObject = new JSONObject(requestBody.toString());
                myInfoDAO.updateUserMyInfo(getUsernameFromJwt(exchange, "my-secret-key"),
                        jsonObject.getString("additionalName"),
                        jsonObject.getString("headline"),
                        jsonObject.getString("currentJobPosition"),
                        jsonObject.getString("levelOfEducation"),
                        jsonObject.getString("educationPlace"),
                        jsonObject.getString("city"),
                        jsonObject.getString("profession"),
                        jsonObject.getString("condition"));
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsername(getUsernameFromJwt(exchange, "my-secret-key"));
                user.setFirstName(jsonObject.getString("firstName"));
                user.setLastName(jsonObject.getString("lastName"));
                user.setCountry(jsonObject.getString("country"));
                userDAO.update(user);
                sendResponse(exchange, "updated", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (exchange.getRequestMethod().equals("GET")) {
            try {
                MyInformation myInfo = myInfoDAO.getMyInfo(getUsernameFromJwt(exchange, "my-secret-key"));
                ObjectMapper objectMapper = new ObjectMapper();
                String response = objectMapper.writeValueAsString(myInfo);
                sendResponse(exchange, response, 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        }
    }

    private void handleEducation(HttpExchange exchange) throws IOException {
        EducationDAO educationDAO = new EducationDAO();
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                JSONObject jsonObject = new JSONObject(requestBody.toString());
                educationDAO.updateUserEducation(getUsernameFromJwt(exchange, "my-secret-key"),
                        jsonObject.getString("nameOfTheSchool"),
                        jsonObject.getString("fieldOfStudy"),
                        Long.parseLong(jsonObject.getString("startStudying")),
                        Long.parseLong(jsonObject.getString("endOfEducation")),
                        jsonObject.getString("grade"),
                        jsonObject.getString("activities"),
                        jsonObject.getString("description"));
                sendResponse(exchange, "updated", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else {
            try {
                Education education= educationDAO.getEducation(getUsernameFromJwt(exchange, "my-secret-key"));
                ObjectMapper objectMapper = new ObjectMapper();
                sendResponse(exchange, objectMapper.writeValueAsString(education), 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        }
    }

    private void handleContactInfo(HttpExchange exchange) throws IOException {
        ContactDAO contactDAO = new ContactDAO();
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                JSONObject jsonObject = new JSONObject(requestBody.toString());
                contactDAO.updateUserContactInfo(getUsernameFromJwt(exchange, "my-secret-key"),
                        jsonObject.getString("email"),
                        jsonObject.getString("phoneNumber"),
                        jsonObject.getString("phoneType"),
                        jsonObject.getString("address"),
                        jsonObject.getString("birthday"),
                        jsonObject.getString("birthMonth"),
                        jsonObject.getString("birthdayDisplayPolicy"),
                        jsonObject.getString("instantCommunication"));
                sendResponse(exchange, "updated", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else {
            try {
                ContactInfo contactInfo = contactDAO.getContactInfo(getUsernameFromJwt(exchange, "my-secret-key"));
                ObjectMapper objectMapper = new ObjectMapper();
                sendResponse(exchange, objectMapper.writeValueAsString(contactInfo), 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        }
    }

    private void handleLike(HttpExchange exchange) throws IOException {
        PostController postController = new PostController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        try {
            if (postController.like(Integer.parseInt(details[4]), getUsernameFromJwt(exchange, "my-secret-key"))) {
                sendResponse(exchange, "liked", 200);
            } else {
                sendResponse(exchange, "server error", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleUnlike(HttpExchange exchange) throws IOException {
        PostController postController = new PostController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        try {
            if (postController.unlike(Integer.parseInt(details[4]), getUsernameFromJwt(exchange, "my-secret-key"))) {
                sendResponse(exchange, "liked", 200);
            } else {
                sendResponse(exchange, "server error", 400);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleMessage(HttpExchange exchange) throws IOException {
        MessageController messageController = new MessageController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        if (details.length == 4) {
            if (exchange.getRequestMethod().equals("GET")) {
                try {
                    String response = messageController.getMessagesBetweenUser(getUsernameFromJwt(exchange, "my-secret-key"), details[3]);
                    sendResponse(exchange, response, 200);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendResponse(exchange, "server error", 400);
                }
            } else if (exchange.getRequestMethod().equals("POST")) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                    StringBuilder requestBody = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }
                    reader.close();
                    messageController.addMessage(requestBody.toString(),
                            getUsernameFromJwt(exchange, "my-secret-key"), details[3]);
                    sendResponse(exchange, "sent", 200);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendResponse(exchange, "server error", 400);
                }
            }
        }
        sendResponse(exchange, "no username", 400);
    }

    private void handleFileMessage(HttpExchange exchange) throws IOException {
        MessageController messageController = new MessageController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        if (details.length == 5) {
            if (exchange.getRequestMethod().equals("POST")) {
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                    StringBuilder requestBody = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        requestBody.append(line);
                    }
                    reader.close();
                    messageController.addFileMessage(requestBody.toString(),
                            getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                    sendResponse(exchange, "sent", 200);
                } catch (Exception e) {
                    e.printStackTrace();
                    sendResponse(exchange, "server error", 400);
                }
            }
        }
        sendResponse(exchange, "no username", 400);
    }

    private void handleConnect(HttpExchange exchange) throws IOException {
        ConnectController connectController = new ConnectController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        if (details.length == 3) {
            try {
                String response = connectController.getConnects(getUsernameFromJwt(exchange, "my-secret-key"));
                sendResponse(exchange, response, 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("request")) {
            try {
                connectController.requestToConnect(getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                sendResponse(exchange, "request sent", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("myRequest")) {
            try {
                String response = connectController.getMyRequests(getUsernameFromJwt(exchange, "my-secret-key"));
                sendResponse(exchange, response, 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("otherRequest")) {
            try {
                String response = connectController.getConnectRequests(getUsernameFromJwt(exchange, "my-secret-key"));
                sendResponse(exchange, response, 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("accept")) {
            try {
                connectController.accept(getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                sendResponse(exchange, "accepted", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("reject")) {
            try {
                connectController.reject(getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                sendResponse(exchange, "rejected", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("deleteRequest")) {
            try {
                connectController.deleteRequest(getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                sendResponse(exchange, "deleted", 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (details[3].equals("isConnected")) {
            try {
                int status = connectController.isConnected(getUsernameFromJwt(exchange, "my-secret-key"), details[4]);
                if (status == 0) {
                    sendResponse(exchange, "no", 200);
                } else if (status == 1) {
                    sendResponse(exchange, "yes", 200);
                } else if (status == 2) {
                    sendResponse(exchange, "requested", 200);
                } else if (status == 3) {
                    sendResponse(exchange, "requests", 200);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        }
    }

    private void handleDisconnect(HttpExchange exchange) throws IOException {
        ConnectController connectController = new ConnectController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        connectController.disconnect(getUsernameFromJwt(exchange, "my-secret-key"), details[3]);
        sendResponse(exchange, "disconnected", 200);
    }

    private void handleUser(HttpExchange exchange) throws IOException {
        String[] details = exchange.getRequestURI().getPath().split("/");
        if (details.length == 4) {
            try {
                UserController userController = new UserController();
                sendResponse(exchange, userController.getUser(details[3]), 200);
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
            return;
        }
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
            StringBuilder requestBody = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
            reader.close();
            ObjectMapper objectMapper = new ObjectMapper();
            ArrayList<String> usernames = objectMapper.readValue(requestBody.toString(), new TypeReference<ArrayList<String>>() {
            });
            UserDAO userDAO = new UserDAO();
            ArrayList<User> users = new ArrayList<>();
            for (String username : usernames) {
                users.add(userDAO.getUserByUsername(username));
            }
            sendResponse(exchange, objectMapper.writeValueAsString(users), 200);
        } catch (Exception e) {
            e.printStackTrace();
            sendResponse(exchange, "server error", 400);
        }
    }

    private void handleComment(HttpExchange exchange) throws IOException {
        PostController postController = new PostController();
        String[] details = exchange.getRequestURI().getPath().split("/");
        ObjectMapper objectMapper = new ObjectMapper();
        if (exchange.getRequestMethod().equals("POST")) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
                StringBuilder requestBody = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    requestBody.append(line);
                }
                reader.close();
                String text = objectMapper.readValue(requestBody.toString(), String.class);
                if (postController.comment(Integer.parseInt(details[4]), text, getUsernameFromJwt(exchange, "my-secret-key"))) {
                    sendResponse(exchange, "comment added", 200);
                } else {
                    sendResponse(exchange, "server error", 400);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
        } else if (exchange.getRequestMethod().equals("GET")) {
            try {
                String post = postController.getCommentPostByID(Integer.parseInt(details[4]));
                if (post != null) {
                    sendResponse(exchange, post, 200);
                } else {
                    sendResponse(exchange, "server error", 400);
                }
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, "server error", 400);
            }
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
