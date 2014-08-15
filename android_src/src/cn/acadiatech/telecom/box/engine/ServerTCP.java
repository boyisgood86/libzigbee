package cn.acadiatech.telecom.box.engine;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;
import cn.acadiatech.telecom.box.utils.Constant;

/**
 * TCP服务器
 * 
 * @author QUYONG
 * 
 */
public class ServerTCP {

	private Handler handler;
	private ExecutorService executorService;// 线程池

	public ServerTCP(Handler handler) {
		this.handler = handler;

		 executorService = Executors.newFixedThreadPool(Runtime.getRuntime()
		 .availableProcessors() * 50);
	}

	public void serverRun() {
		try {
			ServerSocket serverSocket = new ServerSocket(Constant.TCPPORT);

			while (true) {
				Socket incoming = serverSocket.accept();
				
				executorService.execute(new DeviceControlHandler(incoming, handler));// 启动一个线程来处理请求

//				Runnable r = new ThreadEchoHandler(incoming, handler);
//				Thread t = new Thread(r);
//				t.start();

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
