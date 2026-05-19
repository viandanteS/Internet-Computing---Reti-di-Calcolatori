package programmazioneweb01.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    public static void main(String[] args) throws IOException {
        Scanner sc=new Scanner(System.in);
        Socket s=new Socket("localhost",5555);
        System.out.println("Connection Established");
        BufferedReader in=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out=new PrintWriter(s.getOutputStream(),true);
        String serverMessage;

        while((serverMessage = in.readLine()) != null)
        {
            System.out.println(serverMessage);
            String prompt = sc.nextLine();
            out.println(prompt);
            if(out.checkError()){
                System.out.println("Can't reach the server, closing connection");
                break;
            }
        }
        s.close();
        sc.close();
    }
}
