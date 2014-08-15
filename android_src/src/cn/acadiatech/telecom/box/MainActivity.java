package cn.acadiatech.telecom.box;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cn.acadiatech.telecom.box.engine.DataProvider;
import cn.acadiatech.telecom.box.service.ReadDeviceService;
import cn.acadiatech.telecom.box.service.SmartControlService;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.LogUtil;
import cn.acadiatech.telecom.box.utils.PermissionUtils;

public class MainActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainActivity";

	private EditText etSerialPort, etTtySpeed, etTtyStopBits, etTtyParity,
			etTtyFlowControl, etTtyBits, etDeviceMac, etDeviceType,
			etDeviceState;

	private Button btnSetPort, btnSetDeviceState, btnClosePort;
	private DataProvider provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main1);
		initData();

		// 1. 开启后台控制service

		CommonUtil.startService(this, SmartControlService.class);

		// 2. 开启后台更新设备状态方法

		//CommonUtil.startService(this, ReadDeviceService.class);

	}

	/**
	 * 设置数据
	 */
	private void initData() {
		findViewById();
		setListener();
		provider = DataProvider.getInstance();
		
		

		// 关闭串口
		// CommonUtil.closeTtyPort();

		// 开启串口
		// CommonUtil.setTtyPort();

	}

	private void findViewById() {
		etSerialPort = (EditText) findViewById(R.id.et_serialPort);
		etTtySpeed = (EditText) findViewById(R.id.et_ttyspeed);
		etTtyStopBits = (EditText) findViewById(R.id.et_ttystopbits);
		etTtyParity = (EditText) findViewById(R.id.et_ttyparity);
		etTtyFlowControl = (EditText) findViewById(R.id.et_ttyflowcontrol);
		etTtyBits = (EditText) findViewById(R.id.et_ttybits);
		etDeviceMac = (EditText) findViewById(R.id.et_devicemac);
		etDeviceType = (EditText) findViewById(R.id.et_devicetype);
		etDeviceState = (EditText) findViewById(R.id.et_devicestate);

		btnSetPort = (Button) findViewById(R.id.setPort);
		btnSetDeviceState = (Button) findViewById(R.id.setDeviceState);
		btnClosePort = (Button) findViewById(R.id.closePort);

	}

	private void setListener() {
		btnSetPort.setOnClickListener(this);
		btnSetDeviceState.setOnClickListener(this);
		btnClosePort.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		int id = v.getId();

		switch (id) {
		case R.id.setPort:

			String serialPort = etSerialPort.getText().toString();
			LogUtil.d(TAG, "serialPort: " + serialPort);

			File device = new File(serialPort);
			boolean permisson = PermissionUtils.getInstance().getPermisson(
					device);

			Toast.makeText(this, "permisson: " + permisson + "__",
					Toast.LENGTH_SHORT).show();

			// 获得jni类

			// 打开串口
			int openTty = provider.OpenTty(serialPort);

			if (openTty == -1) {
				// 打开串口失败
				LogUtil.d(TAG, "打开串口失败");
			} else {
				// 打开成功
				Toast.makeText(this, "打开串口成功！" + openTty, Toast.LENGTH_SHORT)
						.show();

				// 1.设置波特率
				String ttySpeed = etTtySpeed.getText().toString();
				provider.SetTtySpeed(Integer.parseInt(ttySpeed));

				// 2.设置停止位
				String ttyStopBits = etTtyStopBits.getText().toString();
				provider.SetTtyStopBits(Integer.parseInt(ttyStopBits));

				// 3.设置奇偶校验位
				String ttyParity = etTtyParity.getText().toString();
				provider.SetTtyParity(Integer.parseInt(ttyParity));

				// 4.设置流控制
				String ttyFlowControl = etTtyFlowControl.getText().toString();
				provider.SetTtyFlowControl(Integer.parseInt(ttyFlowControl));

				// 5.设置数据位
				String ttyBits = etTtyBits.getText().toString();
				provider.SetTtyBits(Integer.parseInt(ttyBits));

				LogUtil.d(TAG, "设置串口信息成功！");

				Toast.makeText(this, "设置串口信息成功！", Toast.LENGTH_SHORT).show();

			}

			break;
		case R.id.setDeviceState:

			// 6.操作设备

			String deviceMac = etDeviceMac.getText().toString();
			String deviceType = etDeviceType.getText().toString();
			String deviceState = etDeviceState.getText().toString();
			int operator = provider
					.Operator(deviceMac, Integer.parseInt(deviceType),
							Integer.parseInt(deviceState));

			if (operator == -1) {
				// 打开串口失败
				LogUtil.d(TAG, "打开设备失败");

				Toast.makeText(MainActivity.this, "打开设备失败！" + operator,
						Toast.LENGTH_SHORT).show();

			} else {
				// 打开成功

				Toast.makeText(MainActivity.this, "打开设备成功！" + operator,
						Toast.LENGTH_SHORT).show();

			}

			// new Thread(new Runnable() {
			//
			// private int operator;
			//
			// @Override
			// public void run() {
			// while (true) {
			// try {
			// Thread.sleep(100);
			// } catch (InterruptedException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// String deviceMac = etDeviceMac.getText().toString();
			// String deviceType = etDeviceType.getText().toString();
			// String deviceState = etDeviceState.getText().toString();
			// operator = provider.Operator(deviceMac,
			// Integer.parseInt(deviceType),
			// Integer.parseInt(deviceState));
			//
			// if (operator == -1) {
			// // 打开串口失败
			// LogUtil.d(TAG, "打开设备失败");
			// runOnUiThread(new Runnable() {
			// public void run() {
			// Toast.makeText(MainActivity.this,
			// "打开设备失败！" + operator,
			// Toast.LENGTH_SHORT).show();
			//
			// }
			// });
			// } else {
			// // 打开成功
			// runOnUiThread(new Runnable() {
			// public void run() {
			// Toast.makeText(MainActivity.this,
			// "打开设备成功！" + operator,
			// Toast.LENGTH_SHORT).show();
			//
			// }
			// });
			//
			// }
			// }
			//
			// }
			// }).start();

			break;
		case R.id.closePort:

			// 关闭串口
			provider.CloseTty();

			Toast.makeText(this, "关闭串口成功！", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}

	}

}
