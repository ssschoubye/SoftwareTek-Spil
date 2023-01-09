import java.io.*;
import java.net.*;

public class ServerClient {


        public static void main(String[] args) throws IOException {
            // Connect to the server
            Socket socket = new Socket("localhost", 8080);

            // Get the input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send a message to the server
            out.println("HELLO");

            // Read and process the server's response
            String response = in.readLine();
            System.out.println("Response: " + response);  // prints "Response: ACK"

            // Close the socket
            socket.close();
        }
    }



