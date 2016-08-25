package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServer {

    private final int port;
    private final String directoryPath;

    public HttpServer(int port, String directoryPath) {
        this.port = port;
        this.directoryPath = directoryPath;
    }

    public int getPort() {
        return port;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void listen() {
        try {
            new ServerSocket(5000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
