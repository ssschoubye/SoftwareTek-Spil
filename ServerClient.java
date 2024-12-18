import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

/////////////////////////////////////////////////////////////////
///                      Server                               ///
///               by Søren Sehested Schoubye s224756          ///
///                                                           ///
/////////////////////////////////////////////////////////////////
//This class is very similar to the Server Class but is used to control the client side of the game.
//Relevant change to the code is marked with a comment.

public class ServerClient implements Serializable {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    public ServerClient(Socket socket){ //Constructor for the ServerClient class.
        try{
            this.socket = socket; //The socket is set to the socket that is passed to the constructor.
            this.objectOut = new ObjectOutputStream(socket.getOutputStream()); //The objectOut is set to the ObjectOutputStream that is created from the socket's output stream.
            this.objectIn = new ObjectInputStream(socket.getInputStream()); //The objectIn is set to the ObjectInputStream that is created from the socket's input stream.
        }catch (IOException e){
            System.out.println("Error in Server constructor");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void sendArray(int[][] array){
        try{
            ArrayReturn arrayReturn = new ArrayReturn(array); //An ArrayReturn object is created from the array that is passed to the method.
            System.out.println(Arrays.deepToString(array));
            objectOut.writeObject(arrayReturn); //The arrayReturn object is written to the objectOut.
            objectOut.flush();
        }catch (IOException e){
            System.out.println("Error in sendArray");
            e.printStackTrace();
            closeEveryThing(socket, objectOut, objectIn);


        }
    }
    public void closeEveryThing(Socket socket, ObjectOutputStream objectOut, ObjectInputStream objectIn){
        try{
            socket.close();
            objectOut.close();
            objectIn.close();
        }catch (IOException e){
            System.out.println("Error in closeEverything");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void recieveArray(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(socket.isConnected()){
                    try{
                        ArrayReturn arrayReturn = (ArrayReturn) objectIn.readObject();
                        int[][] array = arrayReturn.getArray();
                        System.out.println(Arrays.deepToString(array));
                        PlayOnlineClient.setMap(array);
                    }catch (IOException | ClassNotFoundException e){
                        System.out.println("Error in recieveArray");
                        e.printStackTrace();
                        closeEveryThing(socket, objectOut, objectIn);
                        break;
                    }
                }

            }
        }).start();
    }
}


