package uk.nickbdyer.httpserver;

public class Arguments {

    private final String[] args;

    public Arguments(String[] args) {
        this.args = args;
    }

    public int getPort() {
        String port = "5000";
        port = findFlagInArgsAndReturnValue(args, port, "-p");
        return Integer.parseInt(port);
    }

    public String getDirectoryPath() {
        String directoryPath = "/Users/nickdyer/projects/cob_spec/public";
        directoryPath = findFlagInArgsAndReturnValue(args, directoryPath, "-d");
        return directoryPath;
    }

    private String findFlagInArgsAndReturnValue(String[] args, String value, String flag) {
        for(int i = 0; i < args.length; i++) {
            if (flag.equals(args[i])) {
                value = args[i + 1];
            }
        }
        return value;
    }
}
