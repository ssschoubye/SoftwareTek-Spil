import java.io.*;
import java.net.*;
import java.util.Arrays;
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
        public String hostip;
        public int turnCounter = 1;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            while (turnCounter < 4) {
                if (turnCounter == 1) {
                    try {
                        // Get the input and output streams
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                        // Read and process client messages
                        while (true) {
                            //Modtager bekræftelse besked.
                            hostip = in.readLine();
                            System.out.println("Ip modtaget" + hostip);

                            if (hostip == null) {
                                break;
                            }else{
                                Board board = new Board();
                                int[][] initialmap = board.getArray();
                                //int[][] initialmap = {{1, 2, 3, 4},{1, 2, 3, 4}};
                                ArrayReturn arrayReturn = new ArrayReturn(initialmap);
                                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                                System.out.println(Arrays.deepToString(initialmap));
                                objectOut.writeObject(arrayReturn);
                                //out.println(hostip);
                                turnCounter = 3;
                                objectOut.flush();
                                socket.close();
                                break;
                            }
                            //System.out.println(hostip);
                            //Scanner scan = new Scanner(System.in);
                            //String message1 = scan.nextLine();

                            // Process the message

                            // Send a response to the client
                            //out.println(message1);
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

                } else if (turnCounter == 3) {

                }


            }
        }
    }
