import javafx.application.Application;

import java.io.IOException;

public class OnlineController {


    public void onlineGame(boolean hostMode) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println(hostMode);
        if(hostMode == true){
            Server server = new Server();
            server.start();
            System.out.println("hey");
            for(int i = 0; i < 20; i++){
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

                System.out.println("You have " + (10 - i) + " seconds to join");
            }
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }else{
            ServerClient client = new ServerClient();
            client.start();
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }

    }
    static int[][] map;
    static int gameMode = 1;
    public synchronized static void setMap(int[][] inputMap){
    map = inputMap;
    PlayOnline.setMap(map); //Might have to revise code later.
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
}
