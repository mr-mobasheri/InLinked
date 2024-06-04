import HttpHandler.MessageHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

import HttpHandler.UserHandler;

public class Server {
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/users", new UserHandler());
            server.createContext("/messages", new MessageHandler());
            server.start();
            System.out.println("Server is running...");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}