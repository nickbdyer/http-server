package uk.nickbdyer.httpserver.testdoubles;

import java.io.IOException;
import java.io.InputStream;

public class UnreadbleInputStreamStub extends InputStream {
    @Override
    public int read() throws IOException {
        throw new IOException();
    }
}
