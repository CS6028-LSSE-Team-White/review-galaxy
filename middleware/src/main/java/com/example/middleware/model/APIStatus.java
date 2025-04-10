package com.example.middleware.model;

public enum APIStatus {
	SUCCESS("success"),
	ERROR("error");
	private String status;

	APIStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return status;
	}
}
