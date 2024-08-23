package com.opentext.executor.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.opentext.executor.model.Task;

public class TaskExecutorService implements TaskExecutor {
	ExecutorService executor;
	int size;
	
	public TaskExecutorService(int size) {
		super();
		this.size = size;
		executor = Executors.newFixedThreadPool(size);
	}

	@Override
	public <T> Future<T> submitTask(Task<T> task) {
		Future<T> response = null;
		if(task!=null) {
			try {
				Thread.sleep(1000);
				response = executor.submit(task.getTaskAction());
			} catch (InterruptedException e) {
				System.out.println("Task "+task.getTaskUUID()+" submission failed due to "+e.getMessage());
			}
			
		}
		return response;
	}
	
	
	
}
