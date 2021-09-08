package com.jtriemstra.wonders.api.notifications;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	//TODO: split this off to a microservice, back it with something other than memory, and deal with removing old values
	//TOOD: allow for separation by game, so multiple games can be running
	
	private ArrayList<Notification> messages = new ArrayList<>();
	private AtomicInteger nextId = new AtomicInteger(0);
	
	public void addNotification(String message) {
		int id = nextId.getAndIncrement();
		messages.add(new Notification(message, id, LocalTime.now()));
	}
	
	public List<Notification> getListSinceId(int id) {
		if (messages.size() > 0) {
			int currentIndex = messages.size() - 1;
			while (currentIndex >= 0 && messages.get(currentIndex).getIdentifier() != id) {
				currentIndex--;
			}
			return messages.subList(currentIndex + 1, messages.size());
		}
		
		return new ArrayList<>();
	}
}
