import javafx.fxml.Initializable;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Server extends Thread{

    @Override
    public void run(){
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
    }
}
    class ClientHandler extends Thread {
        private Socket socket;
        public String hostip;
        public int gameMode;//Int to keep track of which phase the game is in.

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        public ClientHandler(){String hostip;}

        @Override
        public void run() {

                gameMode = OnlineController.getGameMode();
            System.out.println(gameMode);
                //first time initialising
                if (gameMode == 1) {
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
                    System.out.println(gameMode);
                } else if (gameMode == 2) { //First stage of the game. Upstart move is sent to client.
                    int[][] initialmap = OnlineController.getMap();
                    ArrayReturn arrayReturn = new ArrayReturn(initialmap);
                    try {
                        ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                        System.out.println(Arrays.deepToString(initialmap));
                        objectOut.writeObject(arrayReturn);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //The new map is set in the controller class since Play and Server runs in two different threads.
                    OnlineController.setMap(initialmap);
                    System.out.println(gameMode);
                } else if (gameMode == 3) {//When upstart move have been done then await response.
                    try {
                        ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
                        ArrayReturn boardRec = (ArrayReturn) inObject.readObject();
                        int[][] inputMap = boardRec.getArray();
                        System.out.println(Arrays.deepToString(inputMap));
                        OnlineController.setMap(inputMap);
                    } catch (RuntimeException | ClassNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(gameMode);

                } else if (gameMode == 4) {//Game have started. This is Output Stream.
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
                } else if (gameMode == 5) {// This is input stream.
                    try {
                        ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());
                        ArrayReturn boardRec = (ArrayReturn) inObject.readObject();
                        int[][] inputMap = boardRec.getArray();
                        System.out.println(Arrays.deepToString(inputMap));
                        OnlineController.setMap(inputMap);
                    } catch (RuntimeException | ClassNotFoundException | IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

