package vttp2022.httpwebserver;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.parse(args);
        HttpServer server = new HttpServer();
        server.start();
    }
}
