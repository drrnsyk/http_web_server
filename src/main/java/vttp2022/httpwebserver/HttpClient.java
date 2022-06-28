package vttp2022.httpwebserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

// FOR TESTING PURPOSES ONLY

public class HttpClient {
    public static void main(String[] args) throws IOException, EOFException {

        Socket sock = new Socket("localhost", 8080);
        //Socket sock = new Socket(args[0]); // established connection, server will load/create directory folder
        OutputStream os = sock.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        dos.writeUTF("GET /index.html HTTP/1.1");
        dos.flush();
        dos.close();

        try {
            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String response;
            response = dis.readUTF();
            System.out.println();
            System.out.print(response);
            response = dis.readUTF();
            System.out.print(response);
            response = dis.readUTF();
            System.out.print(response);
        } catch (EOFException e) {
            // e.printStackTrace();
            // System.out.println("end of file reached");
        }

    }
}
