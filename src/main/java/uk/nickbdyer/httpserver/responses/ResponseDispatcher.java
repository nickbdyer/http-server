package uk.nickbdyer.httpserver.responses;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ResponseDispatcher {

    private final OutputStream out;
    private final Map<Integer, String> responseCodes;

    public ResponseDispatcher(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        this.responseCodes = buildResponseMap();
    }

    public void sendResponse(int code, String statusAndHeaders, byte[] body) throws IOException {
        out.write(buildStatusLine(code));
        out.write(statusAndHeaders.getBytes());
        if (body != null) {
            out.write(body);
        }
        out.close();
    }

    private byte[] buildStatusLine(int code) {
        String statusLine = "HTTP/1.1 " + code + " " + responseCodes.get(code);
        return (statusLine + "\n").getBytes();
    }

    private Map<Integer, String> buildResponseMap() {
        Map<Integer, String> responses = new HashMap<>();
        responses.put(200, "OK");
        responses.put(404, "Not Found");
        responses.put(405, "Method Not Allowed");
        responses.put(302, "Found");
        responses.put(418, "Teapot");
        return responses;
    }
}
