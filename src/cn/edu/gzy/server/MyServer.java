package cn.edu.gzy.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MyServer {
    private static ArrayList<UserThread> socketList = new ArrayList<>();
    private static StartServer startServer;

    /**
     * 启动服务线程
     */
    private static class StartServer extends Thread{
        @Override
        public void run() {
            try {
                /**
                 * 获得套接字有两个方法: Socket 和 ServerSocket
                 * 1.Socket: 一般在客户端使用
                 *  一旦你成功创建了一个Socket类的实例，你可以使用它来发送和接受字节流。
                 *  要发送字节流，你首先必须调用Socket类的getOutputStream方法来获取一个java.io.OutputStream对象。
                 *  要发送文本到一个远程应用，你经常要从返回的OutputStream对象中构造一个java.io.PrintWriter对象。
                 *  要从连接的另一端接受字节流，你可以调用Socket类的getInputStream方法用来返回一个java.io.InputStream对象。
                 *  2. ServerSocket: 一般在服务端使用
                 *  Socket类代表一个客户端套接字，即任何时候你想连接到一个远程服务器应用的时候你构造的套接字，
                 *  现在，假如你想实施一个服务器应用，例如一个HTTP服务器或者FTP服务器，你需要一种不同的做法。
                 *  这是因为你的服务器必须随时待命，因为它不知道一个客户端应用什么时候会尝试去连接它。
                 *  为了让你的应用能随时待命，你需要使用java.net.ServerSocket类。这是服务器套接字的实现。
                 *     ServerSocket和Socket不同，服务器套接字的角色是等待来自客户端的连接请求。一旦服务器套接字获得一个连接请求，它创建一个Socket实例来与客户端进行通信。
                 */
                ServerSocket serverSocket = new ServerSocket(6666);//创建端口为6666的Socket对象
                while(true){
                    Socket socket = serverSocket.accept();//创建Socket对象用于接收客户端请求
                    System.out.println("接受的Socket连接信息："+socket.toString());
                    UserThread userThread = new UserThread(socket);
                    MyServer.socketList.add(userThread);
                    new Thread(userThread).start();//开启输入输出线程
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * 内部类：实现用户进程
     */
    private static class UserThread implements Runnable{
        private Socket socket;
        private DataOutputStream dos;//DataOutputStream来包装OutputStream，你就可以用DataOutputStream直接以Java基本类型来写数据，从而来代替原始的字节。
        private DataInputStream dis;
        public UserThread(Socket socket) {
            this.socket = socket;
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public DataOutputStream getDos() {
            return dos;
        }

        public void setDos(DataOutputStream dos) {
            this.dos = dos;
        }

        public DataInputStream getDis() {
            return dis;
        }

        public void setDis(DataInputStream dis) {
            this.dis = dis;
        }

        @Override
        public void run() {
            try {
                dos = new DataOutputStream(socket.getOutputStream());//获取输出流，准备从服务器给其他的客户端发送信息
                dis = new DataInputStream(socket.getInputStream());//接收客户端发过来的消息，
                String recMsg = "";
                while(true){
                    //读取输入流的消息，并把消息存入recMsg
                    if(!"".equals(recMsg = dis.readUTF())){
                        System.out.println("收到一条消息："+recMsg);
                        for(UserThread userSocket : socketList){
                            if(userSocket.equals(this)){
                                continue;
                            }
                            try {
                                userSocket.getDos().writeUTF(recMsg);//写入字节流
                            }catch (IOException e){
                                socketList.remove(userSocket);
                                e.printStackTrace();
                            }
                        }
                        recMsg = "";//recMsg内容重新刷新
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) {
        new StartServer().start();//启动服务端
    }
}
