package com.opentext.executor.model;

import java.util.UUID;
import java.util.concurrent.Callable;

public class Task<T> {

	UUID taskUUID;
	TaskGroup taskGroup;
	TaskType taskType;
	Callable<T> taskAction;


	public Task(UUID taskUUID, TaskGroup taskGroup, TaskType taskType, Callable<T> taskAction) {
		if (taskUUID == null || taskGroup == null || taskType == null || taskAction == null) {
			throw new IllegalArgumentException("All parameters must not be null");
		} 
		this.taskGroup = taskGroup;
		this.taskType = taskType;
		this.taskUUID = taskUUID;
		this.taskAction = taskAction;
	}


	public UUID getTaskUUID() {
		return taskUUID;
	}


	public void setTaskUUID(UUID taskUUID) {
		this.taskUUID = taskUUID;
	}


	public TaskGroup getTaskGroup() {
		return taskGroup;
	}


	public void setTaskGroup(TaskGroup taskGroup) {
		this.taskGroup = taskGroup;
	}


	public TaskType getTaskType() {
		return taskType;
	}


	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}


	public Callable<T> getTaskAction() {
		return taskAction;
	}


	public void setTaskAction(Callable<T> taskAction) {
		this.taskAction = taskAction;
	}


	public static <T> Callable<T> getCallableFunction(T taskInput, UUID uuid, TaskType type, TaskGroup group) {
		/*
		 * Task action callable interface implementation
		 */
		return () -> {
			// call method
			System.out.println("Task with task input "+taskInput+" of taskId "+uuid+" of task type "+type+" from task group "+group+" submitted successfully");
			Thread.sleep(10000);
			System.out.println("Task with task input "+taskInput+" of taskId "+uuid+" of task type "+type+" from task group "+group+" processing successfully");
			Thread.sleep(10000);
			System.out.println("Task with task input "+taskInput+" of taskId "+uuid+" of task type "+type+" from task group "+group+" completed successfully");
			return taskInput;
		};
	}
}
