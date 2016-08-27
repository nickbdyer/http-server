package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    private final ServerSocket serverSocket;

    public ConnectionHandler(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Socket getSocket() throws IOException {
        return serverSocket.accept();
    }
}
