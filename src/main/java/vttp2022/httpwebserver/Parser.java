package vttp2022.httpwebserver;

import java.io.File;

public class Parser {
    
    // properties
    private static int port = 3000;
    private static String defaultDir = "./";
    private static String[] directories;


    // constructors



    // methods
    public void parse (String[] args) {
        if (args.length == 0) {
            // use default port
            // use default dir
            //System.out.println(defaultDir);
        } 
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("--port")) {
                port = Integer.parseInt(args[1]);
            }
            else if (args[0].equalsIgnoreCase("--docRoot")) {
                port = 3000;
                System.out.println(port);
                String[] dirArray = args[1].split(":");
                directories = new String[dirArray.length];
                directories = dirArray;
                // System.out.println(dirArray[0]);
                // System.out.println(dirArray[1]);
                // defaultDir = dirArray[0];
                // if (dirArray.length > 1) {
                //     directoriesArray = dirArray[1].split("/");
                //     directoriesArray[0] = defaultDir;
                //     for (int i = 1; i < directoriesArray.length; i++) {
                //         directoriesArray[i] = "./" + directoriesArray[i];
                //     }
                // }
            }
        }
        else if (args.length == 4) {
            port = Integer.parseInt(args[1]);
            if (args[2].equalsIgnoreCase("--docRoot")) {
                String[] dirArray = args[3].split(":");
                directories = new String[dirArray.length];
                directories = dirArray;
                // System.out.println(dirArray[0]);
                // System.out.println(dirArray[1]);
                // defaultDir = dirArray[0];
                // if (dirArray.length > 1) {
                //     directoriesArray = dirArray[1].split("/");
                //     directoriesArray[0] = defaultDir;
                //     for (int i = 1; i < directoriesArray.length; i++) {
                //         directoriesArray[i] = "./" + directoriesArray[i];
                //     }
                // }
            }
        }
    }

    public int getPort () {
        return port;
    }
    
    public String[] getDirectories () {
        System.out.println(directories[0]);
        System.out.println(directories[1]);
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
