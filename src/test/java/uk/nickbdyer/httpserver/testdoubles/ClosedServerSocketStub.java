package uk.nickbdyer.httpserver.testdoubles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class ClosedServerSocketStub extends ServerSocket {

    public ClosedServerSocketStub() throws IOException {
    }

    @Override
    public Socket accept() throws SocketException {
        throw new SocketException("Socket closed");
    }

}
