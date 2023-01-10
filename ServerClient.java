import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Scanner;



public class ServerClient {
        public static void main(String[] args) throws IOException {
            InetAddress localHost = InetAddress.getLocalHost();
            String client = localHost.getHostAddress();

            // Connect to the server
            //Socket socket = new Socket("192.168.1.8", 8080);
            String host = null;
            try{
                for (int i = 0; i <= 255; i++) {
                    host = "192.168.1." + i;
                    InetAddress inetAddress = InetAddress.getByName(host);
                    //if(inetAddress.isReachable(5000)){
                        System.out.println("Trying out" + inetAddress);
                        try (Socket socket = new Socket(host, 8080)) {
                            System.out.println(host + " is reachable");
                            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println(client);
                            String response = in.readLine();
                            if(response.equals("hey")){
                                break;
                            }else{
                                System.out.println("It aint right.");
                            }

                        } catch(ConnectException e){
                            System.out.println(host + " is not reachable");
                        }

                    //}else{
                        System.out.println(host + "Cannot be reached.");
                    //}
                }
            }catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            Socket socket = new Socket(host, 8080);


            // Get the input and output streams
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(client);
            while(true){
                // Send a message to the server
                System.out.println("What is your message?");
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



