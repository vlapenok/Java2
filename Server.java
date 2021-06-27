import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<ClientHandler> clients; // Список клиентов

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
        broadcastClientsList();
    }

    public synchronized void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastMessage("Server: Нас покидает " + clientHandler.getUserName());
        broadcastClientsList();
    }

    // Метод рассылки сообщений всем клиентам
    public synchronized void broadcastMessage(String msg) {
        for (ClientHandler c : clients) {
            c.sendMessage(msg);
        }
    }

    // Метод рассылки личных сообщений
    public synchronized void personalSendMessage(ClientHandler sender, String receiver, String message) {
        if(sender.getUserName().equalsIgnoreCase(receiver)) {
            sender.sendMessage("Server: Сообщения самому себе запрещены");
            return;
        }
        for(ClientHandler c : clients) {
            if(c.getUserName().equalsIgnoreCase(receiver)) {
                c.sendMessage("от " + sender.getUserName() + ": " + message);
                sender.sendMessage("ЛС для " + receiver + ": " + message);
                return;
            }
        }
        sender.sendMessage("Server: " + receiver + " не в сети");
    }

    // Метод формирования списка клиентов в одну строку
    public synchronized void broadcastClientsList() {
        StringBuilder builder = new StringBuilder(clients.size() * 10);
        builder.append("/clients_list ");
        for (ClientHandler c : clients) {
            builder.append(c.getUserName()).append(" ");
        }
        String clientsListStr = builder.toString();
        broadcastMessage(clientsListStr);
    }

    // Метод проверки имени пользователя на уникальность
    public synchronized boolean chekUserName(String userName) {
        for(ClientHandler c : clients) {
            if(c.getUserName().equalsIgnoreCase(userName)) {
                return false;
            }
        }
        return true;
    }
}
