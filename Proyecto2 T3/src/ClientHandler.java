
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    private Socket socket;
    //Writer
    private DataOutputStream dataOutputStream;
    //Reader
    private DataInputStream dataInputStream;
    private String clientUsername;

    public  ClientHandler(Socket socket){
        try {
            this.socket = socket;
            //Writer
            this.dataOutputStream=new DataOutputStream(socket.getOutputStream());
            //Reader
            this.dataInputStream=new DataInputStream(socket.getInputStream());
            this.clientUsername = dataInputStream.readLine();
            //clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + "enter");
        } catch (IOException e){
            closeEverything(socket, dataInputStream,dataOutputStream);
        }

    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try {
                messageFromClient = dataInputStream.readLine();
                broadcastMessage(messageFromClient);
            }catch (IOException e){
                closeEverything(socket, dataInputStream,dataOutputStream);
                break;
            }
        }
    }

    public void broadcastMessage(String messageToSend){
        for (ClientHandler clientHandler : clientHandlers){
            try {
                if (!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.dataOutputStream.write(Integer.parseInt(messageToSend));
                    //clientHandler.dataOutputStream.newLine();
                    clientHandler.dataOutputStream.flush();
                }
            } catch (IOException e){
                closeEverything(socket,dataInputStream,dataOutputStream);
            }
        }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("S" + clientUsername + "left");
    }

    public void closeEverything(Socket socket, DataInputStream bufferedReader, DataOutputStream bufferedWriter){
        removeClientHandler();
        try {
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
