package programmazioneweb01.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) {
        try{
            ServerSocket ss=new ServerSocket(5555);
            Socket incoming=ss.accept(); //metodo bloccante
            System.out.println("Server Listening on "+incoming.getInetAddress().toString());
            BufferedReader in=new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out=new PrintWriter(incoming.getOutputStream(),true);
            out.println("Hello! Type BYE and press enter to exit!");

            while(true){
                String line=in.readLine();
                if(line.trim().equals("BYE") || line == null){break;}
                out.println(line);
            }//while
            incoming.close();
            ss.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//main
}//EchoServer
