package cn.acadiatech.telecom.box.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import cn.acadiatech.telecom.box.engine.DataProvider;

@SuppressLint({ "DefaultLocale", "SimpleDateFormat" })
public class CommonUtil {

	/**
	 * 获取6位随机数
	 * 
	 * @return
	 */
	public static String getRandomNumber() {
		// 处理六位的随机数
		Random random = new Random();
		int num = random.nextInt(999999) + 1;// 0~999998 1~999999 000001
		DecimalFormat decimalFormat = new DecimalFormat("000000");

		String numFormat = decimalFormat.format(num);
		return numFormat;
	}

	private static String loadFileAsString(String filePath)
			throws java.io.IOException {
		StringBuffer fileData = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}

	/*
	 * Get the STB MacAddress
	 */
	public static String getMacAddress() {
		try {
			return loadFileAsString("/sys/class/net/eth0/address")
					.toUpperCase().substring(0, 17);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 开启service
	 * 
	 * @param <T>
	 * @param context
	 * @param clazz
	 */
	public static <T> void startService(Context context, Class<T> clazz) {
		Intent i = new Intent(context, clazz);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(i);
	}

	/**
	 * 获得系统当前时间
	 * 
	 * @return
	 */
	public static String getCurrentTime() {
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		return date;
	}

	/**
	 * 获得SD卡路径
	 * 
	 * @return
	 */
	public static String getExternalPath() {

		String isMounted = Environment.getExternalStorageState();
		if (isMounted.equals(Environment.MEDIA_MOUNTED)) {

			return Environment.getExternalStorageDirectory().getAbsolutePath();

		}
		return null;
	}

	/**
	 * 防止控件重复点击
	 */
	private static long lastClickTime;

	public static boolean isFastDoubleClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 800) {
			return true;
		} else {
			lastClickTime = time;
			return false;
		}
	}

	/**
	 * 设置串口
	 */
	public static void setTtyPort() {
		File device = new File(Constant.SERIALPORT);
		PermissionUtils.getInstance().getPermisson(device);

		// 获得jni类
		DataProvider provider = DataProvider.getInstance();

		// 打开串口
		int openTty = provider.OpenTty(Constant.SERIALPORT);

		if (openTty == -1) {
			// 打开串口失败
		} else {
			// 打开成功

			// 1.设置波特率
			provider.SetTtySpeed(Constant.TTYSPEED);

			// 2.设置停止位
			provider.SetTtyStopBits(Constant.PORTSTOPBITS);

			// 3.设置奇偶校验位
			provider.SetTtyParity(Constant.PORTPARITY);

			// 4.设置流控制
			provider.SetTtyFlowControl(Constant.PORTFLOWCONTROL);

			// 5.设置数据位
			provider.SetTtyBits(Constant.PORTBITS);

		}
	}

	/**
	 * 关闭串口
	 */
	public static void closeTtyPort() {
		DataProvider provider = DataProvider.getInstance();

		provider.CloseTty();
	}
	
	
	public static boolean isStringNotNull(String value) {
		return value != null && value.equals("") == false
				&& value.equals("null") == false;
	}
	
	/**
	 *	写入文件
	 * @param context
	 * @param fileName
	 * @param msg
	 */
	public static void save(Context context,String fileName,String msg) {  
		  
        try {  
            /* 根据用户提供的文件名，以及文件的应用模式，打开一个输出流.文件不存系统会为你创建一个的， 
             * 至于为什么这个地方还有FileNotFoundException抛出，我也比较纳闷。在Context中是这样定义的 
             *   public abstract FileOutputStream openFileOutput(String name, int mode) 
             *   throws FileNotFoundException; 
             * openFileOutput(String name, int mode); 
             * 第一个参数，代表文件名称，注意这里的文件名称不能包括任何的/或者/这种分隔符，只能是文件名 
             *          该文件会被保存在/data/data/应用名称/files/chenzheng_java.txt 
             * 第二个参数，代表文件的操作模式 
             *          MODE_PRIVATE 私有（只能创建它的应用访问） 重复写入时会文件覆盖 
             *          MODE_APPEND  私有   重复写入时会在文件的末尾进行追加，而不是覆盖掉原来的文件 
             *          MODE_WORLD_READABLE 公用  可读 
             *          MODE_WORLD_WRITEABLE 公用 可读写 
             *  */  
            FileOutputStream outputStream = context.openFileOutput(fileName,  
                    Activity.MODE_PRIVATE|Activity.MODE_APPEND);  
            outputStream.write((CommonUtil.getCurrentTime()+":  	"+msg+"\r\n").getBytes());  
            outputStream.flush();  
            outputStream.close();  
            //Toast.makeText(context, "保存成功", Toast.LENGTH_LONG).show();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
    }
	
	public static String getCpuUsed() throws Exception{
		String Result;
		StringBuffer stringBuffer=new StringBuffer();
    	Process p=Runtime.getRuntime().exec("top -n 1");

    	BufferedReader br=new BufferedReader(new InputStreamReader   
    	(p.getInputStream ()));
    	while((Result=br.readLine())!=null)
    	{
    	 if(Result.trim().length()<1){
    		 continue;
    	 }else{
    		 String[] CPUusr = Result.split("%");
    		 stringBuffer.append("USER:"+CPUusr[0]+"\n");
        	 String[] CPUusage = CPUusr[0].split("User");
        	 String[] SYSusage = CPUusr[1].split("System");
        	 stringBuffer.append("CPU:"+CPUusage[1].trim()+" length:"+CPUusage[1].trim().length()+"\n");
        	 stringBuffer.append("SYS:"+SYSusage[1].trim()+" length:"+SYSusage[1].trim().length()+"\n");
        	 stringBuffer.append(Result+"\n");
        	 break;
    	 }
    	}
    	return stringBuffer.toString();
	}
	
	
	
	/**
	 * 
	* @Title: getControlStatus 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mac
	* @param @param recevData
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int getControlStatus(String mac,String recevData){
		
		/*
		fe
		cb:99:b1:04:00:4b:12:00  Mac
		01  型号
		f2  操作码
		00  
		01
		64   灯状态
		d1
		23
		*/
		try {
			if(null == mac || "".equals(mac.trim()) || null == recevData || "".equals(recevData.trim())||(recevData.length() <32)){
				//数据不合法
				return -1;
			}else if((recevData.length() > 256)){
				//數據錯誤
				//跟新数据库的值为0
				return 0;
				
			}else{
				//合法数据
				String macAddr = splitStr(mac.trim());
				StringBuilder sb = new StringBuilder(recevData.trim());
				String subMac = sb.substring(2, 18);
				if(!macAddr.equalsIgnoreCase(subMac.trim())){
					//Mac地址校验失败
					return -1;
				}else{
					//mac校验成功
					String valueStr = sb.substring(22, 24);
					int value = Integer.parseInt(valueStr);
					return value;
					
				}
				
				
			}
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	/**
	 * 
	* @Title: splitStr 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param mac
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String splitStr(String mac){
		
		
		if(null == mac){
			return null;
		}else if("".equals(mac.trim())){
			return null;
		}else{
			String macAddr=mac.replace(":", "");
			return macAddr.trim();
			
		}
	}

}
