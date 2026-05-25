package programmazioneweb01.threadedechoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {
    static Scanner sc=new Scanner(System.in);

    public static void main(String[] args){
        Socket s = null;
        try {
           s =  new Socket("localhost", 5555);
           System.out.println("Connection Established");
           BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
           PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
           System.out.println(br.readLine());
            String serverResp=null;
           while(true){
               pw.println(sc.nextLine());
               pw.flush();
               serverResp=br.readLine();
               if(serverResp.equals("Closing connection...") || serverResp==null){
                System.out.println("Closing connection...");
                break;
               }
           }
       }catch(SocketException e){
           if (e.getMessage()!=null && e.getMessage().toLowerCase().contains("connection reset")) {
            System.out.println("Connesione chiusa dal server");            
           }
       }catch(IOException ioe){
        System.out.println("IO exception triggered");
       }finally {
            
        try{
            s.close();
        }catch(Exception e){
            e.printStackTrace();
        }
           sc.close();

       }
    }

}
