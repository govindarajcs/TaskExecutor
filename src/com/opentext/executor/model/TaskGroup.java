package com.opentext.executor.model;

import java.util.UUID;

public class TaskGroup {

	public TaskGroup(UUID taskId) {
		if(taskId == null) {
			throw new IllegalArgumentException("All parameters must not be null");
		}
	}
}
