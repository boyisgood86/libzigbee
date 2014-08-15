package cn.acadiatech.telecom.box.engine;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

/** 
* @ClassName: StreamTool 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author quyong1986@gmail.com  
* @date 2014年7月25日 下午1:40:25 
*  
*/
public class StreamTool {
	 
	 /** 
	* @Title: save 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param file
	* @param @param data
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public static void save(File file, byte[] data) throws Exception {
		 FileOutputStream outStream = new FileOutputStream(file);
		 outStream.write(data);
		 outStream.close();
	 }
	 
	
	/**
	 * 
	* @Title: readLine 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param in
	* @param @return
	* @param @throws IOException    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	 public static String readLine(PushbackInputStream in) throws IOException {
			char buf[] = new char[128];
			int room = buf.length;
			int offset = 0;
			int c;
loop:		while (true) {
				switch (c = in.read()) {
					case -1:
					case '\n':
						break loop;
					case '\r':
						int c2 = in.read();
						if ((c2 != '\n') && (c2 != -1)) in.unread(c2);
						break loop;
					default:
						if (--room < 0) {
							char[] lineBuffer = buf;
							buf = new char[offset + 128];
						    room = buf.length - offset - 1;
						    System.arraycopy(lineBuffer, 0, buf, 0, offset);
						   
						}
						buf[offset++] = (char) c;
						break;
				}
			}
			if ((c == -1) && (offset == 0)) return null;
			return String.copyValueOf(buf, 0, offset);
	}
	 
	/**
	 * 
	* @Title: readStream 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param inStream
	* @param @return
	* @param @throws Exception    设定文件 
	* @return byte[]    返回类型 
	* @throws
	 */
	public static byte[] readStream(InputStream inStream) throws Exception{
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = -1;
			while( (len=inStream.read(buffer)) != -1){
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			return outSteam.toByteArray();
	}
}
