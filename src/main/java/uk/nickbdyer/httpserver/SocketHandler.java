package uk.nickbdyer.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketHandler {

    private final Socket socket;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    public String getRequest() throws IOException {
        return new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
    }
}
