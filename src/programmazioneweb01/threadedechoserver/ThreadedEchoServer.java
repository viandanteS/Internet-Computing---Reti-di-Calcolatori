package programmazioneweb01.threadedechoserver;

import java.io.*;
import java.net.*;


public class ThreadedEchoServer {


    public static void main(String[] args) throws IOException {
        int i=0;
        ServerSocket serverSocket=null;
        try{
            serverSocket=new ServerSocket(5555);
            while(true){
                new EchoServerHandler(serverSocket.accept(),i).start();
                i++;
            }
        }
        catch (Exception e){

        }finally{
            serverSocket.close();
        }
    }
    static class EchoServerHandler extends Thread{

        private Socket socket;
        private int id;

        public EchoServerHandler(Socket socket,int i){
            this.socket=socket;
            this.id=i;
        }

        public void run(){
            try {
                BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out=new PrintWriter(socket.getOutputStream(),true);
                out.println("BYE to terminate the connection, Handler number "+this.id);
                String response=in.readLine();
                while(true){
                    if(response.trim().equals("BYE")){
                        out.println("Closing connection...");
                        break;}
                    out.println("");
                    System.out.println(response);
                    response=in.readLine();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {}
            }

        }
    }

}
