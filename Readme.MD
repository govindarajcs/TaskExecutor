<h1>Task Executor Service</h1>

<h2>Given</h2>
    TaskExecutor Interface is the API file which has submit(Task) method to execute the task and extract the result.
    
<h2>Criteria</h2>

<ol> 
   <li>Order of job submission to execution should be preserved</li>
   <li>Read and display the result as soon as it is available</li>
   <li>Tasks from same group should not run concurrently</li> 
   <li>Task submission should not block the submitter </li>  
</ol>


<h2>Assumptions</h2>

<ol>
	<li>In the TaskExecutorService class, created 2 instance variables
		<ol>
			<li> ConcurrentHashMap -> to control the concurrent submission of tasks from different task groups. </li>
			<li> ExecutorService -> to execute the callable threads in asynchronous manner and to limit the usage of threads </li>
		</ol>
	</li>
	<li> Submit task will create a callable child thread which in turn will call the Task action callable thread in synchronous manner i.e. directly calling call() of the Callable instance. </li>
	</li> Once the task is submitted to executor service, future object is send to a new runnable thread for polling and this is to get the object as soon as possible.
<ol>