import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ChatBotWindow extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sampleChatBot.fxml"));
        primaryStage.setTitle("ChatBot");
        primaryStage.setX(1100);
        primaryStage.setY(270);
        primaryStage.setScene(new Scene(root, 330, 500));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
