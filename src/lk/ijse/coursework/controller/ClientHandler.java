package lk.ijse.coursework.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread{
    public static ArrayList<ClientHandler> clientHandlerArrayList= new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;

    public ClientHandler(Socket socket,ArrayList<ClientHandler>clientHandlers) {
        try {
            this.socket = socket;
            this.clientHandlerArrayList=clientHandlers;
            this.bufferedReader= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.printWriter=new PrintWriter(socket.getOutputStream(),true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String clientMsg;
            while ((clientMsg = bufferedReader.readLine()) != null) {
                if (clientMsg.equalsIgnoreCase("bye")) {
                    break;
                }
                for (ClientHandler c : clientHandlerArrayList) {
                    c.printWriter.println(clientMsg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                bufferedReader.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
