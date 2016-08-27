package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        new HttpServer(new ConnectionHandler(new ServerSocket(Arguments.getPort(args))), Arguments.getDirectoryPath(args)).listen();
    }

}
