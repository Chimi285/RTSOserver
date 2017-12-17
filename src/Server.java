

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

    public class Server {
        // порт, который будет прослушивать наш сервер
        static final int PORT = 6969;
        // список клиентов, которые будут подключаться к серверу
        public ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();

        public Server() {
            Socket clientSocket = null;
            // серверный сокет
            ServerSocket serverSocket = null;
            try {
                // создаём серверный сокет на определенном порту
                serverSocket = new ServerSocket(PORT);
                System.out.println("Сервер запущен!");
                // запускаем бесконечный цикл
                while (true) {
                    // таким образом ждём подключений от сервера
                    clientSocket = serverSocket.accept();
                    // создаём обработчик клиента, который подключился к серверу
                    // this - это наш сервер
                    ClientHandler client = new ClientHandler(clientSocket, this, null, 200, 200, 100, 0, 10, 10, new item[10]);
                    clients.add(client);
                    // каждое подключение клиента обрабатываем в новом потоке
                    new Thread(client).start();
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            finally {
                try {
                    // закрываем подключение
                    clientSocket.close();
                    System.out.println("Сервер остановлен");
                    serverSocket.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // отправляем сообщение всем клиентам
        public void sendMessageToAllClients(String msg) {
            for (ClientHandler o : clients) {
                o.sendMsg(msg);
            }

        }
        public String serialize(int x, int y, int vizible){
            String mes = "";
            for (ClientHandler o : clients) {
                //if (o.getX() - vizible <= x && o.getX() + vizible >= x && o.getY() - vizible <= y && o.getY() + vizible >= y) {
                    mes += o.profile();
                //}
            }
            return mes;
        }

        public void hit(int x, int y, int side, String nick, int damage){
            for (ClientHandler o : clients) {
                switch (side){
                    case 0:
                        if (!nick.equals(o.getNickname()) && o.getX() + 3 >= x && o.getX() - 3 <= x && o.getY() - 10 <= y && o.getY() >= y){
                            o.hit(damage);
                        }
                    break;
                    case 1:
                        if (!nick.equals(o.getNickname()) && o.getX() - 10 <= x && o.getY() + 3 >= y && o.getY() - 3 <= y + 3 && o.getX() >= x){
                            o.hit(damage);
                        }
                        break;
                    case 2:
                        if (!nick.equals(o.getNickname()) && o.getX() + 3 >= x && o.getX() - 3 <= x && o.getY() + 10 >= y && o.getY() <= y){
                            o.hit(damage);
                        }
                        break;
                    case 3:
                        if (!nick.equals(o.getNickname()) && o.getX() + 10 >= x && o.getY() + 3 >= y && o.getY() - 3 <= y + 3 && o.getX() <= x){
                            o.hit(damage);
                        }
                        break;
                }
            }
        }

        // удаляем клиента из коллекции при выходе из чата
        public void removeClient(ClientHandler client) {
            clients.remove(client);
        }

    }
