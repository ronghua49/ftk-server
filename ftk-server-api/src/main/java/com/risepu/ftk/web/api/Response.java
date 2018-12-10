/**
 * 
 */
package com.risepu.ftk.web.api;

import java.io.Serializable;

/**
 * @author q-wang
 */
public class Response<T> implements Serializable {

	public static class Header implements Serializable {
		private int code;
		private String message;
		private String desc;
		private long timestamp;

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return "Header [code=" + code + ", " + (message != null ? "message=" + message + ", " : "")
					+ (desc != null ? "desc=" + desc + ", " : "") + "timestamp=" + timestamp + "]";
		}

	}
	
	private Header header;

	private T body;

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public static <T> Response<T> succeed(T body) {
		Response<T> result = new Response<>();
		Header header = new Header();
		header.code = 0;
		header.timestamp = System.currentTimeMillis();
		result.setBody(body);
		return result;
	}
	
	public static <T> Response<T> failed(int code, String message) {
		Header header = new Header();
		header.code = code;
		header.message = message;
		header.timestamp = System.currentTimeMillis();
		Response<T> result = new Response<>();
		result.setHeader(header);
		return result;
	}

}
