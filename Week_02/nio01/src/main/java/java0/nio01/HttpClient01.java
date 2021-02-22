package java0.nio01;

import java.io.IOException;
import java.net.Socket;

/**
 * @Desc
 * @Author wfy
 * @Date 2021/2/20 9:49
 */
public class HttpClient01 {
    public static void main(String[] args) {
        try {
            Socket client = new Socket("localhost", 8801);
            client.getOutputStream().write(1);
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
