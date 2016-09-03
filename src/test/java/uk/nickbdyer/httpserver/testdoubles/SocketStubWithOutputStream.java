package uk.nickbdyer.httpserver.testdoubles;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStubWithOutputStream extends Socket {

    private final ByteArrayOutputStream output;

    public SocketStubWithOutputStream(ByteArrayOutputStream output) {
        this.output = output;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream("".getBytes());
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

}
