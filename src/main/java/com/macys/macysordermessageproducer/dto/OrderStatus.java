package com.macys.macysordermessageproducer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

public enum OrderStatus {
	CREATED("Created"), CONFIRMED("Confirmed"), SHIPPED("Shipped");

	private String status;

	OrderStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	@Override
	public String toString() {
		return status;
	}
}
