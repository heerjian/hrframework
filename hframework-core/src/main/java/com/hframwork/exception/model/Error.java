package com.hframwork.exception.model;

import java.io.Serializable;

public class Error implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private Object msg;

	public Error() {
	}

	public Error(String name, Object msg) {
		super();
		this.name = name;
		this.msg = msg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}
}
