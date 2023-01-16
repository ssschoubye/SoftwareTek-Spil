public class OnlineController {

    public void onlineGame(){
        Server server = new Server();
        server.start();
        PlayOnline playOnline = new PlayOnline();
        playOnline.gameStart();
    }

}
