package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by oliviachisman on 4/7/19
 */
public class InetClient {

    // setting port to 8080. Must use same port as server
    private static int port = 8080;

    public static void main(String[] args) {
        String serverName;

        // can pass in a server name with program arguments. If none provided, the default is "localhost"
        if (args.length < 1) {
            serverName = "localhost";
        } else {
            serverName = args[0];
        }

        System.out.println("Olivia Chisman's Inet client, 1.8.\n");
        System.out.println("Using server: " + serverName + ", port: " + port);

        // creating an input stream whose source is the keyboard
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String name;
            // program loop in which input is taken from the user, processed, and feedback is given back
            // to the user. Loop will exit if the user enters the string "quit" into the input stream
            // through the keyboard
            do {
                // prompting the user to enter input into the input stream
                System.out.print("Enter a hostname or an IP address, (quit) to end: ");
                System.out.flush();
                // reading user input from input stream
                name = in.readLine();
                // checking to see if user has entered program exit command
                if (!name.contains("quit")) {
                    getRemoteAddress(name, serverName);
                }
            } while (!name.contains("quit"));
            System.out.println("Cancelled by user request.");
        } catch (IOException e) {
            // catches and prints any exceptions thrown by reading from the input stream
            e.printStackTrace();
        }
    }

    private static void getRemoteAddress(String name, String serverName) {
        try {
            // creating a socket with the specified port and server name with which to connect to the server.
            // The port should be the same as that of the server in order to connect to the server
            Socket socket = new Socket(serverName, port);
            // creating a reader to read the data coming into the socket (coming from the server)
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // creating a stream to write to the socket (to send data to the server)
            PrintStream toServer = new PrintStream(socket.getOutputStream());
            // writing the name to the stream, which will be read by the server
            toServer.println(name);
            toServer.flush();

            // reading up to 3 lines from the socket (coming from the server) and printing them to the
            // console for the user to see
            for (int i = 0; i < 3; i++) {
                String textFromServer = fromServer.readLine();
                if (textFromServer != null) {
                    System.out.println(textFromServer);
                }
            }

            // closing the connection to the server
            socket.close();
        } catch (IOException e) {
            // catches any exceptions thrown during reading from or writing to the socket
            System.out.println("Socket error");
            e.printStackTrace();
        }
    }
}
