package uk.nickbdyer.httpserver.testdoubles;

import uk.nickbdyer.httpserver.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandlerSpy extends ConnectionHandler{

    private boolean socketConnectionWasMade;
    public int numberOfConnectionsMade;

    public ConnectionHandlerSpy(ServerSocket serverSocket) {
        super(serverSocket);
        socketConnectionWasMade = false;
        numberOfConnectionsMade = 0;
    }

    @Override
    public Socket getSocket() throws IOException {
        Socket connection = serverSocket.accept();
        socketConnectionWasMade = true;
        numberOfConnectionsMade += 1;
        return connection;
    }

    public boolean socketConnectionWasMade() {
        return socketConnectionWasMade;
    }
}
