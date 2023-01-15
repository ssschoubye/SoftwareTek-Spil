import java.io.*;
import java.net.*;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;



public class ServerClient {
    public static boolean firstTime = true;
        public static void runServerClient(int dim) throws IOException, ClassNotFoundException {
            //First time running this code it needs to be determined whether it is first time making a connection,
            // if the game is in its upstart fase or if regular play have started.
            if(firstTime == true){
                //First time running the code the IP address for the connecting device will be sent to the host server.
                InetAddress localHost = InetAddress.getLocalHost();
                String client = localHost.getHostAddress();
                //Grabs the IP address input from HostPrompt to create a connection the the host.
                Socket socket = new Socket(HostPrompt.IPinput, 8080);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(client);
                firstTime = false;
                //Then game starts.
                PlayOnline.turnCounter = 3;
                Server.serverStart();
            }else{
                if(PlayOnline.turnCounter == 2){ //If it isnt the first time then we move to upstart fase.
                    //while(true){
                    String IPAddress = ClientHandler.getHostIP();
                    Socket socket = new Socket(IPAddress, 8080);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    ObjectInputStream inObject = new ObjectInputStream(socket.getInputStream());

                    ArrayReturn boardRec = (ArrayReturn) inObject.readObject();
                    int[][] inputMap = boardRec.getArray();
                    System.out.println(Arrays.deepToString(inputMap));
                    PlayOnline play = new PlayOnline();
                    play.gameStart(dim);
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
                }else{

                }
            }

        }
    }


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


