package cn.acadiatech.telecom.box.utils;

import java.io.File;

/**
 * 获得串口读写权限
 * @author QUYONG
 *
 */
public class PermissionUtils {

	private static File device;
	private static final PermissionUtils instance = new PermissionUtils(device);

	private PermissionUtils(File device) {
		PermissionUtils.device = device;
	}

	public static PermissionUtils getInstance() {
		return instance;

	}

	public boolean getPermisson(File device) {
		try {
			/* Check access permission read,write */

			if (!device.canRead() || !device.canWrite()) {

				/* Missing read/write permission, trying to chmod the file */

				Process su;

				su = Runtime.getRuntime().exec("su");

				String cmd = "chmod 777 " + device.getAbsolutePath() + "\n"

				+ "exit\n";

				su.getOutputStream().write(cmd.getBytes());

				if ((su.waitFor() != 0) || !device.canRead()

				|| !device.canWrite()) {

					throw new SecurityException();

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
