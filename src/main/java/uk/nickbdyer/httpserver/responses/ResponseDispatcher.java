package uk.nickbdyer.httpserver.responses;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ResponseDispatcher {

    private final OutputStream out;

    public ResponseDispatcher(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
    }

    public void sendResponse(String statusAndHeaders, byte[] body) throws IOException {
        out.write(statusAndHeaders.getBytes());
        if (body != null) {
            out.write(body);
        }
        out.close();
    }

}
