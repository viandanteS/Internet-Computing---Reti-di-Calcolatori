package programmazioneweb02.datagramtimeserver;

import java.io.*;
import java.net.*;
import java.util.*;

public class TimeServer {

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(3575); //APRO IL SOCKET PER RICEVERE E INVIARE (SAREBBE LA PORTA DI USCITA)
            int n = 1;
            while (n <= 10) {

                byte[] buf = new byte[256];

                // riceve la richiesta
                DatagramPacket packet = new DatagramPacket(buf, buf.length); //CREO UN PACKET PER RICEVERE MAX 256 CHAR PRATICAMENTE
                socket.receive(packet);

                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println(packet.getAddress()+": "+n+" "+ receivedMessage);

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(receivedMessage));
                buf = new byte[256];//ripulisco buf da quello che ho letto
                // produce la risposta
                String dString = "Time in US/Alaska> "+cal.get(Calendar.HOUR)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
                buf = dString.trim().getBytes();

                // invia la risposta al client
                InetAddress address = packet.getAddress(); //DAL PACCHETTO RICEVUTO POSSO ESTRARRE CHI ME L'HA INVIATO
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port); //CREO UN NUOVO PACCHETTO INDICANDO A CHI LO VOGLIO INVIARE
                socket.send(packet);

                n++;
            }
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
            socket.close();
        }
    }
}