package cn.acadiatech.telecom.box.engine;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import cn.acadiatech.telecom.box.utils.Constant;

/**
 * UDP服务器
 * 
 * @author QUYONG
 * 
 */
public class ServerUDP {

	private static final int ECHOMAX = 255;// max size of echo datagram

	public static void serverRun() {
		try {

			// 1.创建一个DatagramSocket实例，指定本地端口号，可以选择指定本地地址

			DatagramSocket socket = new DatagramSocket(Constant.UDPPORT);

			DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX],
					ECHOMAX);

			while (true) {

				// 2.使用DatagramSocket的receive方法来接收一个DatagramPacket实例。

				socket.receive(packet);

				System.out.println("Handling client at "
						+ packet.getAddress().getHostAddress() + " on port "
						+ packet.getPort());

				socket.send(packet);

				packet.setLength(ECHOMAX);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
