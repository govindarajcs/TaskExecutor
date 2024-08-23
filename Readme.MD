<h1>Task Executor Service</h1>

<h2>Given</h2>
    TaskExecutor Interface is the API file which has submit(Task) method to execute the task and extract the result.
    
<h2>Criteria</h2>

<ol> 
   <li>Order of job submission to execution should be preserved</li>
   <li>Read and display the result as soon as it is available</li>
   <li>Tasks from same group should not run concurrently</li>   
</ol>


<h2>Assumptions</h2>

<ol>
	<li>In the TaskGroup class, created an instance variable of type Semaphore with concurrent access to critical section of the code set to 1. This variable is is used to avoid concurrent access of the 2 threads from same group </li>
	<li>In the Driver class where the task is getting submitted, a static variable of type Queue is defined. This queue is to store the Future object of submitted tasks.</li>
	<li>The tasks submitted will be added to the blocking queue for polling.</li>
	<li>A runnable thread is created to monitor the status of the submitted job one-by-one and if completed then remove that job from the queue.</li>
	<li>TaskExecutorService implemented the interface TaskExecutor. This class also defines a constructor with the input as size that defines the number of  threads executed concurrently with the help of FixedPoolExecutorService</li>
<ol>