package com.opentext.executor.service;

import java.util.concurrent.Future;

import com.opentext.executor.model.Task;

public interface TaskExecutor {
    /**
     * Submit new task to be queued and executed.
     *
     * @param task Task to be executed by the executor. Must not be null.
     * @return Future for the task asynchronous computation result.
     */
    <T> Future<T> submitTask(Task<T> task);
}
