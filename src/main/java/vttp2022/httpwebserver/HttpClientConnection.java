package vttp2022.httpwebserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class HttpClientConnection implements Runnable{
    
    private Socket client;
    private String[] dirArray;

    //constructor
    public HttpClientConnection (Socket sock , String[] directoriesArray) {
        client = sock;
        dirArray = new String[directoriesArray.length];
        dirArray = directoriesArray;
    }

    @Override
    public void run() {
        try {
            // read and print request from client browser
            InputStreamReader isr = new InputStreamReader(client.getInputStream()); // ask socket to give me the inputStream
            BufferedReader br = new BufferedReader(isr);
            StringBuilder request = new StringBuilder(); // piece together strings by appending
            String line;
            line = br.readLine();
            while (!line.isBlank()) {
                request.append(line + "\r\n");
                line = br.readLine();
            }
            System.out.println("----------------------REQUEST----------------------");
            System.out.println(request);

            // check the conditions of request header (first line)
            String[] strArray = request.toString().split("\n");
            String firstLine = strArray[0]; // this will give the first header line "GET /index.html HTTP/1.1"
            String[] firstLineArray = firstLine.split(" ");
            String method = firstLineArray[0]; // this will give the method GET
            String resource = firstLineArray[1]; // this will give the resource index.html
            if (resource.equalsIgnoreCase("/"))
                resource = "/index.html";
            if (!method.equalsIgnoreCase("GET")) { // check if it is a GET request
                OutputStream os = client.getOutputStream(); // ask socket to give me the outputStream
                os.write(("HTTP/1.1 405 Method Not Allowed\r\n").getBytes());
                os.write(("\r\n").getBytes());
                os.write((method + " " + "not supported\r\n").getBytes()); 
                os.flush();
                os.close();
                return;
            } 
            else if (method.equalsIgnoreCase("GET")) {
                // check if resource exist
                for (int i = 0; i < dirArray.length; i++) {
                    String path = dirArray[i] + resource; // get the full path to the requested resource
                    File resourceExists = new File(path); // instantiate the File class to use its methods 
                    if (resourceExists.exists()) {
                        if (resource.endsWith(".png")) {
                            FileInputStream image = new FileInputStream(path); // to retrive file in bytes from file system with a given path
                            OutputStream os = client.getOutputStream(); // ask socket to give me the outputStream
                            os.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            os.write(("Content-Type: image/png\r\n").getBytes());
                            os.write(("\r\n").getBytes());
                            os.write((image.readAllBytes())); 
                            os.flush();
                            os.close();
                            image.close();
                            return;
                        } 
                        else
                        {
                            FileInputStream textFiles = new FileInputStream(path); // to retrive file in bytes from file system with a given path
                            OutputStream os = client.getOutputStream(); // ask socket to give me the outputStream
                            os.write(("HTTP/1.1 200 OK\r\n").getBytes());
                            os.write(("\r\n").getBytes());
                            os.write((textFiles.readAllBytes())); 
                            os.flush();
                            os.close();
                            textFiles.close();
                            return;
                        }
                    }   
                }
                OutputStream os = client.getOutputStream(); // ask socket to give me the outputStream
                os.write(("HTTP/1.1 404 Not Found\r\n").getBytes());
                os.write(("\r\n").getBytes());
                os.write((resource + " " + "not found").getBytes()); 
                os.flush();
                os.close();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
