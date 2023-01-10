import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServerClient {


        public static void main(String[] args) throws IOException {
            // Connect to the server
            Socket socket = new Socket("192.168.1.9", 8080);

            // Get the input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while(true){
                // Send a message to the server
                Scanner scan = new Scanner(System.in);
                String message = scan.nextLine();

                out.println(message);

                String response = in.readLine();
                // Read and process the server's response
                if(response == "no"){
                    break;
                }
                System.out.println("Response: " + response);  // prints "Response: ACK"
            }
            // Close the socket
            socket.close();
        }
    }



