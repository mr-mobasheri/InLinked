package utils;

import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;

import HttpHandler.UserHandler;

public class Server {
    public static void main(String[] args) {
        try {
//            Files.createDirectories(Paths.get("src/main/java/com/sinarmin/server/assets"));
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            server.createContext("/users", new UserHandler());
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}