package Lesson_7.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServ {
    private Vector<ClientHandler> clients;

    public MainServ() throws SQLException {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            // Подключение к БД
            AuthService.connect();

            // Создание слушателя сети
            server = new ServerSocket(8190);
            System.out.println("Сервер запущен!");

            // Ожидание подключения клиента
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился!");
                //
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        AuthService.disconnect();
    }

    /**
     * Отправка сообщения всем зарегистрированным в чате пользователям
     *
     * @param msg
     */
    public void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    /**
     * Отправка сообщения конкретному зарегистрированному в чате пользователю
     */
    public void sendMsgToUser(String user, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(user)) {
                o.sendMsg(msg);
            }
        }
    }

    /**
     * Подключение пользователя к чату
     *
     * @param client
     */
    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    /**
     * Отключение пользовтеля от чата
     *
     * @param client
     */
    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    /**
     * Проверка наличия пользователя в чате
     *
     * @param nick
     * @return
     */
    public boolean checkUserOnChat(String nick) {
        boolean result = false;
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                result = true;
                break;
            }
        }
        return result;
    }
}