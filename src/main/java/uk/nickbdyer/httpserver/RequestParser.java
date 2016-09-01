package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.exceptions.StatusLineCouldNotBeReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static uk.nickbdyer.httpserver.Method.INVALID_METHOD;

public class RequestParser {

    private final BufferedReader in;

    public RequestParser(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new StatusLineCouldNotBeReadException();
        }
    }

    private String readHeaders() {
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            //TEST line.length() > 0 character 13
            while ((line = in.readLine()) != null && line.length() > 0) {
                builder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    private Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return INVALID_METHOD;
        }
    }

}
