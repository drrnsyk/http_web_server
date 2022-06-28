package vttp2022.httpwebserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class HttpClientConnection implements Runnable{
    
    private Socket sock;
    private String[] dirArray;
    private Parser parser;

    public HttpClientConnection (Socket s , String[] d) {
        sock = s;
        dirArray = new String[d.length];
        dirArray = d;
    }

    @Override
    public void run() {

        try {
            // read client input 
            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            String input = dis.readUTF();
            String[] inputArray = input.split(" ");
            String methodName = inputArray[0];
            String resourceName = inputArray[1];
            parser = new Parser();
            
            // check if it is a valid GET method
            if (!inputArray[0].equalsIgnoreCase("GET")) {
                OutputStream os = sock.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);
                dos.writeUTF("HTTP/1.1 405 Method Not Allowed\r\n");
                dos.writeUTF("\r\n");
                dos.writeUTF(methodName + " " + "not supported\r\n");
                dos.flush();
                dos.close();
                return;
            }

            // check if the resource exist
            for (int i = 0; i < parser.getDirectories().length; i++) {
                dirArray = parser.getDirectories();
                String dir = dirArray[i];
                File directory = new File(dir + resourceName);
                OutputStream os = sock.getOutputStream();
                DataOutputStream dos = new DataOutputStream(os);

                System.out.println(directory);
                if (directory.exists()) {
                    if (resourceName.endsWith(".png")) {
                        dos.writeUTF("HTTP/1.1 200 OK\r\n");
                        dos.writeUTF("Content-Type: image/png\r\n");
                        dos.writeUTF("\r\n");
                    }
                    else 
                    {
                        dos.writeUTF("HTTP/1.1 200 OK\r\n");
                        dos.writeUTF("\r\n");
                    }

                    // send resource contents as bytes
                    byte[] bytes = new byte[2048];
                    int size = 0;
                    FileInputStream fis = new FileInputStream(directory);
                    while (size >= 0) {
                        size = fis.read(bytes);
                        if (size > 0) {
                            dos.write(bytes, 0, size);
                    } 

                    dos.flush();
                    os.flush();
                    fis.close();
                    is.close();
                    dos.close();
                    os.close();
                    return;
                    }
                }
  
                if (i == parser.getDirectories().length - 1) { // if it is at the end of the full loop, means file doesnt exist in any of the directories
                    dos.writeUTF("HTTP/1.1 404 Not Found\r\n");
                    dos.writeUTF("\r\n");
                    dos.writeUTF(resourceName + " " + "not found\r\n");
                    dos.flush();
                    dos.close();
                    return;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
