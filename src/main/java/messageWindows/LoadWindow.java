package messageWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoadWindow {
    public static void loadWindow() {
//        Box box = new Box();
//
//        //Setting the properties of the Box
//        box.setWidth(70.0);
//        box.setHeight(70.0);
//        box.setDepth(70.0);
//
//        //Setting the material of the box
//        PhongMaterial material = new PhongMaterial();
//        material.setDiffuseColor(Color.DARKSLATEBLUE);
//
//        //Setting the diffuse color material to box
//        box.setMaterial(material);
//
//        //Setting the rotation animation to the box
//        final RotateTransition rotateTransition = new RotateTransition();
//
//        //Setting the duration for the transition
//        rotateTransition.setDuration(Duration.millis(3000));
//
//        //Setting the node for the transition
//        rotateTransition.setNode(box);
//
//        //Setting the axis of the rotation
//        rotateTransition.setAxis(Rotate.X_AXIS.add(20, 20, 20));
//
//        //Setting the angle of the rotation
//        rotateTransition.setByAngle(360);
//
//        //Setting the cycle count for the transition
//        rotateTransition.setCycleCount(50);
//
//        //Setting auto reverse value to false
//        rotateTransition.setAutoReverse(true);
//        rotateTransition.play();
//
//        //Creating a Group object
//        BorderPane root = new BorderPane();
//        root.setCenter(box);
//
//        //Creating a scene object
//        Scene scene = new Scene(root, 300, 150);
//
//        //Setting camera
//        PerspectiveCamera camera = new PerspectiveCamera(false);
//        camera.setTranslateX(0);
//        camera.setTranslateY(0);
//        camera.setTranslateZ(0);
//        scene.setCamera(camera);
//
//        Stage stage = new Stage();
//
//        //Setting title to the Stage
//        stage.setTitle("Load");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();

        ProgressBar bar = new ProgressBar();
        bar.setMinWidth(170);
        bar.setMinHeight(30);
        BorderPane root = new BorderPane();
        BorderPane.setAlignment(bar, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(bar, new Insets(0, 0, 10, 0));
        root.setBottom(bar);
        Scene scene = new Scene(root, 300, 150);
        Stage stage = new Stage();
        stage.setTitle("Load");
        stage.setScene(scene);
        stage.show();
    }
}
