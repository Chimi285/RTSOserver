
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

// реализуем интерфейс Runnable, который позволяет работать с потоками
public class ClientHandler implements Runnable {

    // экземпляр нашего сервера
    private Server server;
    // исходящее сообщение
    private PrintWriter outMessage;
    // входящее собщение
    private Scanner inMessage;
    private static final String HOST = "localhost";
    private static final int PORT = 6768;
    // клиентский сокет
    private Socket clientSocket = null;
    private String nickname = null;
    // количество клиента в чате, статичное поле
    private static int clients_count = 0;
    private int x, y, health, side, damage, vizible;

    // конструктор, который принимает клиентский сокет и сервер
    public ClientHandler(Socket socket, Server server, String nickname, int x, int y, int health, int side, int damage, int vizible, item[] inventary) {
        try {
            clients_count++;
            this.server = server;
            this.clientSocket = socket;
            this.outMessage = new PrintWriter(socket.getOutputStream());
            this.inMessage = new Scanner(socket.getInputStream());
            this.nickname = nickname;
            this.x = x;
            this.y = y;
            this.health = health;
            this.side = side;
            this.damage = damage;
            this.vizible = vizible;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    // Переопределяем метод run(), который вызывается когда
    // мы вызываем new Thread(client).start();
    @Override
    public void run() {
        try {
            while (true) {
                // сервер отправляет сообщение
                server.sendMessageToAllClients("New Client!");
                server.sendMessageToAllClients("Clients = " + clients_count);
                System.out.println("New Client!");
                System.out.println("Clients = " + clients_count);
                break;
            }

            while (true) {
                // Если от клиента пришло сообщение
                if (inMessage.hasNext()) {
                    String clientMessage = inMessage.nextLine();
                    if(this.nickname == null) {
                        System.out.println("Nick:" + clientMessage);
                        nickname = clientMessage;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("All ok");
                                while (true){
                                    String coor = server.serialize(x, y, vizible);
                                    outMessage.println(coor);
                                    //System.out.println(coor);
                                    outMessage.flush();
                                }
                            }
                        }).start();
                        outMessage.println("Authorization Successful!");
                        outMessage.flush();
                    }else if(clientMessage.equalsIgnoreCase("##session##end##")){
                        System.out.println(this.nickname + " disconnect");
                    }else{
                        switch (clientMessage){
                            case "Forward":
                                this.side = 0;
                                y-=3;
                                break;
                            case "Back":
                                this.side = 2;
                                y+=3;
                                break;
                            case "Right":
                                this.side = 1;
                                x+=3;
                                break;
                            case "Left":
                                this.side = 3;
                                x-=3;
                                break;
                            case "Hit":
                                server.hit(this.x, this.y, this.side, this.nickname, this.damage);

                        }
                    }
                    outMessage.flush();
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println("ERROR");
        }
        finally {
            this.close();
        }
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void hit(int damage){
        this.health -= damage;
    }
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public String profile(){
        if(this.nickname != null) return this.nickname + "." + this.x + "." + this.y + "." + this.health + "." + this.side + ".";
        return "";
    }
    public void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        server.sendMessageToAllClients("Clients = " + clients_count);
        System.out.println("Clients = " + clients_count);
    }
}
