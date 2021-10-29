import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client extends javax.swing.JFrame {
    public Socket socket;
    //Writer
    private DataOutputStream dataOutputStream;
    //Reader
    private DataInputStream dataInputStream;
    private String username;

    private javax.swing.JButton btnEnviar;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea txtTexto;
    public javax.swing.JTextField txtTextoEnviar;
    private Server Scanner;

    /**
     * @autor Ian Hu, Isa Cordoba
     */
    public Client() throws IOException {
        //new Client2(this.socket,this.username);
        Client2();



    }

    public void Client2() throws IOException {
        initComponents();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        Socket socket = new Socket("localhost",1234);

        try {
            System.out.println("Porfavor escribe tu operacion :)");
            this.socket = socket;
            //Writer
            this.dataOutputStream=new DataOutputStream(socket.getOutputStream());
            //Reader
            this.dataInputStream=new DataInputStream(socket.getInputStream());
            new Client2();



        }catch (IOException e){
            e.printStackTrace();
            //closeEverything(socket,dataInputStream,dataOutputStream);
        }
    }

    private void initComponents(){
        System.out.println("todo bien");
        jScrollPane1 = new JScrollPane();
        txtTexto = new javax.swing.JTextArea();
        btnEnviar = new javax.swing.JButton();
        txtTextoEnviar = new javax.swing.JTextField();

        String mensaje3 = "Porfavor escribe tu operacion :)";
        this.txtTexto.append(mensaje3);




        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Cliente X");

        txtTexto.setColumns(20);
        txtTexto.setRows(5);
        jScrollPane1.setViewportView(txtTexto);

        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    btnEnviarActionPerformed(evt);
                } catch (ElementoFaltante e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtTextoEnviar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                                        .addComponent(txtTextoEnviar))
                                .addContainerGap())
        );
        setVisible(true);
        pack();
    }// </editor-fold>//GEN-END:initComponents



    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) throws ElementoFaltante, IOException {//GEN-FIRST:event_btnEnviarActionPerformed
        String mensaje = "\n"+"1: " + this.txtTextoEnviar.getText() + "\n";
        this.txtTexto.append(mensaje);
        System.out.println(txtTextoEnviar.getText());
        Server.kitkat();
        Server.historial();

        //gettext se detecte como el shonting x
        //justo despues meter el resultado que se printee despues del shonting x

    }//GEN-LAST:event_btnEnviarActionPerformed




    private static class Client2 {
        public Socket socket;
        //Writer
        private DataOutputStream dataOutputStream;
        //Reader
        private DataInputStream dataInputStream;
        private String username;
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
    }

    public static void main(String[] args) throws IOException, ElementoFaltante {
        Server.getInstance();
    }

}