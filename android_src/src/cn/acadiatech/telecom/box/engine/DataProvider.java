package cn.acadiatech.telecom.box.engine;

/**
 * 
 * @ProjectName: TelecomBox
 * @ClassName: DataProvider
 * @Description: JNI接口
 * @author QUYONG
 * @date 2014年7月31日 上午9:37:06
 * @version v1.0
 * 
 */
public class DataProvider {

	private static final class DataProviderHolder {

		private static final DataProvider instance = new DataProvider();

	}

	private DataProvider() {
	}

	public static DataProvider getInstance() {

		return DataProviderHolder.instance;

	}

	/**
	 * 控制设备状态
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param deviceType
	 *            设备类型
	 * @param state
	 *            设备状态
	 * @return 0
	 */
	public synchronized native int Operator(String deviceMac, int deviceType,
			int state);

	/**
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param deviceType
	 *            设备类型
	 * @param cmd
	 *            设备操作类型
	 * @param state
	 *            设备状态
	 * @return
	 */
	public synchronized native int OperatorCmd(String deviceMac,
			int deviceType, int cmd, int state);

	/**
	 * 
	 * @param deviceMac
	 *            设备Mac
	 * @param deviceType
	 *            设备类型
	 * @param cmd
	 *            设备操作类型
	 * @param state
	 *            设备状态
	 * @return
	 */
	public synchronized native String OperatorCmdString(String deviceMac,
			int deviceType, int cmd, int state);

	/**
	 * 打开串口
	 * 
	 * @param serialPort
	 *            串口 'ttys0'
	 * @return
	 */
	public native int OpenTty(String serialPort);

	/**
	 * 关闭串口
	 */
	public native void CloseTty();

	/**
	 * 设置串口波特率
	 * 
	 * @param portSpeed
	 *            串口波特率
	 * @return
	 */
	public native int SetTtySpeed(int portSpeed);

	/**
	 * 设置奇偶校验位
	 * 
	 * @param portParity
	 * @return
	 */
	public native int SetTtyParity(int portParity);

	/**
	 * 设置串口数据位
	 * 
	 * @param Bits
	 * @return
	 */
	public native int SetTtyBits(int portBits);

	/**
	 * 设置串口数据停止位
	 * 
	 * @param StopBits
	 * @return
	 */
	public native int SetTtyStopBits(int portStopBits);

	/**
	 * 设置串口流控制
	 * 
	 * @param portFlowControl
	 * @return
	 */
	public native int SetTtyFlowControl(int portFlowControl);

	/**
	 * 读取状态
	 * 
	 * @return
	 */
	public   native String ListDevice();
	
	public synchronized  native String ReadDevice();
	

	/**
	 * 
	* @Title: ListDevice_new 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param value
	* @param @param type
	* @param @param cmd
	* @param @param val
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public synchronized native String ListDevice_new(String value,int type,int cmd,int val);
	
	/**
	 * 
	* @Title: WriteDevice 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param value
	* @param @param type
	* @param @param cmd
	* @param @param val
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public synchronized native String WriteDevice(String value,int type,int cmd,int val);

	static {
		System.loadLibrary("zigbee_r_lock");
	}

}
