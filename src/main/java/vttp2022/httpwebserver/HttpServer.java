package vttp2022.httpwebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

    // properties
    private Parser parser;


    // constructors

    


    // methods
    public void start () throws IOException {
        parser = new Parser();
        ServerSocket server = new ServerSocket(parser.getPort());
        System.out.printf("Server started, listening on port %d\n" , parser.getPort());
        // parser.getDirectories(); // to print out directories if its correct 
        parser.checkDirectories();
        Socket sock = server.accept();

    }

}
