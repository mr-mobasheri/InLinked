import HttpHandler.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8081), 0);
            server.createContext("/home/users", new UserHandler());
            server.createContext("/messages", new MessageHandler());
            server.createContext("/auth", new AuthHandler());
            HttpContext homeContext = server.createContext("/home", new HomeHandler());
            homeContext.getFilters().add(new JwtFilter());

            server.start();
            System.out.println("Server is running...");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}