public class Main {

    public static void main(String[] args) {
        new HttpServer(Arguments.getPort(args), Arguments.getDirectoryPath(args));
    }

}
