package programmazioneweb02.httpserver;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread extends Thread {

    private final Socket socket;
    private BufferedReader in;
    private DataOutputStream out;

    public ServerThread(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {

            try {
                socket.close();
            }catch (IOException ex){
                ex.printStackTrace();

            }
            return;
        }
        System.out.println("Connection Established with " + socket.getInetAddress().getHostName());
        this.start();
    }

    @Override
    public void run() {
        try {
            String request=in.readLine();
            System.out.println("Request: " + request);
            StringTokenizer st = new StringTokenizer(request);

            if ((st.countTokens()>=2) && st.nextToken().equals("GET")) {
                if ((request = st.nextToken()).startsWith("/"))
                    request = request.substring(1);

                if (request.endsWith("/") || request.equals(""))
                    request = request + "index.html";
                // per impedire che si possa scrivere: http://hostname/../../../etc/passwd
                // oppure: http://hostname//etc/passwd

                if ((request.indexOf("..") != -1) || (request.startsWith("/"))) {
                    out.writeBytes("403 Forbidden. " +
                            "You do not have enough privileges to read: " + request + "\r\n");
                } else {
                    File f = new File(request);

                    reply(out, f);
                }
            }else{
                out.writeBytes("HTTP/1.1 400 Bad Request");
            }
            socket.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void reply(DataOutputStream out, File f) throws IOException {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(f));//prendo lo stream del file e lo do ad un DataInputStream
        /*
         A data input stream lets an application read PRIMITIVE Java data
         types from an underlying input stream in a machine-independent way.
         An application uses a data output stream to write data that can later
         be read by a data input stream.
         */

            int length = (int) f.length(); //lunghezza in byte del file

            byte[] buffer = new byte[length];

            in.readFully(buffer);//dallo stream leggo esattamente byte per quanto è
            //Se i dati arrivano lentamente o a blocchi, readFully si mette in attesa e continua a pescare dallo stream fino a riempire l'array.
        /*int i=0;
        while(i<length){
            buffer[i]=in.readByte();
            i++;
        }*/
            out.writeBytes("HTTP/1.1 200 OK\r\n");
            out.writeBytes("Content-Length: " + length + "\r\n");
            out.writeBytes("Content-Type: text/html\r\n\r\n");
            out.write(buffer);
            out.flush();
            in.close();
        }catch (FileNotFoundException e){
            out.writeBytes("HTTP/1.1 404 Not Found\r\n");
        }
    }


}
