public class Arguments {

    public static int getPort(String[] args) {

        int port = 5000;

        for(int i = 0; i < args.length; i++) {
            if ("-p".equals(args[i])) {
                port = Integer.parseInt(args[i + 1]);
            }
        }
        return port;
    }

}
