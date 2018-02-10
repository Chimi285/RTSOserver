

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class Server {
    // порт, который будет прослушивать наш сервер
    static final int PORT = 6969;
    // список клиентов, которые будут подключаться к серверу
    public static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    public static LinkedList<Building> buildings = new LinkedList<>();
    public static LinkedList<item> inventory = new LinkedList<>();
    public Server() {
        buildings.add(new Building(0, 0, 10, 10, 0, "choisedNull.png", 100, 200,
                new item[]{new item("Red", "Red.png", "choisedRed.png", 0, 0, "", 0, 0), null, null, null, null, null, null, null, null}));
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
                ClientHandler client = new ClientHandler(clientSocket, this, null, 200, 200, 100, 0, 10, 10, new item[10], false, null);
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
    public Data serialize(int x, int y, int vizible, boolean menu, item[] inventory, item[] InInventory){
        LinkedList<Hero> players = new LinkedList<>();
        LinkedList<Build> builds = new LinkedList<>();
        for (ClientHandler o : clients) {
            //if (o.getX() - vizible <= x && o.getX() + vizible >= x && o.getY() - vizible <= y && o.getY() + vizible >= y) {
            players.add(o.profile());
            //}
        }
        for (Building o : buildings) {
            //if (o.getX() - vizible <= x && o.getX() + vizible >= x && o.getY() - vizible <= y && o.getY() + vizible >= y) {
            builds.add(o.profile());
            //}
        }

        Data output = new Data(players, builds, menu, inventory, InInventory);
        return output;
    }

    public void hit(int x, int y, int side, String nick, int damage){
        for (ClientHandler o : clients) {
            switch (side){
                case 0:
                    if (!nick.equals(o.getNickname()) && x - 100 <= o.getX() && x + 100 >= o.getX() && y - 150 <= o.getY() && y + 100 >= o.getY()){
                        o.hit(damage);
                    }
                    break;
                case 1:
                    if (!nick.equals(o.getNickname()) && x - 100 <= o.getX() && x + 150 >= o.getX() && y - 100 <= o.getY() && y + 100 >= o.getY()){
                        o.hit(damage);
                    }
                    break;
                case 2:
                    if (!nick.equals(o.getNickname()) && y - 100 <= o.getY() && y + 150 > o.getY() && x + 100 >= o.getX() && x - 100 <= o.getX()){
                        o.hit(damage);
                    }
                    break;
                case 3:
                    if (!nick.equals(o.getNickname()) && y - 100 <= o.getY() && y + 100 >= o.getY() && x - 150 <= o.getX() && x + 100 >= o.getX()){
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
    public static void restartClient(ClientHandler client){
        for (ClientHandler o : clients){
            if(o.getNickname().equals(client.getNickname())){
                o.restart();
            }
        }
    }

}