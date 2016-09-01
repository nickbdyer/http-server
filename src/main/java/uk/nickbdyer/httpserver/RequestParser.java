package uk.nickbdyer.httpserver;

import uk.nickbdyer.httpserver.exceptions.StatusLineCouldNotBeReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import static uk.nickbdyer.httpserver.Method.INVALID_METHOD;

public class RequestParser {

    private final BufferedReader in;

    public RequestParser(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public Request parse() {
        //Parse StatusLine
        String statusLine = getStatusLine();
        int firstSpace = statusLine.indexOf(' ');
        Method method = validateMethod(statusLine.substring(0, (firstSpace)));
        String path = statusLine.substring((firstSpace + 1), statusLine.indexOf(' ', firstSpace + 1));

        //Parse Headers
        String headers = getHeaders();

        //If necessary parse body

        return new Request(method, path);
    }

    private String getStatusLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new StatusLineCouldNotBeReadException();
        }
    }

    private String getHeaders() {
        StringBuilder builder = new StringBuilder();
        try {
            String line;
            while ((line = in.readLine()) != null && line.length() > 0) {
                builder.append(line);
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
