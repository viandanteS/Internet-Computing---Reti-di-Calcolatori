
package programmazioneweb02.httpwelcome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;

public class HttpWelcome {

    private static final int port = 8080;

    private static String HtmlWelcomeMessage(){

        return String
                .format(
                                "<html>\n" +
                                "  <head>\n" +
                                "        <title>Test Ing INFORMATICA</title>\n" +
                                "  </head>\n" +
                                "  <body>\n" +
                                "       <h2 align=\"center\">\n" +
                                "           <font color=\"#0000FF\"> Benvenuti al corso di reti </font>\n" +
                                "       </h2>\n" +
                                "   </body>\n" +
                                "</html>");
    }

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket s = serverSocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter out = new PrintWriter(s.getOutputStream());

                String request = in.readLine();
                System.out.println("REQUEST: " + request);
                StringTokenizer st = new StringTokenizer(request);
                if((st.countTokens())>=2 && st.nextToken().equals("GET")){
                    out.println("HTTP/1.1 200 OK");
                    out.println("Date: " + new Date());
                    out.println("Content-Type: text/html");
                    out.println("Content-Length: " + HtmlWelcomeMessage().length());
                    out.println();
                    out.println(HtmlWelcomeMessage());
                } else {
                    out.println("HTTP/1.1 400 Bad Request");
                }
                out.flush();
                s.close();
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}

//it works!!!!