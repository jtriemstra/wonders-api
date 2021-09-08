package com.jtriemstra.wonders.api.notifications;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:8001", "https://master.d1rb5aud676z7x.amplifyapp.com"})
public class NotificationController {
	@Autowired
	NotificationService service;
	
	@RequestMapping("/notifications")
	public List<Notification> getList(int lastId) {
		return service.getListSinceId(lastId);
	}
}
