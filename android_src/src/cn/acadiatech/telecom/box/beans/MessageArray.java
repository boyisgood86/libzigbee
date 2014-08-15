package cn.acadiatech.telecom.box.beans;

import java.util.List;

public class MessageArray<T> {

	private HeadBean head;
	private List<T> body;

	public HeadBean getHead() {
		return head;
	}

	public void setHead(HeadBean head) {
		this.head = head;
	}

	public List<T> getBody() {
		return body;
	}

	public void setBody(List<T> body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "MessageArray [head=" + head + ", body=" + body + "]";
	}

}
