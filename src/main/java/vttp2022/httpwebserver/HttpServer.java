package vttp2022.httpwebserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    // properties
    private Parser parser;
    
    // constructors

    // methods
    public void start () throws IOException {
        
        parser = new Parser();
        parser.checkDirectories();

        ExecutorService threadPool = Executors.newFixedThreadPool(3); // for multi threading

        ServerSocket server = new ServerSocket(parser.getPort()); // create the socket connection
        System.out.printf("Server started, listening on port %d\n" , parser.getPort());
        
        while (true) {
            Socket sock = server.accept(); // accept request
            HttpClientConnection thr = new HttpClientConnection(sock, parser.getDirectories()); // pass the socket connection and list of directories to threads for execution
            threadPool.submit(thr);
            System.out.println("Client connection submitted to threadpool");
        }
    }
}
