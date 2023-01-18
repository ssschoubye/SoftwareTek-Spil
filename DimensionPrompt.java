import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

//Class for the window where dimensions, skins and game mode is chosen
public class DimensionPrompt {

    //Setting paths for the different images used
    static String backImage1 = "Images/Background/green1.png";
    static String backImage2 = "Images/Background/green2.png";

    static String whiteImage = "Images/WhitePieces/whitePiece1.png";
    static String blackImage = "Images/BlackPieces/blackPiece1.png";


    public int x;
    public int y;
    public String white;
    public String black;
    public String back1;
    public String back2;
    public static int gamemode=0;

    private static int size=8;
    public DimensionPrompt(){
        x= 0;
        y = 0;
        white = whiteImage;
        black = blackImage;
        back1 = backImage1;
        back2 = backImage2;
        //gamemode = gamemode2;

    }
    static DimensionPrompt dim = new DimensionPrompt();
    static Scene scene;

    static {
        try {
            scene = new Scene(FXMLLoader.load(Objects.requireNonNull(DimensionPrompt.class.getResource("dimension.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void start1() {
        Stage stage = new Stage();
        start(stage);

        dim.x = size;
        dim.y = size;
        dim.white = whiteImage;
        dim.black = blackImage;
        dim.back1 = backImage1;
        dim.back2 = backImage2;


    }

    public static void start2(PlayAlone playAlone) {
        playAlone.start(new Stage());
        dim.x = size;
        dim.y = size;
        dim.white = whiteImage;
        dim.black = blackImage;
        dim.back1 = backImage1;
        dim.back2 = backImage2;

    }

    @FXML
    public static void start(Stage dimStage) {

        //Setting the app icon
        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);
        dimStage.getIcons().add(icon);

        //Setting the title for the application
        dimStage.setTitle("Reversi Advanced");

        //Removing the default title bar
        dimStage.initStyle(StageStyle.UNDECORATED);

        //Filling the preview pane
        fillPreviewPane(size);

        //Setting and showing the scene and stage
        dimStage.setScene(scene);
        dimStage.showAndWait();

    }

    //Initializing the preview pane that shows chosen skins
    @FXML
    static
    Pane previewPane=(Pane)scene.lookup("#previewPane");

    //Initializing the label that shows dimension chosen
    @FXML
    static
    Label previewPaneLabel= (Label)scene.lookup("#previewPaneLabel");

    //Initializing the gridpane that the game is loaded onto
    //This gridpane is later loaded on to the preview pane
    static
    GridPane previewGrid = new GridPane();

    //Method for filling the preview pane
    //Largely the same function as the "updateGridPane" used in the "Play"-classes
    //Therefore not going as much into detail about the statements
    private static void fillPreviewPane(int size) {

        //Preview pane label is set to show the appropriate size
        previewPaneLabel.setText(size+"x"+size);

        //Removes previous images from the gridpane
        previewGrid.getChildren().removeIf(node -> node instanceof ImageView);

        //Removes previous gridpane from the preview pane
        previewPane.getChildren().remove(previewGrid);

        //Setting the images for later use
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);

        //Iterating through the gridpane
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                //Setting the two background images
                ImageView back;
                if ((i + j) % 2 == 1) {
                    back = new ImageView(backGround1);
                } else {
                    back = new ImageView(backGround2);
                }
                previewGrid.add(back, i, j);
                back.setMouseTransparent(true);
                back.setPreserveRatio(true);
                back.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                back.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));

                //Setting the two rightmost pieces in the middle of the board to be white
                if ((i == size/2 && j == size/2-1)||(i == size/2 && j == size/2)){
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    previewGrid.add(whitePiece,i,j);
                    whitePiece.setPreserveRatio(true);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));

                //Setting the two leftmost pieces in the middle to be black
                } else if ((i == size/2-1 && j == size/2-1)||(i == size/2-1 && j == size/2)){
                    ImageView blackPiece = new ImageView(blackPieceImage);
                    previewGrid.add(blackPiece,i,j);
                    blackPiece.setPreserveRatio(true);
                    blackPiece.setMouseTransparent(true);
                    blackPiece.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                    blackPiece.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));
                }

            }
        }
        //Adding the grid to the preview pane
        previewPane.getChildren().add(previewGrid);

    }

    //////////////////////////////////////////////////////////////
    ///                          Skins                         ///
    //////////////////////////////////////////////////////////////
    // All the methods here simply change the path of the skins to their respective skins

    //------------------Background skins-------------------------

    public void woodChoice(){
        backImage1 = "Images/Background/wood1.png";
        backImage2 = "Images/Background/wood2.png";
        fillPreviewPane(size);
    }
    public void blueChoice(){
        backImage1 = "Images/Background/blue1.png";
        backImage2 = "Images/Background/blue2.png";
        fillPreviewPane(size);
    }
    public void brownChoice(){
        backImage1 = "Images/Background/brown1.png";
        backImage2 = "Images/Background/brown2.png";
        fillPreviewPane(size);

    }
    public void bubbleGumChoice(){
        backImage1 = "Images/Background/bubblegum1.png";
        backImage2 = "Images/Background/bubblegum2.png";
        fillPreviewPane(size);

    }
    public void glassChoice(){
        backImage1 = "Images/Background/glass1.png";
        backImage2 = "Images/Background/glass2.png";
        fillPreviewPane(size);    }
    public void greenChoice(){
        backImage1 = "Images/Background/green1.png";
        backImage2 = "Images/Background/green2.png";
        fillPreviewPane(size);

    }

    //---------------------white skins-------------------------
    public void white1(){
        whiteImage = "Images/WhitePieces/whitePiece1.png";
        fillPreviewPane(size);
    }
    public void white2(){
        whiteImage = "Images/WhitePieces/whitePiece2.png";
        fillPreviewPane(size);
    }
    public void white3(){
        whiteImage = "Images/WhitePieces/whitePiece3.png";
        fillPreviewPane(size);
    }
    public void white4(){
        whiteImage = "Images/WhitePieces/whitePiece4.png";
        fillPreviewPane(size);
    }
    public void white5(){
        whiteImage = "Images/WhitePieces/whitePiece5.png";
        fillPreviewPane(size);
    }


    //---------------------black skins-------------------------

    public void black1(){
        blackImage = "Images/BlackPieces/blackPiece1.png";
        fillPreviewPane(size);
    }
    public void black2(){
        blackImage = "Images/BlackPieces/blackPiece2.png";
        fillPreviewPane(size);
    }
    public void black3(){
        blackImage = "Images/BlackPieces/blackPiece3.png";
        fillPreviewPane(size);
    }
    public void black4(){
        blackImage = "Images/BlackPieces/blackPiece4.png";
        fillPreviewPane(size);
    }
    public void black5(){
        blackImage = "Images/BlackPieces/blackPiece5.png";
        fillPreviewPane(size);
    }




    //////////////////////////////////////////////////////////////
    ///                    Title bar layout                    ///
    ///              Same code as for the Menu.java            ///
    ///       Further explanations of code can be sen there    ///
    //////////////////////////////////////////////////////////////
    @FXML
    HBox titlebar;

    private double windowX = 0;
    private double windowY = 0;


    @FXML
    private void titleBarDragged(MouseEvent event) {
        Stage stage = (Stage) titlebar.getScene().getWindow();
        stage.setY(event.getScreenY() - windowY);
        stage.setX(event.getScreenX() - windowX);

    }

    @FXML
    private void titleBarPressed(MouseEvent event) {
        windowX = event.getSceneX();
        windowY = event.getSceneY();

    }


    @FXML
    private void onExitButtonClick() {
        Platform.exit();
    }

    @FXML
    Button minimize;

    @FXML
    public void OnMinimizeButtonClick() {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    //////////////////////////////////////////////////////////////
    ///                     Dimension buttons                  ///
    ///             By Søren Sehested Schoubye s224756         ///
    //////////////////////////////////////////////////////////////

    //These functions change the dimensions of the board to their respective value
    public void setDimensions4(){ //The method is called through the onAction method of the button in the FXML file.

        dim.x = 4;
        dim.y = 4;
        size = 4;
        fillPreviewPane(size); //The preview pane is updated to reflect the new dimensions
    }
    //The same goes for the other methods
    public void setDimensions8(){

        dim.x = 8;
        dim.y = 8;
        size = 8;
        fillPreviewPane(size);
    }
    public void setDimensions12(){

        dim.x = 12;
        dim.y = 12;
        size = 12;
        fillPreviewPane(size);
    }




    //////////////////////////////////////////////////////////////
    ///                    Play initialising                   ///
    ///                 By Søren Sehested Schoubye s224756     ///
    //////////////////////////////////////////////////////////////
    //Usage of buttons and onAction methods have already been explained in the menu class.
    //These methods initialize the game in the three different gamemodes.
    //They are similar except for the gamemode variable which is used to determine which gamemode is being played.
    @FXML
    private Button playAlone;
    PlayAlone game = null;//The game object is created here, but is not initialised until the play button is pressed.
    @FXML
    private void playAloneAction(){
        gamemode = 1; //Sets the gamemode to play alone.
        Stage stage = (Stage) playAlone.getScene().getWindow(); //Closes the current stage as pri
        stage.close();
        game = new PlayAlone(); //Initialises the game object
        PlayAlone.gameNumber = 1; //Spcifies that this is the first game.
        PlayAlone.firstStartingPlayer = (int) (Math.random() * 2) + 1; //Randomly chooses which color starts the game.
            game.gameStart(dim.x, dim.y); //Starts the game.
    }



    @FXML
    private Button playAI;

    @FXML
    private void playAIAction(){
        gamemode=2; //Game mode 2 is the AI gamemode
        Stage stage = (Stage) playAI.getScene().getWindow();
        stage.close();
        PlayAI game = new PlayAI();
        PlayAI.gameNumber = 1;
            game.gameStart(dim.x, dim.y);

    }

    @FXML
    private Button playOnlineHost;
    @FXML
    private void playOnlineAction() throws IOException {
        gamemode = 3; //Game mode 3 is the online gamemode
        Stage stage = (Stage) playOnlineHost.getScene().getWindow();
        stage.close();
        HostPrompt game = new HostPrompt();
            game.runHostPrompt(dim.x, dim.y);
    }

    //////////////////////////////////////////////////////////////
    ///                 Back to Menu button                    ///
    ///            By Søren Sehested Schoubye s224756          ///
    //////////////////////////////////////////////////////////////

    //The following method is used to return to the menu from the DimensionPrompt screen.
    @FXML
    private Button backMenu;

    @FXML
    public void backToMenu() throws IOException { //The method is called via the onAction method accociated with the button.
        Stage stage = (Stage) backMenu.getScene().getWindow();
        stage.close();
        Stage primaryStage = new Stage();
        Menu menu = new Menu(); //The menu class is called and the menu is displayed.
        menu.start(primaryStage);

    }
}
