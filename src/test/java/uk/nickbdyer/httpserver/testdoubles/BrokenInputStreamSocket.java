package uk.nickbdyer.httpserver.testdoubles;

import java.io.*;
import java.net.Socket;

public class BrokenInputStreamSocket extends Socket {

    private final ByteArrayOutputStream outputStream;

    public BrokenInputStreamSocket(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        throw new UncheckedIOException(new IOException("Blew Up"));
    }

    @Override
    public OutputStream getOutputStream() {
        return outputStream;
    }

}
