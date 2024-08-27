package com.opentext.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import com.opentext.executor.model.Task;
import com.opentext.executor.model.TaskGroup;
import com.opentext.executor.model.TaskType;
import com.opentext.executor.service.TaskExecutor;
import com.opentext.executor.service.TaskExecutorService;

public class TaskDriver {
	
	
	public static void main(String[] args) {
		TaskExecutor executor = new TaskExecutorService();
		
		List<Task<String>> taskList = new ArrayList<Task<String>>(); 
		TaskGroup tg1 = new TaskGroup(UUID.randomUUID());
		TaskGroup tg2 = new TaskGroup(UUID.randomUUID());
		UUID task1Id = UUID.randomUUID();
		Task<String> task1 = new Task<String>(task1Id, tg1, TaskType.READ, Task.getCallableFunction("Task-1", task1Id, TaskType.READ, tg1));
		taskList.add(task1);
		UUID task2Id = UUID.randomUUID();
		Task<String> task2 = new Task<String>(task2Id, tg1, TaskType.WRITE, Task.getCallableFunction("Task-2", task2Id, TaskType.WRITE, tg1));
		taskList.add(task2);
		UUID task3Id = UUID.randomUUID();
		Task<String> task3 = new Task<String>(task3Id, tg2, TaskType.READ, Task.getCallableFunction("Task-3", task3Id, TaskType.READ, tg2));
		taskList.add(task3);
		UUID task4Id = UUID.randomUUID();
		Task<String> task4 = new Task<String>(task4Id, tg2, TaskType.WRITE, Task.getCallableFunction("Task-4", task4Id, TaskType.WRITE, tg2));
		taskList.add(task4);
		for(Task<String> task: taskList) {
			Future<String> response = executor.submitTask(task);
			CompletableFuture.runAsync(TaskExecutorService.RetrieveCompletedTasks(response));
		}
	}
}
