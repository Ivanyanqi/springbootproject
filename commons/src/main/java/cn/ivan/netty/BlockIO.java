package cn.ivan.netty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author yanqi69
 * @date 2019/11/4
 */
public class BlockIO {

    public static class IOServer{
        public static void main(String[] args) throws IOException {
            ServerSocket ss = new ServerSocket(8000);

            while (true){
                //获取连接
                Socket accept = ss.accept();
                new Thread(()->{
                    try{
                        int len ;
                        byte[] data = new byte[1024];
                        InputStream inputStream = accept.getInputStream();
                        //字节流方式读取
                        while ((len = inputStream.read(data)) != -1){
                            System.out.println(new String(data,0,len));
                        }
                    }catch (IOException e){

                    }


                }).start();
            }
        }
    }

    public static class IOClient{
        public static void main(String[] args) throws IOException {
            Socket socket = new Socket("localhost",8000);
            while (true){
                try {
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write((new Date() + ":hello world").getBytes());
                    outputStream.flush();
                    Thread.sleep(2000);
                }catch (Exception e){

                }

            }
        }
    }
}
