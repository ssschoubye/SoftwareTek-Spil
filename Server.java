import javafx.fxml.Initializable;

import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Server implements Serializable{
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    public Server(ServerSocket serverSocket){ //Constructor for the server class.
        try{
            this.serverSocket = serverSocket; //The serverSocket is set to the serverSocket that is passed to the constructor.
            this.socket = serverSocket.accept(); //The socket is set to the socket that is accepted by the serverSocket.
            this.objectOut = new ObjectOutputStream(socket.getOutputStream()); //The objectOut is set to the ObjectOutputStream that is created from the socket's output stream.
            this.objectIn = new ObjectInputStream(socket.getInputStream()); //The objectIn is set to the ObjectInputStream that is created from the socket's input stream.
        }catch (IOException e){
            System.out.println("Error in Server constructor");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void sendArray(int[][] array){ //Method for sending an array to the client.
        try{
            ArrayReturn arrayReturn = new ArrayReturn(array); //An ArrayReturn object is created from the array that is passed to the method.
            System.out.println(Arrays.deepToString(array));
            objectOut.writeObject(arrayReturn); //The arrayReturn object is written to the objectOut.
            objectOut.flush(); //The objectOut is flushed.

        }catch (IOException e){
            System.out.println("Error in sendArray");
            e.printStackTrace();
            closeEveryThing(socket, objectOut, objectIn);

        }
    }
    public void closeEveryThing(Socket socket, ObjectOutputStream objectOut, ObjectInputStream objectIn){
        //Method for closing the socket, objectOut, and objectIn objects.
        try{
            socket.close(); //The socket is closed.
            objectOut.close(); //The objectOut is closed.
            objectIn.close(); //The objectIn is closed.
        }catch (IOException e){
            System.out.println("Error in closeEverything");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void recieveArray(){ //Method for recieving an array from the client.
        new Thread(new Runnable() {
            @Override
            public void run() {//This is the thread that is used to recieve the array from the client.
                while(socket.isConnected()){//This while loop is used to keep the thread running until the socket is closed.
                    try{
                        ArrayReturn arrayReturn = (ArrayReturn) objectIn.readObject(); //The unknown object recieved is stored in an ArrayReturn object.
                        int[][] array = arrayReturn.getArray(); //The array is set to the array that is returned from the getArray method in the ArrayReturn class.
                        System.out.println(Arrays.deepToString(array));
                        PlayOnlineHost.setMap(array); //The array is set to the map in the PlayOnlineHost class.
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

