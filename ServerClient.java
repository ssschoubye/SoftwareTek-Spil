import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;



public class ServerClient extends Thread {
    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;
    private ObjectOutputStream objectOut;
    private ObjectInputStream objectIn;

    public ServerClient(Socket socket){
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter = new PrintWriter(socket.getOutputStream(), true);
            this.objectOut = new ObjectOutputStream(socket.getOutputStream());
            this.objectIn = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            System.out.println("Error in Server constructor");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public void sendArray(int[][] array){
        try{
            ArrayReturn arrayReturn = new ArrayReturn(array);
            objectOut.writeObject(arrayReturn);
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


