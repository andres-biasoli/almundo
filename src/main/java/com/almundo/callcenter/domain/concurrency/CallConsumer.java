package com.almundo.callcenter.domain.concurrency;


import java.util.Queue;

import org.apache.log4j.Logger;

import com.almundo.callcenter.domain.model.Dispatcher;

/**
 * @author Andres Biasoli
 * This class represents a call consumer in a producer/consumer schema and works as a thread. It waits until there are the amount of calls 
 * provided by the {@link CallWorker} class to be pulled from the queue, and as soon as the batch is ready, it begins with its work.
 */
public class CallConsumer extends CallWorker implements Runnable{
	
	
	private final static Logger logger = Logger.getLogger(CallConsumer.class);
	/**
	 * {@link Dispatcher}
	 */
	private Dispatcher dispatcher;
	
	/**
	 * @param calls
	 * A callConsumer must be created with a queue of calls
	 */
	public CallConsumer(Queue<CallThread> calls){
		super(calls);
		this.dispatcher = new Dispatcher();
	}
	
	/**
	 * The run implementation of this thread. If running is active, while there are calls in the queue, it will try to dispatch them. 
	 * If it succeeds dispatching them, the call is removed from the queue and will follow with the next one.
	 * Otherwise, it will keep attempting to dispatch the call, logging the event, until an operator becomes available.
	 */
	public void run(){
		try{
			while(this.isRunning()) {
		
				synchronized(this.getCalls()){
					
					if(this.getCalls().size() == 0){
						waitCalls();
					}
					
				    if(this.getCalls().size() == MAX){
						this.dispatcher.dispatchCall(getCalls());
						logger.info("*********** Lote de llamadas consumido ***********");
					}
					
					notifyCalls();
					
				}
			}
		}catch(InterruptedException e){
			logger.error("Me interrumpieron");
		}
	}
	
	/**
	 * @return The dispatcher of calls
	 */
	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * @param dispatcher Sets the dispatcher for the CallConsumer
	 */
	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	/**
	 * Initializes the CallConsumer class as a thread
	 */
	@Override
	public void start() {
		this.thread = new Thread(this);
		this.thread.start();
	}
	

}
