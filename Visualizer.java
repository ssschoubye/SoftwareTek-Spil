import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import javafx.scene.layout.GridPane;



public class Visualizer extends Application {

    static int width;
    static int height;
    static int turn = (int)(Math.random()*2)+1;

    static int firstStartingPlayer = (int)(Math.random()*2)+1;

    static int gameNumber = 1;

    static int turnCounter =1;
    String whiteImage = "Images/whitePiece.png";
    String blackImage = "Images/blackPiece.png";
    String markerImage = "Images/marker.png";
    String backImage1 = "Images/backgroundSkins/chess1.png";
    String backImage2 = "Images/backgroundSkins/chess2.png";

    String appIcon = "Images/reversiIcon.png";
    Label showTurn = new Label(turnColor(turn)+"'s turn");

    Rectangle2D screenBounds = Screen.getPrimary().getBounds();




    public void gameStart(int inwidth, int inheight){
        width = inwidth;
        height = inheight;
        Stage stage = new Stage();
        start(stage);
    }



    @Override
    public void start(Stage primaryStage) {

        Board game = new Board(width,height);
        game.initialize();
        turn = game.startingPlayer(gameNumber,firstStartingPlayer);
        showTurn.setText(turnColor(turn%2+1)+"'s turn");



        // Create two GridPanes, which will function as the playing board and as the overall current
        // status of the game (score, time, player turn, announcements...)
        // and adds them to a HBox

        GridPane board = new GridPane();



        // Create 2D array of buttons, which functions as the individual cells on the playing board
        Button[][] cells = new Button[width][height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Button();
                cells[i][j].getStylesheets().add(Objects.requireNonNull(getClass().getResource("boardButtons.css")).toExternalForm());
                board.add(cells[i][j],i,j,1,1);

                final int ii = i;
                final int jj = j;



                updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);



                // Create an event handler for "on action"
                cells[i][j].setOnAction(event -> {
                    if (game.placePiece(ii,jj,turn)){
                        turnCounter++;
                        //Switches player turn
                        if(turnCounter==3){
                            showTurn.setText(turnColor(turn)+"'s turn");
                            turn = Board.turnSwitch(turn);
                        }

                        if (turnCounter>4){
                            showTurn.setText(turnColor(turn)+"'s turn");
                            turn = Board.turnSwitch(turn);

                            //Checks for legal spots
                            if (!game.legalSpots(turn)) {
                                if(!game.legalSpots(Board.turnSwitch(turn))){
                                    System.out.println("No more possible moves \n    game over");
                                    //Save value for ending game
                                    WinPage win = new WinPage();
                                    turnCounter = 1;
                                    gameNumber++;
                                    try{
                                        primaryStage.close();
                                        win.winStart(game);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }

                                } else{
                                    showTurn.setText(turnColor(turn)+"'s turn");
                                    System.out.println("\n" + turn + " has no possible moves");
                                    turn = Board.turnSwitch(turn);

                                    //no move possible for current player
                                }

                            }
                        }

                        updateGridpane(primaryStage, game, board, blackImage, whiteImage, markerImage);




                    }

                });

                cells[i][j].prefHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));
                cells[i][j].prefWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10.0));

            }
        }

        Image icon = new Image(appIcon);
        showTurn.setFont(Font.font("Comic Sans MS", 24));
        primaryStage.setTitle("Reversi");

        // Forsøg på at lave et sidepanel med nødvendige knapper og tekst:
        Label p1_points = playerpoints(1);
        Label p2_points = playerpoints(2);
        Button skins = new Button("skins");
        Button restart = new Button("restart");
        Button settings = new Button("settings");


        VBox sidepanel = new VBox();
        sidepanel.getChildren().addAll(showTurn,p1_points,p2_points,skins,restart,settings);
        sidepanel.setSpacing(50);
        sidepanel.setPadding(new Insets(50));


        HBox hbox = new HBox();
        hbox.getChildren().addAll(sidepanel,board);







        Scene scene = new Scene(hbox, screenBounds.getWidth()/2, screenBounds.getHeight()/2);

        primaryStage.setScene(scene);
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(true);
        primaryStage.show();






    }

    private void updateGridpane(Stage primaryStage, Board game, GridPane board, String whiteImage, String blackImage, String markerImage) {

        board.getChildren().removeIf(node -> node instanceof ImageView);
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        Image markingImage = new Image(markerImage);

        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);



        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                ImageView back;

                if ( (x+y)%2==1){
                    back = new ImageView(backGround1);
                } else  {
                    back = new ImageView(backGround2);
                }


                board.add(back, x, y);
                back.setMouseTransparent(true);
                back.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), width +2));
                back.fitHeightProperty().bind(Bindings.divide(primaryStage.heightProperty(), height + 2));
                back.setPreserveRatio(true);




                if (game.map[x][y] == 1) {
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    board.add(whitePiece, x, y);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                } else if (game.map[x][y] == 2) {
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    board.add(blackPiece, x, y);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                } else if (game.map[x][y] == 3 || game.map[x][y] == 4) {
                    ImageView marker = new ImageView(markingImage);
                    board.add(marker, x, y);
                    marker.setMouseTransparent(true);
                    marker.fitWidthProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                    marker.fitHeightProperty().bind(Bindings.divide(primaryStage.widthProperty(), 10));
                }
            }
        }
    }

    public String turnColor(int turn){
        if(turn==1){
            return "White";
        } else if(turn==2){
            return "Black";
        }else return null;
    }
    public Label playerpoints (int player) {
        String color;
        if (player == 1) {
            color = "white";
        } else color = "black";
        Label label = new Label(color + " x points");
        return label;
    }

}