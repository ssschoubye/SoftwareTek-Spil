public class InterThread {

    static int[][] map;
    static int gameMode = 1;
    public synchronized static void setMap(int[][] inputMap){
        map = inputMap;
        //PlayOnlineHost.setMap(map); Might have to revise code later.
    }

    public synchronized static int[][] getMap() {
        return map;
    }
    public synchronized static void setGameMode(int Mode){gameMode = Mode;
    }
    public synchronized static int getGameMode() {
        return gameMode;
    }
}
