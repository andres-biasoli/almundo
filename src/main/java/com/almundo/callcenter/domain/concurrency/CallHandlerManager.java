package com.almundo.callcenter.domain.concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.almundo.callcenter.utils.XMLCallHandlerConfiguration;

/**
 * @author Andres Biasoli
 * This class is in charge of managing the call handlers which will answer the calls
 */
public class CallHandlerManager {

	/**
	 * Singleton Instance of @{CallHandlerManager}
	 */
	private static CallHandlerManager instance;
	
	
	/**
	 * Instantiates the CallHandlerManager instance
	 */
	private synchronized static void create(){
		
		if(instance == null){
			instance = new CallHandlerManager();
		}
	}

	/**
	 * @return CallHandlerManager instance
	 */
	public static CallHandlerManager getInstance(){
		
		if(instance == null){
			create();
		}
		return instance;
	}
	
	/**
	 * The list of call handlers
	 */
	private List<CallHandler> handlers;
	
	/**
	 * A call handler manager has a group of handlers under its control. As soon as it is created,
	 * the list of handlers are ready to answers calls. The list if populated via XML file through @{XMLCallHandlerConfiguration} class.
	 */
	private CallHandlerManager(){
		this.handlers = Collections.synchronizedList(new ArrayList<CallHandler>());
		XMLCallHandlerConfiguration.populateHandlers(handlers);
	}
	
	/**
	 * @return The handler that is without a call on the phone
	 */
	public CallHandler getAvailableHandler(){
		synchronized(handlers) {
		    Iterator<CallHandler> it = handlers.iterator(); 
		    while (it.hasNext()){
		        CallHandler handler = it.next();
		        if(handler.isAvailable()){
					handler.setAvailable(false);
					return handler;
				}
		    }
		    return null;
		}
	}

	/**
	 * @return the call handlers
	 */
	public List<CallHandler> getHandlers() {
		return handlers;
	}

	/**
	 * @param handlers The call handlers
	 */
	public void setHandlers(List<CallHandler> handlers) {
		this.handlers = handlers;
	}
}
