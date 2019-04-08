package main.java;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InetServer {

    public static void main(String[] args) throws IOException {
        int qLen = 6;
        // defining the port on which to start the server socket
        int port = 8080;

        // creating a server socket
        ServerSocket serverSocket = new ServerSocket(port, qLen);

        // letting the user know that the server has started, and on which port
        System.out.println("Olivia Chisman's Inet server 1.8 starting up, listening to port " + port + ".\n");

        // program runs in infinite loop, unless exception is thrown
        while (true) {
            // if any clients try to connect, the serverSocket will accept their connections and return a socket
            Socket socket = serverSocket.accept();
            // this socket is passed to a new thread that is spawned (an instance of the Worker class) and start
            // is called on this thread, kicking off the processes in the Worker class run method.
            new Worker(socket).start();
        }
    }
}
