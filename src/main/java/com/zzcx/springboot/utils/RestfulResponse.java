package com.zzcx.springboot.utils;

public class RestfulResponse<T> {
	private Integer returnCode;
	private String returnMsg;
	private T body;
	
	public void setResponse(Integer returnCode, String returnMsg) {
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}

	public Integer getReturnCode() {
		return this.returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return this.returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public T getBody() {
		return this.body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String toString() {
		return "Response{returnCode=" + this.returnCode + ", returnMsg='"
				+ this.returnMsg + '\'' + ", body=" + this.body + '}';
	}
}