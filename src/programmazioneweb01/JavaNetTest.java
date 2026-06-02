package programmazioneweb01;
import java.net.*;
import java.util.Arrays;


public class JavaNetTest {


    static void printLocalAddress(){
        try {
            InetAddress myself = InetAddress.getLocalHost();
            System.out.println("My name is "+myself.getHostName());
            System.out.format("My IP is %s\n",myself.getHostAddress());
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
    }

    static void printHostnameAddress(String name){
        try {
            InetAddress in = InetAddress.getByName(name);
            System.out.println("Requested IP address is " + in.getHostAddress());
            System.out.println("Inserted and requested hostname is " + in.getHostName());
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {


        try {
            InetAddress[] ips=InetAddress.getAllByName("www.google.com");
            Arrays.stream(ips).toList().forEach(System.out::println);
            System.out.println();
            printHostnameAddress("www.google.com");
            printLocalAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

    }
}
