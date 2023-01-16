import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameLoader {

    public int playerLoad;
    public int dimensionLoad;
    public String whiteLoad;
    public String blackLoad;
    public String board1Load;
    public String board2Load;
    public int turnCount;
    public int[][] gameMapLoad;


    public GameLoader() {
        try (BufferedReader lineReader = new BufferedReader(new FileReader("Boardsave.txt"))) {
            String line;

            String gridString = "";


            int lineCount = 0;
            while ((line = lineReader.readLine()) != null && lineCount < 8) {
                if (lineCount == 0) {
                    playerLoad = Integer.parseInt(line);
                } else if (lineCount == 1) {
                    dimensionLoad = Integer.parseInt(line);
                } else if (lineCount == 2) {
                    whiteLoad = line;
                } else if (lineCount == 3) {
                    blackLoad = line;
                } else if (lineCount == 4) {
                    board1Load = line;
                } else if (lineCount == 5) {
                    board2Load = line;
                } else if (lineCount == 6) {
                    gridString = line;
                } else if (lineCount == 7) {
                    turnCount = Integer.parseInt(line);
                }
                lineCount++;
            }


            gameMapLoad = new int[dimensionLoad][dimensionLoad];

            for (int i = 0; i < dimensionLoad; i++) {
                for (int j = 0; j < dimensionLoad; j++) {
                    int pos = dimensionLoad * i + j;
                    gameMapLoad[j][i] = gridString.charAt(pos) - '0';
                }
            }
            /*
            for (int i = 0; i < dimension; i++) {
                System.out.println();
                for (int j = 0; j < dimension; j++) {
                    System.out.print(grid[i][j]);
                }
            }*/


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
