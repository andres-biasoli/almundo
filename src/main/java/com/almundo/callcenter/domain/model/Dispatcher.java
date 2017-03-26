package com.almundo.callcenter.domain.model;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.apache.log4j.Logger;

import com.almundo.callcenter.domain.concurrency.CallHandler;
import com.almundo.callcenter.domain.concurrency.CallHandlerManager;
import com.almundo.callcenter.domain.concurrency.CallThread;

/**
 * 
 * @author Andres Biasoli
 * This class is responsible for dispatching calls concurrently
 */
public class Dispatcher {
	
	private final static Logger logger = Logger.getLogger(Dispatcher.class);
	
	/**
	 * The call handler manager which groups and assigns all kinds of operators {@link CallHandlerManager}
	 */
	private CallHandlerManager callHandlerManager;
	/**
	 * The calls which could not be dispatched at the time they were made due to unavailable operators
	 */
	private Set<CallThread> callsOnHold;
	/**
	 * A Map which contains messages of calls kept on hold only for logging purposes and to avoid duplicate messages. 
	 */
	private Map<Long, String> messages;
	
	/**
	 * The dispatcher constructor
	 */
	public Dispatcher(){
		this.callHandlerManager = CallHandlerManager.getInstance();
		this.callsOnHold = new HashSet<CallThread>();
		this.messages = new HashMap<Long, String>();
	}
	
	/**
	 * @param calls The queue containing the batch of calls being produced. 
	 * @throws InterruptedException
	 * This method attempts to dispatch the calls previously querying for an available call handler. 
	 * If all handlers are busy, the call is kept on hold until a call handler becomes available. 
	 * It's important to notice that the purpose of using a queue and to check for a handler before hand, 
	 * is to respect the order in which the calls were made. It didn't sound fair to me that the call handler
	 * would be searched after all calls are dispatched. This behavior would result in having later calls
	 * being answered first than earlier calls. I think the main purpose of a call center is to answer calls
	 * in the order they arrive. That's a First In First Out policy.  
	 */
	public void dispatchCall(Queue<CallThread> calls) throws InterruptedException{
		 
		while(!calls.isEmpty()){
			CallThread call = calls.peek();
			CallHandler handler = callHandlerManager.getAvailableHandler();
			if(handler != null){
				logger.info("El " + handler.getName() + " esta disponible");
				call.setCallHandler(handler);
				makeCall(call);
				calls.poll();
			}else{
				logCallOnHold(call);
			}
			Thread.sleep(100);
		}
	}
	
	/**
	 * @param call The call thread to be started
	 * Starts a thread with the call
	 */
	private void makeCall(CallThread call) {
		call.start();
	}
	
	/**
	 * @param call The call on hold to be logged.
	 * Provides a log of calls which could not be dispatched. 
	 */
	/**
	 * @param call The call on hold that is about to be verified if it's already logged. 
	 */
	private void logCallOnHold(CallThread call) {
		callsOnHold.add(call); // Adds the call which could not be dispatched and was kept on hold just to leave a record.
		if(!messages.containsKey(call.getId())){
			String message = "Call " + call.getCallId() + ": quedo en espera por falta de operadores";
			messages.put(call.getId(), message);
			logger.info(message);
		}
	}

	/**
	 * @param callHandlerManager Sets a call handler manager
	 */
	public void setCallHandlerManager(CallHandlerManager callHandlerManager) {
		this.callHandlerManager = callHandlerManager;
	}
	
	/**
	 * @return A list without duplicates of calls kept on hold
	 */
	public Set<CallThread> getCallsOnHold() {
		return callsOnHold;
	}

	/**
	 * @return the call handler manager
	 */
	public CallHandlerManager getCallHandlerManager() {
		return callHandlerManager;
	}

	/**
	 * @return a map of messages of calls which were kept on hold
	 */
	public Map<Long, String> getMessages() {
		return messages;
	}

	/**
	 * @param messages Sets a map of calls messages which were kept on hold
	 */
	public void setMessages(Map<Long, String> messages) {
		this.messages = messages;
	}
	
}
