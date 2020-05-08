package util;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * DatagramSocket 是 Java 通过 UDP 而不是 TCP 进行网络通信的机制
 * 参考 https://www.cnblogs.com/czwbig/p/10018118.html
 */
public class DatagramClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket();

        byte[] buffer = "123456789".getBytes();
        InetAddress receiverAddress = InetAddress.getLocalHost();

        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiverAddress, 80);//DatagramPacket 类此类表示数据报包
        datagramSocket.send(packet);
    }
}
