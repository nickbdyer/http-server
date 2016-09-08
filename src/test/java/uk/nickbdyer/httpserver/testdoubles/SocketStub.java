package uk.nickbdyer.httpserver.testdoubles;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStub extends Socket {

    private final String request;
    private final OutputStream output;

    public SocketStub(String request, OutputStream output) {
        this.request = request;
        this.output = output;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(request.getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

}
