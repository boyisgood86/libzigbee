package cn.acadiatech.telecom.box.db.ex;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import cn.acadiatech.telecom.box.beans.ControlBean;
import cn.acadiatech.telecom.box.db.ex.action.DeviceDBAction;
import cn.acadiatech.telecom.box.db.ex.action.ModeDBAction;
import cn.acadiatech.telecom.box.db.ex.action.ModeRecordDBAction;
import cn.acadiatech.telecom.box.db.ex.action.RoomDBAction;
import cn.acadiatech.telecom.box.db.ex.bean.Device;
import cn.acadiatech.telecom.box.db.ex.bean.Mode;
import cn.acadiatech.telecom.box.db.ex.bean.ModeRecord;
import cn.acadiatech.telecom.box.utils.CommonUtil;
import cn.acadiatech.telecom.box.utils.Constant;

public class Controller {
	Context context;
	private static Controller instance;

	public Controller(Context context) {
		this.context = context;
	}

	public static Controller getInstance(Context context) {
		if (instance == null) {
			return new Controller(context);
		}
		return instance;
	}

	/**
	 * 获得房间所有模式下的设备
	 * 
	 * @param mode_name
	 * @param roomName
	 * @return
	 */
	public List<ControlBean> controlMode(String mode_name, String roomName) {
		List<ControlBean> list = new ArrayList<ControlBean>();
		int room_type_id = RoomDBAction.getInstance(context)
				.getRoomByName(roomName).getRoomType().getRoom_type_id();
		Mode mode = ModeDBAction.getInstance(context).getModeByName(mode_name,
				room_type_id);

		if (mode != null) {
			List<ModeRecord> modeRecords = ModeRecordDBAction.getInstance(
					context).getByModeId(mode.getMode_id());
			for (ModeRecord modeRecord : modeRecords) {
				Device device = DeviceDBAction.getInstance(context)
						.getDeviceByName(
								modeRecord.getDevice().getDevice_name(),
								roomName);

				if (CommonUtil.isStringNotNull(device.getDevice_mac())) {

					if (device.getDeviceType().getDevice_type() == Constant.TV_VIEW) {

						continue;
					}

					ControlBean controlBean = new ControlBean(
							device.getDevice_mac(), device.getDeviceType()
									.getDevice_type(),
							modeRecord.getDevice_progress());
					list.add(controlBean);

				}

			}
		}
		return list;
	}

	/**
	 * 获得房间所有该类型的设备
	 * 
	 * @param deviceTypeid
	 *            设备id
	 * @param roomName
	 *            房间号
	 * @return
	 */
	public List<Device> controlMode(int deviceTypeid, String roomName) {
		List<Device> list = new ArrayList<Device>();
		List<Device> devices = DeviceDBAction.getInstance(context)
				.getDeviceByTypeAndRoomName(deviceTypeid, roomName);

		if (devices != null && devices.size() != 0) {
			for (Device device : devices) {

				if (CommonUtil.isStringNotNull(device.getDevice_mac())) {

//					if (device.getDeviceType().getDevice_type() == Constant.TV_VIEW) {
//
//						continue;
//					}

					list.add(device);

				}

			}
		}
		return list;
	}

	/**
	 * 获得房间所有设备
	 * 
	 * @param roomName
	 *            房间名
	 * @return
	 */
	public List<ControlBean> controlMode(String roomName) {
		List<ControlBean> list = new ArrayList<ControlBean>();
		List<Device> devices = DeviceDBAction.getInstance(context)
				.getDeviceByRoomName(roomName);

		if (devices != null && devices.size() != 0) {
			for (Device device : devices) {

				if (CommonUtil.isStringNotNull(device.getDevice_mac())) {

//					if (device.getDeviceType().getDevice_type() == Constant.TV_VIEW) {
//
//						continue;
//					}

					ControlBean controlBean = new ControlBean(
							device.getDevice_mac(), device.getDeviceType()
									.getDevice_type(), 0);
					list.add(controlBean);

				}

			}
		}
		return list;
	}
	/**
	 * 获得房间所有设备
	 * 
	 * @param roomName
	 *            房间名
	 * @return
	 */
	public List<ControlBean> controlModeRead(String roomName) {
		List<ControlBean> list = new ArrayList<ControlBean>();
		List<Device> devices = DeviceDBAction.getInstance(context)
				.getDeviceByRoomName(roomName);
		
		if (devices != null && devices.size() != 0) {
			for (Device device : devices) {
				
				if (CommonUtil.isStringNotNull(device.getDevice_mac())) {
					
//					if (device.getDeviceType().getDevice_type() == Constant.TV_VIEW) {
//
//						continue;
//					}
					
					ControlBean controlBean = new ControlBean(
							device.getDevice_mac(), device.getDeviceType()
							.getDevice_type(), device.getDevice_progress());
					list.add(controlBean);
					
				}
				
			}
		}
		return list;
	}

	/**
	 * 获得所有设备
	 * 
	 * @param roomName
	 *            房间名
	 * @return
	 */
	public List<Device> controlMode() {
		List<Device> list = new ArrayList<Device>();
		List<Device> devices = DeviceDBAction.getInstance(context).getDevice();

		if (devices != null && devices.size() != 0) {
			for (Device device : devices) {

				if (CommonUtil.isStringNotNull(device.getDevice_mac())) {

					if (device.getDeviceType().getDevice_type() == Constant.TV_VIEW) {

						continue;
					}

					list.add(device);

				}

			}
		}
		return list;
	}

}
