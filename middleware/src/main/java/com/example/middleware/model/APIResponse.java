package com.example.middleware.model;
import java.sql.Date;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIResponse {
	private APIStatus status;
	private String message;
	private LocalDateTime time;

	public APIResponse(APIStatus status, String message, LocalDateTime time) {
		this.status = status;
		this.message = message;
		this.time = time;
	}

	public APIResponse(APIStatus status, String message) {
		this.status = status;
		this.message = message;
		this.time = LocalDateTime.now();
	}
}
