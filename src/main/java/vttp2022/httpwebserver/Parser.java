package vttp2022.httpwebserver;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    
    // properties
    private static int port = 3000;
    private static String directory = "./";
    private static String[] directories;
    private List<String> tokens = new LinkedList<>();

    // constructors


    // methods
    public void parse (String[] args) {

        // tokenize it into a LinkedList, so that we can iterate it by removing the front of the token
        for (int i = 0; i < args.length; i++) {
            tokens.add(args[i]); 
        }

        // check the conditions, ensure port and directory are allocated the correct values or left at the default values
        if (tokens.size() > 0) {
            if (tokens.get(0).equalsIgnoreCase("--port")) {
                tokens.remove(0);
                port = Integer.parseInt(tokens.get(0));
                tokens.remove(0);
                if (tokens.size() > 0 && tokens.get(0).equalsIgnoreCase("--docRoot")) {
                    tokens.remove(0);
                    directory = tokens.get(0);
                }
            } 
            else if (tokens.get(0).equalsIgnoreCase("--docRoot")) {
                tokens.remove(0);
                directory = tokens.get(0);
            }
        }

        // maintain an array of directories by regex ":"
        directories = new String[args.length];
        directories = directory.split(":");
    }

    public int getPort () {
        return port;
    }
    
    public String[] getDirectories () {
        return directories;
    }

    public boolean checkDirectories () {
        File dir;
        for (int i = 0; i < directories.length; i++) {
            dir = new File(directories[i]);

            if (!dir.exists()) {
                System.out.printf("%s path does not exist" , directories[i]);
                System.exit(1);
                return false;
            }

            if (!dir.isDirectory()) {
                System.out.printf("%s path is not a directory" , directories[i]);
                System.exit(1);
                return false;
            }

            if (!dir.canRead()) {
                System.out.printf("%s path is not readable" , directories[i]);
                System.exit(1);
                return false;
            }
        }
        return true;
    }
}
