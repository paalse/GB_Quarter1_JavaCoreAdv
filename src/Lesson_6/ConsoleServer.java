package Lesson_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleServer {
    static final String ADDRESS = "localhost";
    static final int PORT = 8186;

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Клиент подключился!");

                DataInputStream in = new DataInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                Scanner consoleIn = new Scanner(System.in);

                // Поток для получения данных по сети
                Thread socketThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String str = in.readUTF();
                                System.out.println(str);
                            } catch (IOException e) {
                                //e.printStackTrace();
                                System.out.println("Клиент отключился!");
                                break;
                            }
                        }
                    }
                });
                socketThread.start();

                // Поток для считывания данных с консоли и отправки их по сети
                Thread consoleThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                String str = consoleIn.nextLine();
                                out.writeUTF(str);
                            } catch (IOException e) {
                                //e.printStackTrace();
                                System.out.println("Клиент отключился!");
                                break;
                            }
                        }
                    }
                });
                consoleThread.setDaemon(true);
                consoleThread.start();
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
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}