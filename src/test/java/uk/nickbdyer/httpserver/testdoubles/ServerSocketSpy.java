package uk.nickbdyer.httpserver.testdoubles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketSpy extends ServerSocket {

    private boolean acceptWasCalled;

    public ServerSocketSpy(int port) throws IOException {
        super(port);
        acceptWasCalled = false;
    }

    public boolean acceptWasCalled() {
       return acceptWasCalled;
    }

    @Override
    public Socket accept() {
        acceptWasCalled = true;
        return null;
    }

}
