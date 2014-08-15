package cn.acadiatech.telecom.box.utils;

/**
 * 常量
 * 
 * @author QUYONG
 * 
 */
public class Constant {

	public static final String KEY_MONETWARE_PREFERENCES = "telecom_preferences";
	// public static final String DB_NAME = "dbname_telecom";
	public static final String ROOMNAMEKEY ="roomName";
	public static final String SERIALPORT = "/dev/ttyS4";
	public static final int TTYSPEED = 115200;
	public static final int PORTSTOPBITS = 1;
	public static final int PORTPARITY = 100;
	public static final int PORTFLOWCONTROL = 100;
	public static final int PORTBITS = 8;
	public static final String DEVICEMAC = "00:2c:45:34:cd:ef:ec:ff";
	// 业务请求码
	public static final int USERLOGIN = 10001;// 用户登录

	public static final int DEVICECONTROL = 11001;// 组设备控制
	public static final int SINGLEDEVICECONTROL = 11002;// 单个设备控制
	public static final int DEVICENETWORK = 11003;// 设备组网控制
	public static final int DEVICEREAD = 11004;// 设备读控制

	public static final int DATABACKUP = 12001;// 数据备份
	public static final int DATARESTORE = 12002;// 数据还原

	public static final int YM_WRITE = 0xF0;// 写数据
	public static final int YM_READ = 0xF1;// 读数据、状态
	public static final int YM_RETURN = 0xF2; // 用于返回信息
	public static final int YM_ADD_ED = 0xF3; // 添加新的终端

	public static final int YM_RETERR = 0xF4; // 当出错时会返回错误信息
	public static final int YM_ONLINE = 0XF5; // 是否在线，val=1在线，val=0掉线
	public static final int YM_DEL_ED = 0xF6;
	public static final int YM_A20_START = 0xF7; // A20已经启动了
	public static final int YM_RM_ED_ALL = 0xF8; 

	// 业务返回状态
	public static final int SUCCESS = 0;// 成功

	public static final int FAIL = 1;// 失败

	// 睡眠时间ms
	public static final int SLEEPTIME = 500;

	// 端口
	public static final int TCPPORT = 4977;
	public static final int UDPPORT = 4937;

	public static final int SWITCH_VIEW = 21; // 开关
	public static final int CURTAIN_VIEW = 20; // 窗帘
	public static final int LIGHT_VIEW = 16; // 能调节亮度的灯
	public static final int TV_VIEW = 17; // 红外电视机

	public static final String ROOM_ENTRY_STATUS = "room_entry_status"; // 录入状态
	public static final int ROOM_ENTRY_STATUS_NO = 1; // 未录入
	public static final int ROOM_ENTRY_STATUS_YES = 2; // 已录入

	public static final int REQUTEST_SUCCESS = 1;

	public static final String SLEEPMODE = "入睡模式";
	public static final String GETUPMODE = "起床模式";
	public static final String ENTERTAINMENTMODE = "娱乐模式";
	public static final String OFFICMODE = "办公模式";
	public static final String BATHMODE = "洗浴模式";

	public static final int LOGIN_SUCCESS = 0;// 登陆成功
	public static final int LOGIN_FAILURE = 1;// 登陆失败

	public static final String GETUP_TIME = "getup_time";

	public static final int DEVICE_STATUS_CLOSE = 0; // 关闭
	public static final int DEVICE_STATUS_OPEN = 100; // 开启
	public static final int DEVICE_STATUS_STOP = 50; // 停止

}
