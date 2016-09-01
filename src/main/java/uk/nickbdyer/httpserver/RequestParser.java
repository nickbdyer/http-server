package uk.nickbdyer.httpserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static uk.nickbdyer.httpserver.Method.INVALID_METHOD;

public class RequestParser {

    private final InputStream in;

    public RequestParser(Socket socket) throws IOException {
        this.in = socket.getInputStream();
    }

    public Request parse() {
        StatusLine statusLine = getStatusLine(readStatusLine());
        //Parse Headers
        Map<String, String> headers = getHeaders(readHeaders());

        //If necessary parse body

        return new Request(statusLine, headers);
    }

    private Map<String, String> getHeaders(String headers) {
        //This will blow up with no header sent, Host is required in 1.1. Might require empty check
        Map<String, String> dict = new HashMap<>();
        Arrays.stream(headers.split("\\r?\\n"))
                .forEach(line -> dict.put(line.substring(0, line.indexOf(':')), line.substring(line.indexOf(' ') + 1)));
        return dict;
    }

    private StatusLine getStatusLine(String statusLine) {
        int firstSpace = statusLine.indexOf(' ');
        Method method = validateMethod(statusLine.substring(0, (firstSpace)));
        String path = statusLine.substring((firstSpace + 1), statusLine.indexOf(' ', firstSpace + 1));
        return new StatusLine(method, path);
    }

    private String readStatusLine() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            int character = in.read();
            do {
                result.write(character);
                character = in.read();
            } while(character != 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private String readHeaders() {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        try {
            int character = in.read();
            do {
                result.write(character);
                character = in.read();
            } while (endOfHeaderHasNotBeenReached(character));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private boolean endOfHeaderHasNotBeenReached(int character) {
        return (character != 13) && (character != -1);
    }

    private Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return INVALID_METHOD;
        }
    }

}
