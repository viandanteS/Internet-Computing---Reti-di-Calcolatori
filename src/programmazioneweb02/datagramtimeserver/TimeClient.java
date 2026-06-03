package programmazioneweb02.datagramtimeserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {

            socket = new DatagramSocket();
            InetAddress inetAddress = InetAddress.getByName("localhost");
            String message = "US/Alaska";
            byte[] buf = message.getBytes();

            DatagramPacket dp=new DatagramPacket(buf,buf.length,inetAddress,3575);//specifico a chi lo voglio inviare
            socket.send(dp);//prendo il mio socket e invio il messaggio al destinatario

            buf = new byte[256];//ripulisco il buffer

            dp = new DatagramPacket(buf,buf.length); //creo packet per ricevere
            socket.receive(dp);

            String receivedMessage = new String(dp.getData()).trim();

            System.out.println(receivedMessage);


        }catch(IOException ioe){
            ioe.printStackTrace();
        }finally {
            socket.close();
        }
    }
}
