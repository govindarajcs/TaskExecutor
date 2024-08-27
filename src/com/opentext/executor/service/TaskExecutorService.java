package com.opentext.executor.service;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import javax.annotation.PreDestroy;

import com.opentext.executor.model.Task;
import com.opentext.executor.model.TaskGroup;

public class TaskExecutorService implements TaskExecutor {

	ExecutorService executor;
	// Data structure to accept submitting tasks of other task groups
	ConcurrentHashMap<TaskGroup, Semaphore> taskGroupConcurrency;
	public TaskExecutorService() {
		super();
		executor = Executors.newFixedThreadPool(10);
		taskGroupConcurrency = new ConcurrentHashMap<TaskGroup, Semaphore>();
	}

	@PreDestroy
	public void destroy() {
		this.executor.shutdown();
	}

	@Override
	public <T> Future<T> submitTask(Task<T> task) {
		Future<T> response = null;
		if(task!=null) {
			response = executor.submit(getTaskSubmissionThread(task));
		}

		return response;
	}


	public <T> Callable<T> getTaskSubmissionThread(Task<T> task) {
		/*
		 * Method to execute the task submission thread 
		 */
		return ()-> {
			T t = null;
			try {
				// Initializing the value of the task group to Semaphore with permit 1 to make only one thread to be executed concurrently
				this.taskGroupConcurrency.putIfAbsent(task.getTaskGroup(), new Semaphore(1)); 
				Semaphore tgSemaphore = this.taskGroupConcurrency.get(task.getTaskGroup());
				tgSemaphore.acquire();
				// task action call should be synchronous so that task group will be locked by a single thread of that group till the task is completed.
				t = task.getTaskAction().call();
				tgSemaphore.release();
			} catch (InterruptedException e) {
				System.out.println("Task "+task.getTaskUUID()+" submission failed due to "+e.getMessage());
			}
			return t;
		};
	}

	public static <T> Runnable RetrieveCompletedTasks(Future<T> t) {
		/*
		 * Runnable thread to get the completed task result
		 */
		return () -> {
			while(!t.isDone())
				try {
					System.out.println("Task "+t.get()+" is completed Successfully");
				} catch (InterruptedException | ExecutionException e) {
					System.out.println("Task execution failed due to "+e.getMessage());
				}
		};
	}
}
