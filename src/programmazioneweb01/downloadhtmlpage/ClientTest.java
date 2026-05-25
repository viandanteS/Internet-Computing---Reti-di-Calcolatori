package programmazioneweb01.downloadhtmlpage;

import java.io.*;
import java.net.*;

public class ClientTest {

    public static void main(String[] args) {

        try{
            String URL="stackoverflow.com";
            Socket socket=new Socket(URL,80);
            PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("GET /questions HTTP/1.1");
            out.println("Host: "+URL);
            out.println();//quando si da l'accapo il server ricevente interpreterà la richeista.
            boolean more=true;
            while(more){
                String line=in.readLine();
                if(line==null){more=false; break;}
                System.out.println(line);
            }
            socket.close();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }


}
