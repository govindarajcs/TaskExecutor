package com.opentext.executor.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Semaphore;

public class TaskGroup {
	
	String name;
	List<UUID> taskIdList;
	Semaphore countSemaphore;
	
	

	public TaskGroup(UUID taskId, String name) {
		if(taskId == null) {
			throw new IllegalArgumentException("All parameters must not be null");
		} else {
			if(taskIdList == null) {
				taskIdList = new ArrayList<UUID>();
				countSemaphore = new Semaphore(1);
			}
			this.name=name;
			taskIdList.add(taskId);
		}
	}
	
		
	public String getName() {
		return name;
	}


	public void addTask(UUID taskId) {
		taskIdList.add(taskId);
	}

	public List<UUID> getTaskIdList() {
		return taskIdList;
	}

	public Semaphore getCountSemaphore() {
		return countSemaphore;
	}

	public void setCountSemaphore(Semaphore countSemaphore) {
		this.countSemaphore = countSemaphore;
	}


}
