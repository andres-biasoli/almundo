package com.almundo.callcenter.domain.concurrency;


import static com.almundo.callcenter.domain.model.Call.MAX_DURATION;
import static com.almundo.callcenter.domain.model.Call.MIN_DURATION;

import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.log4j.Logger;

import com.almundo.callcenter.domain.model.Counter;

/**
 * @author Andres Biasoli
 * This class represents a call producer in a producer/consumer schema and works as a thread. It starts producing the amount of calls given by 
 * the {@link CallWorker} class and waits when it reaches the batch limit.  
 */
public class CallProducer extends CallWorker implements Runnable {
	
	private final static Logger logger = Logger.getLogger(CallProducer.class);
	
	/**
	 * A counter which increments its value by one every time it is called. It is used to provide a call id.
	 */
	private Counter counter;
	
	/**
	 * @param calls The queue of calls
	 * @param counter The counter for the call's Id.
	 */
	public CallProducer(Queue<CallThread> calls, Counter counter){
		super(calls);
		this.counter = counter;
	}
	
	/**
	 * The run implementation of this thread. If running is active, it produces calls until it reaches the limit of the batch. 
	 * The call id is got from the Counter object. The call is enqueued and notified to the consumer.
	 * Once it gets to the limit, it waits until it is waken up by a call consumer to start the process again. 
	 */
	public void run(){
		try{
			while(this.isRunning()) {
				
				synchronized(this.getCalls()){
					CallThread call = produce();
					
					while(this.getCalls().size() >= MAX){
						waitCalls();
					}
					
					incrementCounter();
					enqueue(call);
					notifyCalls();
				}
			}
		
		}catch(InterruptedException e){
			logger.error("Me interrumpieron");
		}
	}

	/**
	 * @return The call that is created by a counter and a random duration between 5 and 10 seconds.
	 */
	private CallThread produce() {
		return new CallThread(new Long(counter.getCounter()), ThreadLocalRandom.current().nextLong(MIN_DURATION,MAX_DURATION));
	}

	/**
	 * @param call The call to be put in the queue.
	 */
	private void enqueue(CallThread call) {
		this.getCalls().add(call);
	}

	/**
	 * Tells the counter to increment its value
	 */
	private void incrementCounter() {
		counter.increment();
	}

	/**
	 * @return the counter
	 */
	public Counter getCounter() {
		return counter;
	}
	
	/**
	 * Initializes the CallProducer class as a thread
	 */
	@Override
	public void start(){
		this.thread = new Thread(this);
		this.thread.start();
	}

}
