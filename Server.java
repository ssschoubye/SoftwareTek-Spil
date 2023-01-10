import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        // Create a server socket that listens on port 8080
        ServerSocket serverSocket = new ServerSocket(8080);

        while (true) {
            // Accept an incoming client connection
            Socket clientSocket = serverSocket.accept();

            // Create a new thread to handle the client
            new ClientHandler(clientSocket).start();
        }
    }
}
    class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                // Get the input and output streams
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                // Read and process client messages
                while (true) {
                    String message = in.readLine();

                    if (message == null) {
                        break;
                    }
                    System.out.println(message);

                    Scanner scan = new Scanner(System.in);
                    String message1 = scan.nextLine();

                    // Process the message
                    // ...

                    // Send a response to the client
                    out.println(message1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
