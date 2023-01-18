import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameLoader {

// A class for reading the saved game-data. All needed parameters are instantiated.
    public int playerLoad;
    public int dimensionLoad;
    public String whiteLoad;
    public String blackLoad;
    public String board1Load;
    public String board2Load;
    public int turnCount;
    public int[][] gameMapLoad;

// A constructor for the class.
    public GameLoader() {
// BufferedReader is used to read one line of text at a time.
        try (BufferedReader lineReader = new BufferedReader(new FileReader("Boardsave.txt"))) {
            String line;

            String gridString = "";

// Various if-statements are issued to turn each line of text into the desired data type.
// The instantiated objects are overridden with the value from the String "line".
// If the value is needed in the data type "Integer", the function "parseInt" is used.
            int lineCount = 0;
// A while-loop is used to read all lines of text in the document.
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
// After one line has been read, the while-loop moves to the next line.
                lineCount++;
            }

// The 2D array is set to the size of the previously saved game.
            gameMapLoad = new int[dimensionLoad][dimensionLoad];
// A for-loop nested in a for-loop is used to calculate which position of the 2D array equates to which position of the String of integers.
            for (int i = 0; i < dimensionLoad; i++) {
                for (int j = 0; j < dimensionLoad; j++) {
                    int pos = dimensionLoad * i + j;
// charAt is used to set the value of the given field in the 2D array to the desired character.
                    gameMapLoad[j][i] = gridString.charAt(pos) - '0';
                }
            }
// A for-loop to test if this implementation worked as intended *ONLY FOR TESTING*
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
