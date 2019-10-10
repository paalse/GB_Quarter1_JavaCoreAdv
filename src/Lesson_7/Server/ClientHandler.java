package Lesson_7.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

public class ClientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServ serv;
    private String nick;

    public ClientHandler(MainServ serv, Socket socket) {
        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String currentNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);

                                if (currentNick != null) {
                                    if (!serv.checkUserOnChat(currentNick)) {  // Проверка подлкючен ли пользователь с таким именем
                                        sendMsg("/authok");
                                        nick = currentNick;
                                        serv.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        sendMsg("Пользователь " + currentNick + " уже подключен к чату");
                                    }
                                } else {
                                    sendMsg("неверный логин/пароль");
                                }
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {  // Блок обработки служебных сообщений
                                if (str.equalsIgnoreCase("/end")) {
                                    sendMsg("/clientClose");
                                    break;
                                }
                                if (str.startsWith("/w")) {
                                    String[] splitStr = str.split(" ", 3);
                                    String recipient = splitStr[1];
                                    if (serv.checkUserOnChat(recipient)) {
                                        serv.sendMsgToUser(recipient, nick + ": " + splitStr[2]);
                                    } else {
                                        sendMsg("Пользователь " + recipient + " не подключен к чату");
                                    }
                                }
                            } else {
                                serv.broadcastMsg(nick + ": " + str);
                            }
                        }

                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serv.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}