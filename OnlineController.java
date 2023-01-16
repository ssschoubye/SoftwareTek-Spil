import javafx.application.Application;

import java.io.IOException;

public class OnlineController {


    public void onlineGame(boolean hostMode) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println(hostMode);
        if(hostMode == true){
            Server server = new Server();
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
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();



        }else{
            ServerClient client = new ServerClient();
            client.start();
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }

    }

}
