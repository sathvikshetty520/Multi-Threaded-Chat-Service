import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {

    // Store all connected clients
    static Vector<ClientHandler> clients = new Vector<>();// stores all connected clients

    public static void main(String[] args) {

        try {

            // Server starts on port 1234
            ServerSocket serverSocket = new ServerSocket(1234);//creates a socket

            System.out.println("Server started... Waiting for clients");

            while (true) {

                // Accept client connection
                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                // Input and Output streams
                DataInputStream dis =
                        new DataInputStream(socket.getInputStream());

                DataOutputStream dos =
                        new DataOutputStream(socket.getOutputStream());

                // Create client handler
                ClientHandler client =
                        new ClientHandler(socket, dis, dos);

                // Add client to list
                clients.add(client);

                // Create thread
                Thread t = new Thread(client);

                // Start thread
                t.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}