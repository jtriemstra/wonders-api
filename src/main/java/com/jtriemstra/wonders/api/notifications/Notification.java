package com.jtriemstra.wonders.api.notifications;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Notification {
	private String message;
	private int identifier;
	private LocalTime timestamp;
}
