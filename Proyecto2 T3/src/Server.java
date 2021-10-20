
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Server {
    private ArrayList<String> prefija;
    private Stack<String> pila;

    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

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

    public static void kitkat()  {
        Scanner input = new Scanner(System.in);
        String myString=input.next();
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

        RPN r = new RPN(prefija);
        try {
            System.out.println("\nResultado: "+r.rpn());
        } catch (ElementoFaltante e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        Server server = new Server(serverSocket);
        server.startServer();

    }
}
