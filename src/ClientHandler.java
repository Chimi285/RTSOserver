
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;


// реализуем интерфейс Runnable, который позволяет работать с потоками
public class ClientHandler implements Runnable{

    // экземпляр нашего сервера
    private Server server;
    // исходящее сообщение
    private PrintWriter outMessage;
    // входящее собщение
    private Scanner inMessage;
    private static final String HOST = "localhost";
    // клиентский сокет
    private Socket clientSocket = null;
    private String nickname = null;
    // количество клиента в чате, статичное поле
    private static int clients_count = 0;
    private int x, y, health, side, damage, vizible;
    private item[] inventory = new item[3];

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
            this.inventory = inventary;
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
                /*server.sendMessageToAllClients("New Client!");
                server.sendMessageToAllClients("Clients = " + clients_count);*/
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
                        outMessage.println("Authorization Successful!");
                        outMessage.flush();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("All ok");
                                ObjectOutputStream oos = null;
                                try {
                                    oos = new ObjectOutputStream(clientSocket.getOutputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                while (true){
                                    try {
                                        oos.writeObject(server.serialize(x, y, vizible));
                                        oos.flush();
                                    } catch (IOException e) {
                                    }
                                }
                            }
                        }).start();

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
                                break;
                            case "Died":
                                //System.out.println("restartBEFORE");
                                this.restart();
                                //System.out.println("restartAFTER");
                                break;
                        }
                    }
                    if (clientMessage.equalsIgnoreCase("##session##end##")) {
                        break;
                    }
                }
            }
        }
        catch (Exception e) {
            //System.out.println("ERROR");
        }
        finally {
            this.close();
        }
    }
    public void restart() {
        x = 0;
        y = 0;
        side = 0;
        health = 100;
        System.out.println("restartVOID");
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
        if(this.health - damage >= 0)this.health -= damage;
        else this.health = 0;
    }
    public void sendMsg(String msg) {
        try {
            outMessage.println(msg);
            outMessage.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public Hero profile(){
        if(this.nickname != null){
            return new Hero(this.nickname, this.x, this.y, this.health, this.side, this.inventory);
        }
        return new Hero(null, 0, 0, 0, 0, null);
    }
    public void close() {
        // удаляем клиента из списка
        server.removeClient(this);
        clients_count--;
        //server.sendMessageToAllClients("Clients = " + clients_count);
        System.out.println("Clients = " + clients_count);
    }
}
