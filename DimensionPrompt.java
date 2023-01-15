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

public class DimensionPrompt {


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
    //public int gamemode2;

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

    @FXML
    public static void start(Stage dimStage) {


        String appIcon = "Images/reversiIcon.png";
        Image icon = new Image(appIcon);

        dimStage.getIcons().add(icon);
        dimStage.setTitle("Reversi");
        dimStage.initStyle(StageStyle.UNDECORATED);


        fillPreviewPane(size);



        dimStage.setScene(scene);
        dimStage.showAndWait();

    }

    @FXML
    static
    Pane previewPane=(Pane)scene.lookup("#previewPane");
    @FXML
    static
    Label previewPaneLabel= (Label)scene.lookup("#previewPaneLabel");
    static
    GridPane previewGrid = new GridPane();

    private static void fillPreviewPane(int size) {
        previewPaneLabel.setText(size+"x"+size);
        Image whitePieceImage = new Image(whiteImage);
        Image blackPieceImage = new Image(blackImage);
        previewGrid.getChildren().removeIf(node -> node instanceof ImageView);
        previewPane.getChildren().remove(previewGrid);


        Image backGround1 = new Image(backImage1);
        Image backGround2 = new Image(backImage2);


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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


                if ((i == size/2 && j == size/2-1)||(i == size/2 && j == size/2)){
                    ImageView whitePiece = new ImageView(whitePieceImage);
                    previewGrid.add(whitePiece,i,j);
                    whitePiece.setPreserveRatio(true);
                    whitePiece.setMouseTransparent(true);
                    whitePiece.fitWidthProperty().bind(Bindings.divide(previewPane.widthProperty(), size));
                    whitePiece.fitHeightProperty().bind(Bindings.divide(previewPane.heightProperty(), size));


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

        previewPane.getChildren().add(previewGrid);

    }

    //////////////////////////////////////////////////////////////
    ///                          Skins                         ///
    //////////////////////////////////////////////////////////////



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
    //////////////////////////////////////////////////////////////
    public void setDimensions4(){

        dim.x = 4;
        dim.y = 4;
        size = 4;
        fillPreviewPane(size);
    }
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
    ///                 Play Alone initialising                ///
    //////////////////////////////////////////////////////////////

    @FXML
    private Button playAlone;

    @FXML
    private void playAloneAction(){
        gamemode = 1;
        Stage stage = (Stage) playAlone.getScene().getWindow();
        stage.close();
        PlayAlone game = new PlayAlone();
        if (dim.x == 0 || dim.y == 0){
            int newx = 8;
            int newy = 8;
            System.out.println(newx);
            game.gameStart(newx, newy);
        }else if(dim.x > 0 || dim.y > 0){
            System.out.println(dim.x + " " + dim.y);
            game.gameStart(dim.x, dim.y);
        }
    }



    @FXML
    private Button playAI;

    @FXML
    private void playAIAction(){
        gamemode=2;
        Stage stage = (Stage) playAI.getScene().getWindow();
        stage.close();
        PlayAI game = new PlayAI();
        if (dim.x == 0 || dim.y == 0){
            int newx = 8;
            int newy = 8;
            System.out.println(newx);
            game.gameStart(newx, newy);
        }else if(dim.x > 0 || dim.y > 0){
            System.out.println(dim.x + " " + dim.y);
            game.gameStart(dim.x, dim.y);
        }
    }

    @FXML
    private Button playOnline;
    @FXML
    private void playOnlineAction() throws IOException {
        gamemode = 3;
        Stage stage = (Stage) playOnline.getScene().getWindow();
        stage.close();
        HostPrompt game = new HostPrompt();
        if (dim.x == 0 || dim.y == 0){
            int newx = 8;
            int newy = 8;
            System.out.println(newx);
            game.runHostPrompt();
        }else if(dim.x > 0 || dim.y > 0){
            System.out.println(dim.x + " " + dim.y);
            game.runHostPrompt();
        }
    }

    //////////////////////////////////////////////////////////////
    ///                 Back to Menu button                    ///
    //////////////////////////////////////////////////////////////
    @FXML
    private Button backMenu;

    @FXML
    public void backToMenu() throws IOException {
        Stage stage = (Stage) backMenu.getScene().getWindow();
        stage.close();

        Stage primaryStage = new Stage();
        Menu menu = new Menu();
        menu.start(primaryStage);

    }
}
