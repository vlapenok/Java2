package lesson4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    public void btnAction(ActionEvent actionEvent) {
        if(!textField.getText().isEmpty()) {
            textArea.appendText(textField.getText() + "\n");
            textField.clear();
        }
    }

    public void textFieldAction(ActionEvent actionEvent) {
        btnAction(actionEvent);
    }
}