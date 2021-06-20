import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
            Thread thread = new Thread(() -> {
                    try {
                        while (true) { // Цикл для чтения сообщений об авторизации
                            String msg = in.readUTF();
                            if (msg.startsWith("/auth ")) { // Получение команды для авторизации на сервере
                                userName = msg.split("\\s+", 2)[1]; // Разбитие строки по пробелам
                                if(server.chekUserName(userName)) {
                                    sendMessage("/authOk"); // Отправка команды на сервер для авторизации
                                    server.subscribe(this); // Добавление данного клиента в список
                                    break;
                                } else {
                                    sendMessage("Такое имя уже занято, введите уникальное");
                                }
                            } else {
                                sendMessage("Server: Вам необходимо авторизоваться");
                            }
                        }
                        while(true) { // Цикл для чтения сообщений и рассылки всем клиентам
                            String msg = in.readUTF();
                            System.out.println(userName + ": " + msg);
                            server.broadcastMessage(userName + ": " + msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        server.unsubscribe(this); // Если клиент отключился, то удаляем его из списка
                    }
                });
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод отправки сообщений на неавторизованному клиенту
    public void sendMessage(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
