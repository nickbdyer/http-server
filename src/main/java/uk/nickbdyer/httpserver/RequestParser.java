package uk.nickbdyer.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static uk.nickbdyer.httpserver.Method.INVALID_METHOD;

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
            e.printStackTrace();
        }
        return new Request(requestLine, headers);
    }

    private RequestLine getStatusLine(String statusLine) {
        int firstSpace = statusLine.indexOf(' ');
        Method method = validateMethod(statusLine.substring(0, (firstSpace)));
        String path = statusLine.substring((firstSpace + 1), statusLine.indexOf(' ', firstSpace + 1));
        return new RequestLine(method, path);
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

    private List<String> readHeaders() {
        String line;
        List<String> headers = new ArrayList<>();
        try {
            while ((line = in.readLine()) != null && line.length() > 0) {
                headers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return headers;
    }

    private String readBody(String contentLength) {
        int readLimit = Integer.parseInt(contentLength);
        char[] buffer = new char[readLimit];
        try {
            in.read(buffer, 0, readLimit);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(buffer);
    }

    private Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return INVALID_METHOD;
        }
    }

}
