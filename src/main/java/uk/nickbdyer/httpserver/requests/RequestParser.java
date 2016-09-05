package uk.nickbdyer.httpserver.requests;

import uk.nickbdyer.httpserver.exceptions.SocketUnreadableException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.nickbdyer.httpserver.requests.Method.*;

public class RequestParser {

    private final BufferedReader in;

    public RequestParser(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Request parse() {
        RequestLine requestLine = null;
        Map<String, String> headers = null;
        try {
            requestLine = getStatusLine(readStatusLine());
            headers = getHeaders(readHeaders());
            if (headers.containsKey("Content-Length")) {
                String body = readBody(headers.get("Content-Length"));
                return new Request(requestLine, headers, body);
            }
        } catch (IOException e) {
            throw new SocketUnreadableException();
        }
        return new Request(requestLine, headers);
    }

    private RequestLine getStatusLine(String statusLine) {
        int firstSpace = statusLine.indexOf(' ');
        Method method = validateMethod(statusLine.substring(0, (firstSpace)));
        if (statusLine.contains("?")) {
            String path = statusLine.substring((firstSpace + 1), statusLine.indexOf('?'));
            String parameters = getParameters(statusLine);
            return new RequestLine(method, path, parameters);
        }
        String path = statusLine.substring((firstSpace + 1), statusLine.indexOf(' ', firstSpace + 1));
        return new RequestLine(method, path, null);
    }

    private String getParameters(String statusLine) {
        int mark = statusLine.indexOf('?');
        return statusLine.substring(mark + 1, statusLine.indexOf(' ', mark));
    }

    private Map<String, String> getHeaders(List<String> headers) {
        //This will blow up with no header sent, Host is required in 1.1. Might require empty check
        Map<String, String> dict = new HashMap<>();
        headers.forEach(line -> dict.put(line.substring(0, line.indexOf(':')), line.substring(line.indexOf(' ') + 1)));
        return dict;
    }

    private String readStatusLine() throws IOException {
        return in.readLine();
    }

    private List<String> readHeaders() throws IOException {
        String line;
        List<String> headers = new ArrayList<>();
        while ((line = in.readLine()) != null && line.length() > 0) {
            headers.add(line);
        }
        return headers;
    }

    private String readBody(String contentLength) throws IOException {
        int readLimit = Integer.parseInt(contentLength);
        char[] buffer = new char[readLimit];
        in.read(buffer, 0, readLimit);
        return new String(buffer);
    }

    private Method validateMethod(String method) {
        try {
            return valueOf(method);
        } catch (IllegalArgumentException e) {
            return UNKNOWN_METHOD;
        }
    }

}
