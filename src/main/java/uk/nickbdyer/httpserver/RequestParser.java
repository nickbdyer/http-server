package uk.nickbdyer.httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static uk.nickbdyer.httpserver.Method.METHOD_NOT_ALLOWED;

public class RequestParser {

    private final BufferedReader in;
    private final PrintWriter out;

    public RequestParser(Socket socket) throws IOException {
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    public String getRequest() {
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

    public void sendResponse(String response) {
        out.print(response);
        out.close();
    }

    public Request parse() {
        String requestString = getRequest();
        int firstSpace = requestString.indexOf(' ');
        Method method = validateMethod(requestString.substring(0, (firstSpace)));
        String path = requestString.substring((firstSpace + 1), requestString.indexOf(' ', firstSpace + 1));
        return new Request(method, path);
    }

    private Method validateMethod(String method) {
        try {
            return Method.valueOf(method);
        } catch (IllegalArgumentException e) {
            return METHOD_NOT_ALLOWED;
        }
    }

}
