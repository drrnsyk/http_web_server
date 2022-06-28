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

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        ServerSocket server = new ServerSocket(parser.getPort());
        System.out.printf("Server started, listening on port %d\n" , parser.getPort());
        // parser.getDirectories(); // to print out directories if its correct 
        parser.checkDirectories();

        while (true) {
            Socket sock = server.accept();
            HttpClientConnection thr = new HttpClientConnection(sock, parser.getDirectories());
            threadPool.submit(thr);
            System.out.println("Submitted to threadpool");
        }
        

    }

}
