package programmazioneweb02.httpwelcome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHttpWelcome {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream());

            pw.println("GET / HTTP/1.1");
            pw.println("Host: jacopo");
            pw.flush();
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
