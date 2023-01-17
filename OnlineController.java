import java.io.IOException;

public class OnlineController {

    static String IPinput;

    public void onlineGame(boolean hostMode, boolean firstTime) throws IOException, ClassNotFoundException, InterruptedException {
        InterThread interThread = new InterThread();
        IPinput = HostPrompt.getIPinput();
        System.out.println(hostMode);
        if(hostMode == true){
            Server server = new Server(interThread);
            server.start();
            System.out.println("hey");
            for(int i = 0; i < 10; i++){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("You have " + (10 - i) + " seconds to join");
            }
            HostPrompt.stageClose();
            //if(firstTime == true){
                PlayOnlineHost playOnlineHost = new PlayOnlineHost();
                playOnlineHost.gameStart();
            //}

        }else{
            ServerClient client = new ServerClient(interThread);
            client.start();
            if(firstTime == true){
                PlayOnlineClient playOnlineClient = new PlayOnlineClient();
                playOnlineClient.gameStart();
            }

        }

    }
    static int[][] map;
    static int gameMode = 1;
    public synchronized static void setMap(int[][] inputMap){
    map = inputMap;
    PlayOnlineHost.setMap(map); //Might have to revise code later.
    }

    public synchronized static int[][] getMap() {
        return map;
    }
    public synchronized static void setGameMode(int Mode){
        gameMode = Mode;
    }
    public synchronized static int getGameMode() {
        return gameMode;
    }
    public static String getIPinput(){
        return(IPinput);
    }
}
