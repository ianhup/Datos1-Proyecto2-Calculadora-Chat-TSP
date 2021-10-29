import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Server {
    private ArrayList<String> prefija;
    private Stack<String> pila;
    public static String historypath = "Historial.csv";
    public static BufferedWriter writer;
    public static FileWriter fw;
    public static List<String> list;
    private ServerSocket serverSocket;
    public Client socket;
    private Client Scanner;
    public static Client instance = null;
    public static RPN r;
    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * @autor Ian Hu, Isa Cordoba
     */
    public void startServer(){
        try {

            while (!serverSocket.isClosed()){

                Socket socket = serverSocket.accept();
                System.out.println("Client connect");
                ClientHandler clientHandler = new ClientHandler(socket);

                Thread thread = new Thread(clientHandler);
                thread.start();

            }

        } catch (IOException e) {

        }
    }

    public void closeServerSocket(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void kitkat() throws ElementoFaltante, IOException {
        System.out.println(getInstance().txtTextoEnviar.getText());
        String myString=getInstance().txtTextoEnviar.getText();
        ShuntingYard sy = null;
        try {
            sy = new ShuntingYard(myString);
        } catch (ParentesisAperturaException | ParentesisCierreException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        ArrayList<String> prefija = sy.getPrefija();

        for(String string : prefija){
            System.out.print(string+ " ");
        }

        r = new RPN(prefija);
        try {
            System.out.println("\nResultado: "+r.rpn());
            getInstance().txtTexto.append("Resultado: " + String.valueOf(r.rpn()));
        } catch (ElementoFaltante e) {
            e.printStackTrace();
        }

    }
    public static Client getInstance() throws ElementoFaltante, IOException {
        if(instance == null){
            instance = new Client();
        }
        return instance;
    }
    public static void historial(){
        list= new LinkedList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String formatDatetime = now.format(formatter);
        list.add(formatDatetime);
        System.out.println(list);
        System.out.println(formatDatetime);
        try {
            fw = new FileWriter(historypath, true);
            writer = new BufferedWriter(fw);

            for (String str : list) {
                writer.write(str + ", "+getInstance().txtTextoEnviar.getText() + ", "+String.valueOf(r.rpn()));
                writer.newLine();
                writer.flush();
            }

            System.out.println("Done");
            writer.close();
            fw.close();

        } catch (IOException | ElementoFaltante e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ElementoFaltante {
        ServerSocket serverSocket = new ServerSocket(1234);
        getInstance();
        System.out.println("lol");
        Server server = new Server(serverSocket);
        server.startServer();
    }

}