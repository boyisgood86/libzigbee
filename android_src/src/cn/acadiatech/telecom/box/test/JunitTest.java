package cn.acadiatech.telecom.box.test;

import java.io.File;
import java.util.List;

import android.database.sqlite.SQLiteOpenHelper;
import android.test.AndroidTestCase;
import cn.acadiatech.telecom.box.ControlApplication;
import cn.acadiatech.telecom.box.beans.RoleBean;
import cn.acadiatech.telecom.box.beans.UserBean;
import cn.acadiatech.telecom.box.db.DBHelper;
import cn.acadiatech.telecom.box.db.RoleDao;
import cn.acadiatech.telecom.box.db.UserDao;
import cn.acadiatech.telecom.box.db.ex.action.DeviceDBAction;
import cn.acadiatech.telecom.box.db.ex.bean.Device;
import cn.acadiatech.telecom.box.engine.DataProvider;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.LogUtil;
import cn.acadiatech.telecom.box.utils.PermissionUtils;

/**
 * 单元测试
 * 
 * @author QUYONG
 * 
 */
public class JunitTest extends AndroidTestCase {

	private static final int TTYSPEED = 115200;
	private static final int PORTSTOPBITS = 1;
	private static final int PORTPARITY = 100;
	private static final int PORTFLOWCONTROL = 100;
	private static final int PORTBITS = 8;
	private static final String DEVICEMAC = "00:2c:45:34:cd:ef:ec:ff";
	private static final String TAG = "JunitTest";

	// 测试数据库，表创建
	public void testDb() {
		SQLiteOpenHelper instance = DBHelper.getInstance(getContext());
		instance.getWritableDatabase();
	}

	// 测试插入数据到角色表
	public void testInsertRole() {

		RoleDao roleDao = RoleDao.getInstance(getContext());
		RoleBean role = new RoleBean(1, "管理员");
		roleDao.insert(role);
	}

	// 测试从角色表获取数据
	public void testGetRole() {

		RoleDao roleDao = RoleDao.getInstance(getContext());
		RoleBean role = roleDao.getRole(1);
		LogUtil.d(TAG, role.toString() + "__");
	}

	// 测试插入数据到角色表
	public void testInsertUser() {

		UserDao userDao = UserDao.getInstance(getContext());
		RoleBean role = new RoleBean(1, "管理员");

		UserBean user = new UserBean("android", "android", "18621918933",
				"这是一间豪华间！", role);
		userDao.insert(user);
	}

	// 测试从数据表获取数据
	public void testGetUser() {

		UserDao userDao = UserDao.getInstance(getContext());
		List<UserBean> users = userDao.queryUsers();

		for (UserBean user : users) {
			LogUtil.d(TAG, user.toString() + "__");

		}
	}

	// 更新角色表
	public void testUpdateRole() {
		RoleDao roleDao = RoleDao.getInstance(getContext());
		RoleBean role = new RoleBean(1, "普通用户");
		roleDao.update(role);
	}

	// 测试插入数据到角色表
	public void testUpdateUser() {

		UserDao userDao = UserDao.getInstance(getContext());
		RoleBean role = new RoleBean(1, "管理员");

		UserBean user = new UserBean("401", "778899", "18621918933",
				"这是一间豪华间！", role);
		userDao.update(user);
	}

	// 测试登陆
	public void testLogin() {
		UserDao userDao = UserDao.getInstance(getContext());
		boolean user = userDao.getUser("401", "7788899");

		LogUtil.d(TAG, user + "__");
	}

	// 删除Role
	public void testDeleteRole() {
		RoleDao roleDao = RoleDao.getInstance(getContext());
		RoleBean role = new RoleBean(1, "");
		roleDao.delete(role);

	}

	// 删除User
	public void testDeleteUser() {
		UserBean user = new UserBean("401", null, null, null, null);
		UserDao.getInstance(getContext()).delete(user);
		;

	}

	public void getCachePath() {

		LogUtil.d(TAG, ControlApplication.getFileCache());
	}

	public void testJni() {

		// 打开串口读写权限
		String serialPort = "/dev/ttyS0";
		File device = new File(serialPort);
		boolean permisson = PermissionUtils.getInstance().getPermisson(device);

		LogUtil.d(TAG, permisson + "__");

		// 获得jni类
		DataProvider provider = DataProvider.getInstance();

		// 打开串口
		int openTty = provider.OpenTty(serialPort);

		if (openTty == -1) {
			// 打开串口失败
			LogUtil.d(TAG, "打开串口失败");
		} else {
			// 打开成功

			// 1.设置波特率
			int setTtySpeed = provider.SetTtySpeed(TTYSPEED);
			// 2.设置停止位
			int setTtyStopBits = provider.SetTtyStopBits(PORTSTOPBITS);
			// 3.设置奇偶校验位
			int setTtyParity = provider.SetTtyParity(PORTPARITY);
			// 4.设置流控制
			int setTtyFlowControl = provider.SetTtyFlowControl(PORTFLOWCONTROL);
			// 5.设置数据位
			int setTtyBits = provider.SetTtyBits(PORTBITS);

			// 6.操作设备

			String listDevice = provider.ListDevice();
			LogUtil.d(TAG, "cmd:" + listDevice);

			// 关闭串口
			provider.CloseTty();

		}
	}

	public void test() {
		DeviceDBAction.getInstance(getContext()).updateProgressByMac(
				"FC:1A:F7:04:00:4B:12:00", 2, 30);
		int brightnessByMac = DeviceDBAction.getInstance(getContext())
				.getBrightnessByMac("FC:1A:F7:04:00:4B:12:00");
		LogUtil.d(TAG, brightnessByMac + "___");
	}

	public void testMath() {
		int value = 30;

		int n = (int) Math.sqrt(value) + 1;

		LogUtil.d(TAG, "  " + n);

	}
	
	
	public void testGetData(){
		
		String recevData= "fef39bb104004b120001f20000008f23fedf9ab104004b120001f2000000a223fee699b104004b120001f20000009823fe4908e804004b120001f2000000ff23";

		String mac = "CB:99:B1:04:00:4B:12:00";
		
	
		//int controlStatus = CommonUtil.getControlStatus(mac, recevData);
		
		LogUtil.d(TAG, "controlStatus：       "+recevData.length());
		
	}
	
	public void testGetData1(){
		
		
		
		String mac = "03";
		
		
		int controlStatus = Integer.parseInt(mac);
		
		LogUtil.d(TAG, "controlStatus：       "+controlStatus);
		
	}
	
	
	

}
