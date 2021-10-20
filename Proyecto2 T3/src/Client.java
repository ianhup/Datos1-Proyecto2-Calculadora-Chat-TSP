

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    private Socket socket;
    //Writer
    private DataOutputStream dataOutputStream;
    //Reader
    private DataInputStream dataInputStream;
    private String username;

    public Client(Socket socket, String username){
        try {
            this.username = username;
            System.out.println(username + " Porfavor escribe tu operacion :)");

            Server.kitkat();

            this.socket = socket;
            //Writer
            this.dataOutputStream=new DataOutputStream(socket.getOutputStream());
            //Reader
            this.dataInputStream=new DataInputStream(socket.getInputStream());
            new Client(this.socket, this.username);



        }catch (IOException e){
            e.printStackTrace();
            closeEverything(socket,dataInputStream,dataOutputStream);
        }
    }

    public void sendMessages(){
        try{
            //dataOutputStream.write(Integer.parseInt(username));
            dataOutputStream.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()){
                //String messageToSend = scanner.nextLine();
                //dataOutputStream.write(Integer.parseInt(username + ": " + messageToSend));
                //dataOutputStream.newLine();
                dataOutputStream.flush();
            }
        }catch (IOException e) {
            closeEverything(socket,dataInputStream,dataOutputStream);
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msgFromGroupChat;

                while (socket.isConnected()){
                    try{
                        msgFromGroupChat = dataInputStream.readLine();
                        System.out.println(msgFromGroupChat);
                    }catch (IOException e) {
                        closeEverything(socket, dataInputStream, dataOutputStream);
                    }
                }
            }
        }).start();
    }

    public void closeEverything(Socket socket, DataInputStream bufferedReader, DataOutputStream bufferedWriter){
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

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost",1234);
        Client client = new Client(socket,username);
        client.listenForMessage();
        client.sendMessages();
    }

}
