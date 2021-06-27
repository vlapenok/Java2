import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private String userName;
    private DataInputStream in;
    private DataOutputStream out;

    public String getUserName() {
        return userName;
    }

    ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            // Поток для чтения сообщений от сервера
            new Thread(() -> logic()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Разбить этот метод на два и упростить
    private void logic() { // Доработать, чтобы нельзя было указывать имена начинающиеся с символа "/"
        try {
            while (true) { // Цикл для чтения сообщений об авторизации
                String msg = in.readUTF();
                if (msg.startsWith("/auth ")) { // Получение команды для авторизации на сервере
                    String[] authText = msg.split("\\s+"); // Разбитие строки по пробелам
                    if(authText.length > 2) {
                        sendMessage("Server: Имя не может состоять из нескольких слов");
                    } else if(authText.length == 1) {
                        sendMessage("Server: Имя не может быть пустым");
                    } else {
                        userName = authText[1];
                        if (server.chekUserName(userName)) {
                            sendMessage("/authOk"); // Отправка команды на сервер для авторизации
                            server.subscribe(this); // Добавление данного клиента в список
                            break;
                        } else {
                            sendMessage("Server: Такое имя уже занято, введите уникальное");
                        }
                    }
                } else {
                    sendMessage("Server: Вам необходимо авторизоваться");
                }
            }
            while(true) { // Цикл для чтения входящих сообщений и рассылки всем клиентам
                String msg = in.readUTF();
                if(msg.startsWith("/")) {
                    if (msg.equals("/exit")) { // команда для выхода пользователя из чата
                        sendMessage(msg);
                        break;
                    } else if (msg.startsWith("/w ")) { // команда для отправки персонального сообщения
                        // Доработать, чтобы сообщение не могло быть нулевым
                        String[] personalText = msg.split("\\s+", 3);
                        server.personalSendMessage(this, personalText[1], personalText[2]);
                    }
                    continue;
                }
                server.broadcastMessage(userName + ": " + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Клиент " + userName + " отключился");
            server.unsubscribe(this); // Если клиент отключился, то удаляем его из списка
            closeConnection();
        }
    }

    private void closeConnection() { // Метод закрытия потоков и соединения
        try {
            if(in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if(socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод отправки сообщений неавторизованному клиенту
    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
