package programmazioneweb02.chat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private ClientServer server;
    private final List<Socket> currentConnections = new ArrayList<Socket>();
    private BufferedReader in;
    private BufferedWriter out;


   private class ClientServer {
        private final ServerSocket serverSocket;

        public ClientServer(ServerSocket serverSocket) {
            try {
                this.serverSocket =new ServerSocket(2222);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void run() {

            while(true){
                try {
                    Socket newConnection = serverSocket.accept();
                    currentConnections.add(newConnection);
                    new ConnectionHandler(newConnection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    class ConnectionHandler {

        private final Socket socket;

        public ConnectionHandler(Socket connection) {
            this.socket = connection;

        }

        private synchronized void sendMessage(String message){
            try {
                out.write(message);
                out.flush();
            } catch (IOException e) {}
        }
    }





}
