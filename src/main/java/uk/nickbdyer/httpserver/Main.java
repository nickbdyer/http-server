package uk.nickbdyer.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        new HttpServer(new ServerSocket(Arguments.getPort(args)), Arguments.getDirectoryPath(args)).listen();
    }

}
