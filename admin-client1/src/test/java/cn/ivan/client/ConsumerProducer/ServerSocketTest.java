package cn.ivan.client.ConsumerProducer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketTest {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8084);

        Socket accept = serverSocket.accept();

        InputStream inputStream = accept.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String line ;
        while (!(line = in.readLine()).startsWith("Accept")){
            System.out.println(line);
        }
        System.out.println("===================接受完毕=====================");
        OutputStream outputStream = accept.getOutputStream();
        outputStream.write("好了".getBytes());
        outputStream.flush();
    }

}
