import javafx.fxml.Initializable;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Server extends Thread{

    @Override
    public void run(){
        //new Thread(new Runnable(){
            //public void run(){
        // Create a server socket that listens on port 8080
                ServerSocket serverSocket = null;
                try {
                    System.out.println("Connecting");
                    serverSocket = new ServerSocket(8080);
                    System.out.println("We got this far");
                    while(true){
                        // Wait for a client to connect
                        Socket socket = serverSocket.accept();
                        System.out.println("Client connected");
                        // Create a thread to handle the client
                        new Thread(new ClientHandler(socket)).start();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);

                }

    //}
    //}).start();
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
        public static boolean waiting = true;

        @Override
        public void run() {
            //first time initialising
           if(PlayOnline.turnCounter <= 2){
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
               waiting = false;
               ServerClient.firstTime = false;

           }else if(PlayOnline.turnCounter > 4){ //First stage of the game
               Board board = new Board();
               int[][] initialmap = board.getArray();
               ArrayReturn arrayReturn = new ArrayReturn(initialmap);
               try {
                   ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                   System.out.println(Arrays.deepToString(initialmap));
                   objectOut.writeObject(arrayReturn);
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
               PlayOnline.setMap(initialmap);
               waiting = false;
           }/*else if(PlayOnline.turnCounter > 4){//When server is called when game is past first stage.
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
            } */
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
