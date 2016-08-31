package uk.nickbdyer.httpserver;

import java.io.*;
import java.net.Socket;

public class SocketHandler {

    private final BufferedReader in;

    public SocketHandler(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String getRequest() throws IOException {
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = in.readLine()) != null && line.length() > 0) {
            builder.append(line);
        }
        return builder.toString();
    }
}
