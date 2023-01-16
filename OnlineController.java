import java.io.IOException;

public class OnlineController {

    public void onlineGame(boolean hostMode) throws IOException, ClassNotFoundException {

        if(hostMode == true){
            Server server = new Server();
            server.start();
            while(true){
                if(ClientHandler.waiting == false){
                    PlayOnline playOnline = new PlayOnline();
                    playOnline.gameStart();
                    break;
                }
            }


        }else{
            ServerClient client = new ServerClient();
            client.runServerClient();
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }

    }

}
