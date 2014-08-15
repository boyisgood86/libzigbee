package cn.acadiatech.telecom.box.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;

public class NetUtil {
	
	public static String inputStream2String(PushbackInputStream is) throws IOException{ 
		   BufferedReader in = new BufferedReader(new InputStreamReader(is)); 
		   StringBuffer buffer = new StringBuffer(); 
		   String line = ""; 
		   while ((line = in.readLine()) != null){ 
		     buffer.append(line); 
		   } 
		   return buffer.toString(); 
		} 

}
