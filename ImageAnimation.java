import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ImageAnimation extends Application {
    ImageView imageView;
    int frameCount = 0;
    Timeline timeline;
    AnimationTimer timer;


    @Override
    public void start(Stage stage) {
        Image image = new Image("path/to/image.jpg");
        imageView = new ImageView(image);
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        Scene scene = new Scene(root, 300, 250);
        stage.setTitle("Image Animation");
        stage.setScene(scene);
        stage.show();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1),
                event -> {
                    frameCount++;
                    imageView.setImage(new Image("path/to/image" + frameCount + ".jpg"));
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}