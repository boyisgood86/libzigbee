package cn.acadiatech.telecom.box.test;

import cn.acadiatech.telecom.box.engine.DataProvider;

public class TestJNI {

	public static void main(String[] args) {
		DataProvider provider = DataProvider.getInstance();
		String listDevice = provider.ListDevice();
		System.out.println(listDevice);

	}
	
	
	
	
	
	

}
