public class HttpServer {

    private final int port;
    private final String directoryPath;

    public HttpServer(int port, String directoryPath) {
        this.port = port;
        this.directoryPath = directoryPath;
    }

    public int getPort() {
        return port;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }
}
