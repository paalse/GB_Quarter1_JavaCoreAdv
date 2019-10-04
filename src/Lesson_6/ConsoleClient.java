package Lesson_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ConsoleClient {
    static final String ADDRESS = "localhost";
    static final int PORT = 8186;

    public static void main(String[] args) throws IOException {
        try {
            Socket socket = new Socket(ADDRESS, PORT);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            Scanner consoleIn = new Scanner(System.in);

            // Поток для получения данных по сети
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String str = in.readUTF();
                            System.out.println(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }).start();

            // Поток для считывания данных с консоли и отправки их по сети
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String str = consoleIn.nextLine();
                            out.writeUTF(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}