package com.almundo.callcenter.domain.concurrency;

import java.util.Queue;

/**
 * @author Andres Biasoli
 * An abstract entity which holds common behavior for call workers.
 */
public abstract class CallWorker {
	
	/**
	 * The total amount of calls to be produced/consumed at a time that will fill in a processing batch.
	 */
	public static final int MAX = 10;
	/**
	 * Indicates if the worker is active or not to process calls.
	 */
	private boolean running;
	/**
	 * The queue of calls to dispatch.
	 */
	private Queue<CallThread> calls;
	/**
	 * The thread on which the worker will run.
	 */
	protected Thread thread;
	
	/**
	 * @param calls The queue of calls
	 */
	public CallWorker(Queue<CallThread> calls) {
		this.running = true;
		this.calls = calls;
	}

	/**
	 * This method represents the starting point of the thread.
	 */
	public abstract void start();

	/**
	 * This method interrupts the current worker thread if there is any.
	 */
	public void stop(){
		if(this.thread != null){
			this.thread.interrupt();
		}
	}
	
	/**
	 * @throws InterruptedException
	 * This method is used to make the worker thread sleep until it is waken up. It is based on the queue's monitor object.
	 */
	protected void waitCalls() throws InterruptedException {
		this.calls.wait();
	}

	/**
	 * This method is used to wake up the other worker thread that was sleeping. It is based on the queue's monitor object.
	 */
	protected void notifyCalls() {
		this.calls.notify();
	}
	
	/**
	 * @return True if it's active. False if it's disabled.
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * @param running Enables or disables the processing
	 */
	public void setRunning(boolean running) {
		this.running = running;
	}

	/**
	 * @return The queue of calls
	 */
	public Queue<CallThread> getCalls() {
		return calls;
	}

	/**
	 * 
	 * @param calls Sets the queue of calls
	 */
	public void setCalls(Queue<CallThread> calls) {
		this.calls = calls;
	}
	
}
