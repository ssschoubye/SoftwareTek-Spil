import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;



public class ServerClient extends Thread{
    private InterThread interThread;

    public ServerClient(InterThread interThread){
        this.interThread = interThread;
    }
        public void run(){
        //while(interThread.getGameMode() < 5){
            int gameMode = interThread.getGameMode();
            //First time running this code it needs to be determined whether it is first time making a connection,
            // if the game is in its upstart fase or if regular play have started.
            System.out.println("Connecting");
            if(gameMode == 1){
                try{
                    InetAddress localHost = InetAddress.getLocalHost();
                    String client = localHost.getHostAddress();
                    //Grabs the IP address input from HostPrompt to create a connection the the host.
                    System.out.println(HostPrompt.IPinput);
                    Socket socket = new Socket(HostPrompt.IPinput, 8080);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    out.println(client);
                    //Respone will be recieved with the server.
                } catch (RuntimeException | IOException e) {
                    throw new RuntimeException(e);
                }
                //First time running the code the IP address for the connecting device will be sent to the host server.

                interThread.setGameMode(2);
            }else if(gameMode == 3){
                String IPAddress = OnlineController.getIPinput();
                Board board = new Board();
                int[][] initialmap = board.getArray();
                ArrayReturn arrayReturn = new ArrayReturn(initialmap);
                try {
                    Socket socket = new Socket(IPAddress, 8080);
                    ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println(Arrays.deepToString(initialmap));
                    objectOut.writeObject(arrayReturn);
                    objectOut.flush();
                    objectOut.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                //The new map is set in the controller class since Play and Server runs in two different threads.
                interThread.setMap(initialmap);
            }
        }

        //}
    }
//while(true){
     /*
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

                 */
//}



// Connect to the server
//Socket socket = new Socket("192.168.1.8", 8080);

            /*
            try{
                for (int i = 0; i <= 255; i++) {
                    host = "192.168.1.10";
                    InetAddress inetAddress = InetAddress.getByName(host);
                    if(inetAddress.isReachable(5000)){
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

                    }else{
                        System.out.println(host + "Cannot be reached.");
                    }
                }
            }catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
             */


