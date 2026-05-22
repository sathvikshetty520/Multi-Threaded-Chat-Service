import java.io.*;
import java.net.*;

class ClientHandler implements Runnable {

    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    // Constructor
    public ClientHandler(Socket socket,
                         DataInputStream dis,
                         DataOutputStream dos) {

        this.socket = socket;
        this.dis = dis;
        this.dos = dos;
    }

    @Override
    public void run() {

        String message;

        try {

            while (true) {

                // Read message from client
                message = dis.readUTF();

                // Send message to all clients
                for (ClientHandler client : ChatServer.clients) {

                    client.dos.writeUTF(message);
                }
            }

        } catch (IOException e) {

            System.out.println("Client disconnected");

        } finally {

            try {

                // Remove client from list
                ChatServer.clients.remove(this);

                // Close streams and socket
                dis.close();
                dos.close();
                socket.close();

                System.out.println("Connection closed");

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
