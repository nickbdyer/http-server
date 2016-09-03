package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ResponseDispatcher {

    private final PrintWriter out;

    public ResponseDispatcher(Socket socket) throws IOException {
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendResponse(String response) {
        out.print(response);
        out.close();
    }

}
