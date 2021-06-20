import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ArrayList<ClientHandler> clients; // Список клиентов

    Server() {
        try {
            this.clients = new ArrayList<>();
            ServerSocket serverSocket = new ServerSocket(8189);
            System.out.println("Сервер запущен с портом " + serverSocket.getLocalPort());
            while (true) {
                Socket socket = serverSocket.accept(); // Бесконечно ждём подключения новых клиентов
                System.out.println("Клиент подключился");
                ClientHandler clientHandler = new ClientHandler(this, socket); // Создание объекта обработчика клиентов
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Два метода для добавления/удаления клиента в список
    public synchronized void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastMessage("Server: К нам присоединяется " + clientHandler.getUserName());
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Server: Нас покидает " + clientHandler.getUserName());
    }

    // Метод рассылки сообщений всем клиентам
    public synchronized void broadcastMessage(String msg) {
        for (ClientHandler c : clients) {
            c.sendMessage(msg);
        }
    }

    // Метод проверки имени пользователя на уникальность
    public boolean chekUserName(String userName) {
        for(ClientHandler c : clients) {
            if(c.getUserName().equalsIgnoreCase(userName)) {
                return false;
            }
        }
        return true;
    }
}
