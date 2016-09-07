package uk.nickbdyer.httpserver.requests;

import uk.nickbdyer.httpserver.middleware.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.*;

import static uk.nickbdyer.httpserver.requests.Method.UNKNOWN_METHOD;
import static uk.nickbdyer.httpserver.requests.Method.valueOf;

public class RequestParser {

    private final BufferedReader in;
    private final Logger logger;

    public RequestParser(Socket socket, Logger logger) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.logger = logger;
    }

    public Request parse() {
        RequestLine requestLine;
        Map<String, String> headers;
        try {
            requestLine = getStatusLine(readStatusLine());
            headers = getHeaders(readHeaders());
            if (headers.containsKey("Content-Length")) {
                String body = readBody(headers.get("Content-Length"));
                return new Request(requestLine, headers, body);
            }
        } catch (IOException e) {
            return new Request(new RequestLine(null, null, null), null, null);
        }
        return new Request(requestLine, headers, "");
    }

    private RequestLine getStatusLine(String statusLine) {
        if (statusLine == null) return new RequestLine(null, null, null);
        int firstSpace = statusLine.indexOf(' ');
        Method method = validateMethod(statusLine.substring(0, (firstSpace)));
        if (statusLine.contains("?")) {
            String path = statusLine.substring((firstSpace + 1), statusLine.indexOf('?'));
            Map<String, String> parameters = getParameters(statusLine);
            return new RequestLine(method, path, parameters);
        }
        String path = statusLine.substring((firstSpace + 1), statusLine.indexOf(' ', firstSpace + 1));
        return new RequestLine(method, path, null);
    }

    private Map<String, String> getParameters(String statusLine) {
        Map<String, String> parameters = new HashMap<>();
        int mark = statusLine.indexOf('?');
        String paramsString = statusLine.substring(mark + 1, statusLine.indexOf(' ', mark));
        for (String pair : paramsString.split("&")) {
            addToParams(parameters, pair);
        }
        return parameters;
    }

    private void addToParams(Map<String, String> parameters, String pair) {
        try {
            int equalsMark = pair.indexOf("=");
            String key = decodeParams(pair.substring(0, equalsMark));
            String value = decodeParams(pair.substring(equalsMark + 1));
            parameters.put(key, value);
        } catch (UnsupportedEncodingException ignored) {
        }
    }

    private static String decodeParams(String encodedString) throws UnsupportedEncodingException {
        return URLDecoder.decode(encodedString, "UTF-8");
    }

    private Map<String, String> getHeaders(List<String> headers) {
        Map<String, String> dict = new HashMap<>();
        headers.forEach(line -> dict.put(line.substring(0, line.indexOf(':')), line.substring(line.indexOf(' ') + 1)));
        return dict;
    }

    private String readStatusLine() throws IOException {
        String statusLine = in.readLine();
        logger.log(statusLine);
        return statusLine;
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
