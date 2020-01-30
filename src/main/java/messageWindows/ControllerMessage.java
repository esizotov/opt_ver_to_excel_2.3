package messageWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class ControllerMessage {

    // окно сообщений +++++
    public static void messageWindowDone(String message) {
        BorderPane root = new BorderPane();

        Label label = new Label(message);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setWrapText(true);

        Button buttonOk = new Button();
        buttonOk.setText("OK");
        buttonOk.setPadding(new Insets(5, 30, 5, 30));

        root.setCenter(label);
        BorderPane.setAlignment(buttonOk, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(buttonOk, new Insets(0, 0, 20, 0));
        root.setBottom(buttonOk);

        buttonOk.setOnAction(event -> {buttonOk.getScene().getWindow().hide();});

        Stage primaryStage = new Stage();
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root, 300, 150));
        primaryStage.setTitle("Info");
        primaryStage.showAndWait();
    }

}
