package com.opentext.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import com.opentext.executor.model.Task;
import com.opentext.executor.model.TaskGroup;
import com.opentext.executor.model.TaskType;
import com.opentext.executor.service.TaskExecutor;
import com.opentext.executor.service.TaskExecutorService;

public class TaskDriver {
	
	
	static Queue<Future> futureTaskQueue = new LinkedBlockingQueue<Future>();

	public static <T> Callable<T> getCallableFunction(UUID uuid, TaskGroup group, TaskType type) {
		return ()-> {
			T t = null;
			group.getCountSemaphore().acquire();
			Thread.sleep(10000);
			System.out.println("Task "+uuid.toString()+" of group "+group.getName()+" for task type "+type.name()+" is triggered");
			Thread.sleep(10000);
			System.out.println("Task "+uuid.toString()+" of group "+group.getName()+" for task type "+type.name()+" is processing");
			Thread.sleep(10000);
			System.out.println("Task "+uuid.toString()+" of group "+group.getName()+" for task type "+type.name()+" is completed");
			group.getCountSemaphore().release();
			return t;
		};
	}
	
	
	static public Runnable getFutureObject() {
		return () -> {
			while(true) {
				if(futureTaskQueue.size()>0) {
					Future response = TaskDriver.futureTaskQueue.poll();
					if(response!=null && response.isDone()) {
						System.out.println("Task Completed");
					} else {
						futureTaskQueue.add(response);
					}
				}
			}
		};
	}

	
	public static<T> void main(String[] args) throws InterruptedException, ExecutionException {

		Thread th = new Thread(getFutureObject());
		TaskExecutor executor = new TaskExecutorService(10);
		th.start();
		
		List<Task<T>> taskList = new ArrayList<Task<T>>();
		UUID writeTask1UUID = UUID.randomUUID();
		UUID readTask1UUID = UUID.randomUUID();
		TaskGroup group1 = new TaskGroup(writeTask1UUID, "TG1");
		group1.addTask(readTask1UUID);


		Task<T> writeTask1 = new Task<T>(writeTask1UUID, group1, TaskType.WRITE, getCallableFunction(writeTask1UUID, group1, TaskType.WRITE));
		Task<T> readTask1 = new Task<T>(readTask1UUID, group1, TaskType.READ, getCallableFunction(readTask1UUID, group1, TaskType.READ));
		taskList.add(writeTask1);
		taskList.add(readTask1);

		UUID writeTask2UUID = UUID.randomUUID();
		UUID readTask2UUID = UUID.randomUUID();
		TaskGroup group2 = new TaskGroup(writeTask2UUID, "TG2");
		group2.addTask(readTask2UUID);


		Task<T> writeTask2 = new Task<T>(writeTask2UUID, group2, TaskType.WRITE, getCallableFunction(writeTask2UUID, group2, TaskType.WRITE));
		Task<T> readTask2 = new Task<T>(readTask2UUID, group2, TaskType.READ, getCallableFunction(readTask2UUID, group2, TaskType.READ));
		taskList.add(writeTask2);
		taskList.add(readTask2);

		
		Future[] futureList = new Future[taskList.size()];
		int count = 0;
		for(Task<T> task: taskList) {
			futureList[count] = executor.submitTask(task);
			futureTaskQueue.add(futureList[count]);
			count++;
		}
		
		th.join();
	
	}

}
