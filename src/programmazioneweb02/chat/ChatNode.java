package programmazioneweb02.chat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class ChatNode {

    private final int PORT = 2222;
    private final ConcurrentHashMap<String, ConnectionHandler> connections = new ConcurrentHashMap<>();

    public ChatNode() {
        // Istanziazione e avvio manuale del thread per il ServerSocket
        Thread acceptorThread = new Thread(new ServerAcceptor());
        acceptorThread.setDaemon(true); // Termina se il main thread muore
        acceptorThread.start();

        // Il costruttore procede bloccando il Main thread sull'input della console
        handleConsoleInput();
    }

    public synchronized void logEvent(String event) {
        System.out.println("\n[EVENTO] " + event);
        System.out.print("> ");
    }

    private void handleConsoleInput() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Nodo avviato. Comandi: /connect <ip>, /send <ip> <msg>, /quit");
            System.out.print("> ");

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("/connect ")) {
                    String ip = line.split(" ")[1];
                    initiateConnection(ip);
                } else if (line.startsWith("/send ")) {
                    String[] parts = line.split(" ", 3);
                    if (parts.length == 3) {
                        sendMessageTo(parts[1], parts[2]);
                    }
                } else if (line.equals("/quit")) {
                    logEvent("Chiusura del nodo in corso...");
                    System.exit(0);
                }
                System.out.print("> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initiateConnection(String ip) {
        try {
            Socket socket = new Socket(ip, PORT);
            ConnectionHandler handler = new ConnectionHandler(socket, ip);
            connections.put(ip, handler);

            // Creazione manuale del thread di ricezione per il nuovo client
            Thread handlerThread = new Thread(handler);
            handlerThread.setDaemon(true);
            handlerThread.start();

            logEvent("Connesso a " + ip);
        } catch (IOException e) {
            logEvent("Errore connessione a " + ip + ": " + e.getMessage());
        }
    }

    private void sendMessageTo(String ip, String message) {
        ConnectionHandler handler = connections.get(ip);
        if (handler != null) {
            handler.sendMessage(message);
            logEvent("Inviato a " + ip + ": " + message);
        } else {
            logEvent("Host " + ip + " non trovato nella lista connessioni.");
        }
    }

    // --- INNER CLASSES ---

    private class ServerAcceptor implements Runnable {
        @Override
        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                logEvent("In ascolto sulla porta " + PORT);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    String remoteIp = clientSocket.getInetAddress().getHostAddress();

                    ConnectionHandler handler = new ConnectionHandler(clientSocket, remoteIp);
                    connections.put(remoteIp, handler);

                    // Spawn del thread per servire la connessione in ingresso accettata
                    Thread handlerThread = new Thread(handler);
                    handlerThread.setDaemon(true);
                    handlerThread.start();

                    logEvent("Accettata connessione da: " + remoteIp);
                }
            } catch (IOException e) {
                logEvent("Errore ServerSocket: " + e.getMessage());
            }
        }
    }

    private class ConnectionHandler implements Runnable {
        private final Socket socket;
        private final String remoteId;
        private final BufferedReader in;
        private final BufferedWriter out;

        public ConnectionHandler(Socket socket, String remoteId) throws IOException {
            this.socket = socket;
            this.remoteId = remoteId;
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        public synchronized void sendMessage(String message) {
            try {
                out.write(message);
                out.newLine();
                out.flush();
            } catch (IOException e) {
                logEvent("Errore invio a " + remoteId);
            }
        }

        @Override
        public void run() {
            try {
                String incomingMessage;
                while ((incomingMessage = in.readLine()) != null) {
                    logEvent("Ricevuto da " + remoteId + ": " + incomingMessage);
                }
            } catch (IOException e) {
                logEvent("Connessione interrotta con " + remoteId);
            } finally {
                connections.remove(remoteId);
                try { socket.close(); } catch (IOException ignored) {}
            }
        }
    }

    public static void main(String[] args) {
        new ChatNode();
    }
}