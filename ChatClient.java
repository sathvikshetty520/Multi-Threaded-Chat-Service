import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {

        try {

            // Connect to server
            Socket socket = new Socket("10.11.6.26", 1234);

            System.out.println("Connected to chat server");
            System.out.println("Type 'exit' to leave chat");

            // Input and Output streams
            DataInputStream dis =
                    new DataInputStream(socket.getInputStream());

            DataOutputStream dos =
                    new DataOutputStream(socket.getOutputStream());

            Scanner sc = new Scanner(System.in);

            // Thread for receiving messages
            Thread receiveThread = new Thread(() -> {

                try {

                    while (true) {

                        String msg = dis.readUTF();

                        System.out.println(msg);
                    }

                } catch (IOException e) {

                    System.out.println("Disconnected from server");
                }
            });

            // Start receive thread
            receiveThread.start();

            // Sending messages
            while (true) {

                String message = sc.nextLine();

                // Exit condition
                if (message.equalsIgnoreCase("exit")) {

                    System.out.println("Closing connection...");

                    dis.close();
                    dos.close();
                    socket.close();

                    break;
                }

                // Send message to server
                dos.writeUTF(message);
            }

            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}