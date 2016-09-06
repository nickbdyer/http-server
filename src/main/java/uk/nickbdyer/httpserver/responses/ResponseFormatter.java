package uk.nickbdyer.httpserver.responses;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

public class ResponseFormatter {

    private final OutputStream out;
    private final Map<Integer, String> responseCodes;
    private final int statusCode;
    private final Map<String, String> headers;
    private final byte[] body;

    public ResponseFormatter(Socket socket, Response response) throws IOException {
        this.out = socket.getOutputStream();
        this.statusCode = response.getStatusCode();
        this.headers = response.getHeaders();
        this.body = response.getBody();
        this.responseCodes = buildResponseMap();
    }

    public void sendResponse() throws IOException {
        out.write(buildStatusLine(statusCode));
        out.write(transfromHeadersToString(headers).getBytes());
        if (body != null) {
            out.write(body);
        }
        out.close();
    }

    private String transfromHeadersToString(Map<String, String> headers) {
        headers.put("Date", RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT"))));
        if (body != null) {
            headers.put("Content-Length", Integer.toString(body.length));
        }
        return headers.entrySet().stream()
                .map(entry -> entry.getKey() + ": "+ entry.getValue() + "\r\n")
                .collect(Collectors.joining()) + "\r\n";
    }

    private byte[] buildStatusLine(int code) {
        String statusLine = "HTTP/1.1 " + code + " " + responseCodes.get(code) + "\n";
        return statusLine.getBytes();
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
