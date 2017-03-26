package com.almundo.callcenter.domain.model;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.almundo.callcenter.domain.concurrency.CallConsumer;
import com.almundo.callcenter.domain.concurrency.CallProducer;
import com.almundo.callcenter.domain.concurrency.CallThread;
import com.almundo.callcenter.domain.concurrency.CallWorker;

/**
 * @author Andres Biasoli
 * This class is the wrapper of the system. Starts and closes the call center.
 */
public class CallCenter {

	/**
	 * The queue of calls to be answered by the operators
	 */
	private Queue<CallThread> calls = new LinkedBlockingQueue<CallThread>();
	/**
	 * A Call Producer which simulates people making calls
	 */
	private CallWorker producer = new CallProducer(calls, new Counter());
	/**
	 * A Call Consumer which administrates and consumes the calls produced by the Call Producer
	 */
	private CallWorker consumer = new CallConsumer(calls);
	
	/**
	 * This method acts as a kick start
	 */
	public void open() {
		this.producer.start();
		this.consumer.start();
	}
	
	/**
	 * Stops the call center
	 */
	public void close(){
		this.producer.stop();
		this.consumer.stop();
	}
}
