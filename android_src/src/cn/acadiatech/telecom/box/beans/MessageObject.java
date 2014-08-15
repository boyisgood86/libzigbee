package cn.acadiatech.telecom.box.beans;

/**
 * Json对象
 * @author QUYONG
 *
 * @param <T>
 */
public class MessageObject<T> {

	private HeadBean head;
	private T body;

	public HeadBean getHead() {
		return head;
	}

	public void setHead(HeadBean head) {
		this.head = head;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "MessageSend [head=" + head + ", body=" + body + "]";
	}

}
