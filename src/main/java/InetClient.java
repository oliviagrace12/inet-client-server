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

    private static int port = 8080;

    public static void main(String[] args) {
        String serverName;

        if (args.length < 1) {
            serverName = "localhost";
        } else {
            serverName = args[0];
        }

        System.out.println("Olivia Chisman's Inet client, 1.8.\n");
        System.out.println("Using server: " + serverName + ", port: " + port);

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        try {
            String name;
            do {
                System.out.print("Enter a hostname or an IP address, (quit) to end: ");
                System.out.flush();
                name = in.readLine();
                if (!name.contains("quit")) {
                    getRemoteAddress(name, serverName);
                }
            } while (!name.contains("quit"));
            System.out.println("Cancelled by user request.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getRemoteAddress(String name, String serverName) {
        try {
            Socket socket = new Socket(serverName, port);
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintStream toServer = new PrintStream(socket.getOutputStream());
            toServer.println(name);
            toServer.flush();

            for (int i = 0; i < 3; i++) {
                String textFromServer = fromServer.readLine();
                if (textFromServer != null) {
                    System.out.println(textFromServer);
                }
            }

            socket.close();
        } catch (IOException e) {
            System.out.println("Socket error");
            e.printStackTrace();
        }
    }
}
