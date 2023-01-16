import java.io.IOException;

public class OnlineController {

    public void onlineGame(boolean hostMode) throws IOException, ClassNotFoundException {

        if(hostMode == true){
            Server server = new Server();
            server.start();
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }else{
            ServerClient client = new ServerClient();
            client.runServerClient();
            PlayOnline playOnline = new PlayOnline();
            playOnline.gameStart();
        }

    }

}
