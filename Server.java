import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;

public class Server {
    public static void serverStart() throws IOException {
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
        public static int turnCounter = 1;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        public ClientHandler(){String hostip;}

        @Override
        public void run() {
            //first time initialising
           if(PlayOnline.turnCounter < 4){
               try { //At first connection is made and the IP address of the client is saved. This will be used later on.
                   InetAddress localHost = InetAddress.getLocalHost();
                   String client = localHost.getHostAddress();
                   BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                   //IP address of connecting client is saved.
                   hostip = in.readLine();
                   System.out.println("Ip modtaget" + hostip);
                   out.println(client);
                   //Server closes so the game can start.
                   socket.close();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }

               ServerClient.firstTime = false;
               //Play is started with dimensions from dimensionprompt.
               PlayOnline play = new PlayOnline();
               play.gameStart(DimensionPrompt.dim.x);

           }else if(PlayOnline.turnCounter > 4){ //When server is called when game is past first stage.
               Board board = new Board();
               int[][] initialmap = board.getArray();
               ArrayReturn arrayReturn = new ArrayReturn(initialmap);
               ObjectOutputStream objectOut;
               try {
                   objectOut = new ObjectOutputStream(socket.getOutputStream());
                   System.out.println(Arrays.deepToString(initialmap));
                   objectOut.writeObject(arrayReturn);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
        }
        public static void stopServer(){
            turnCounter = 4;
        }
        public static String getHostIP(){

            ClientHandler client = new ClientHandler();
            String IPAddress = client.hostip;
            return IPAddress;
        }
    }
