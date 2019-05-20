package Tcp;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpHandler<R extends Retriever> implements Runnable {
    static class MockRetriever implements Retriever {
        @Override
        public String get(String url) {
            return "get " + url;
        }
    }

    private final Socket sock;

    /**
     * 构造方法
     *
     * @param client 要建立tcp连接的客户端
     */
    HttpHandler(Socket client) {
        this.sock = client;
    }

    public void run() {
        // 这是一个 twr try with resources，try语句中初始化的流在脱离try后会被自动的关闭
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()))
        ) {
            System.out.println("接收到新请求");
            out.print("HTTP/1.0 200\r\nContent-Type: text/plain\r\n\r\n");
            String line;
            while ((line = in.readLine()) != null) {
                if (line.length() == 0) break;
                out.println(line);
            }
        } catch (Exception e) {
            //
        }
    }

    @NotNull
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(port);
        // 这个循环让服务器套接字一接受到客户端就转发给handler处理, handler是一个实现了线程接口的处理器
        for (; ; ) {
            Socket client = ss.accept();
            HttpHandler<MockRetriever> handler = new HttpHandler<>(client);
            handler.download(new MockRetriever());
            new Thread(handler).start();
        }
    }


    public String download(R r) {
        return r.get("http://www.baidu.com");
    }
}
