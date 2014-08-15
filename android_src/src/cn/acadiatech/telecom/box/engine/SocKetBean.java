package cn.acadiatech.telecom.box.engine;

import cn.acadiatech.telecom.box.beans.HeadBean;
import cn.acadiatech.telecom.box.beans.UserBean;

/** 
* @ClassName: SocKetBean 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author quyong1986@gmail.com  
* @date 2014年7月25日 下午1:39:55 
*  
*/
public class SocKetBean {
	private long size;
	private String filename;
	private String sourceid;
	private String filepath;
	private UserBean user;
	private HeadBean head;

	/**
	 * @return size
	 */
	public long getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(long size) {
		this.size = size;
	}

	/**
	 * @return filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename
	 *            the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return sourceid
	 */
	public String getSourceid() {
		return sourceid;
	}

	/**
	 * @param sourceid
	 *            the sourceid to set
	 */
	public void setSourceid(String sourceid) {
		this.sourceid = sourceid;
	}

	/**
	 * @return filepath
	 */
	public String getFilepath() {
		return filepath;
	}

	/**
	 * @return user
	 */
	public UserBean getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(UserBean user) {
		this.user = user;
	}

	/**
	 * @return head
	 */
	public HeadBean getHead() {
		return head;
	}

	/**
	 * @param head
	 *            the head to set
	 */
	public void setHead(HeadBean head) {
		this.head = head;
	}

	/**
	 * 
	* @Title: setFilepath 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param filepath    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

}
