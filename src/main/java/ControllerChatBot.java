import bot.Bot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ControllerChatBot {

    @FXML
    TextArea textAreaID;

    @FXML
    TextField textFieldID;

    @FXML
    Button sendID;

    Bot bot;

    @FXML
    public void sendMsg() throws IOException {
        if (textFieldID.getText().trim().length() > 0) {
            textAreaID.appendText("I'am: " + textFieldID.getText() + "\n");
            textAreaID.appendText("\t Bot: " +
                    bot.sayInReturn(textFieldID.getText()) + "\n");
        }
        textFieldID.clear();
        textFieldID.requestFocus();
    }

}
