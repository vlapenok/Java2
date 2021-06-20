import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField, userNameField;

    @FXML
    HBox authPanel, msgPanel;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public void btnAction() { // Метод отправки сообщений по кнопке и по нажатию Ентер
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void auth() { // Метод авторизации
        connect();
        try {
            out.writeUTF("/auth " + userNameField.getText());
        } catch (IOException e) {
            showError("Ошибка авторизации");
        }
    }

    public void connect() {
        if(socket != null && !socket.isClosed()) { // Если соединение не пустое и не закрыто
            return;
        }
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread thread = new Thread(() -> {
                try {
                    while (true) { // Цикл чтения сообщений для авторизации
                        String inputMsg = in.readUTF();
                        if(inputMsg.equals("/authOk")) {
                            msgPanel.setVisible(true);
                            msgPanel.setManaged(true);
                            authPanel.setVisible(false);
                            authPanel.setManaged(false);
                            break;
                        }
                        textArea.appendText(inputMsg + "\n");
                    }
                    while (true) { // Цикл чтения входящих сообщений
                        String echoText = in.readUTF();
                        textArea.appendText(echoText + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        } catch (IOException e) {
            showError("Не удаётся подключится!");
        }
    }

    public void showError(String msg) { // После закрытия ошибки вылетат какой-то ексепшен!!!
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}
