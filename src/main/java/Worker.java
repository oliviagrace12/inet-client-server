package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by oliviachisman on 4/7/19
 */
public class Worker extends Thread {

    private Socket socket;

    public Worker(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            // creating a reader to read the data coming in to the socket from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // creating a stream to write to the socket, sending data back to the client
            PrintStream out = new PrintStream(socket.getOutputStream());

            try {
                // reading a line from the socket (sent from the client) and printing to console
                String nameOrIpAddress = in.readLine();
                System.out.println("Looking up " + nameOrIpAddress);
                // the line read from the socket and the output stream from the socket are both passed in
                printRemoteAddress(nameOrIpAddress, out);
            } catch (IOException e) {
                // if any exceptions are thrown during the reading process, they will be printed here
                System.out.println("Server read error");
                e.printStackTrace();
            }
            // closing the connection with the client
            socket.close();
        } catch (IOException e) {
            // if any exceptions are thrown during the socket operations (getting the input and output streams,
            // closing the socket), they will be printed here
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void printRemoteAddress(String nameOrIpAddress, PrintStream out) {
        try {
            // writing things to the socket, to be received by the client
            out.println("Looking up " + nameOrIpAddress + "...");
            // looking up IP address of host from given name of host. If IP address is given,
            // simply checks that formatting is correct
            InetAddress machine = InetAddress.getByName(nameOrIpAddress);
            // printing out host name connected to found IP address
            out.println("Host name: " + machine.getHostName());
            // printing out IP address
            out.println("Host IP: " + toText(machine.getAddress()));
        } catch (UnknownHostException e) {
            // if lookup of host fails, exceptions will be printed here
            out.println("Failed in attempt to look up " + nameOrIpAddress);
        }
    }

    private String toText(byte[] ip) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ip.length; i++) {
            if (i > 0) {
                result.append(".");
            }
            result.append(0xff & ip[i]);
        }
        return result.toString();
    }
}
