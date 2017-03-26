package com.almundo.callcenter.domain.model;

import java.util.Date;

import org.apache.log4j.Logger;

import com.almundo.callcenter.domain.concurrency.CallHandler;

/**
 * @author Andres Biasoli
 * This is an abstract class which acts as a superclass for the diverse employee categories.
 */
public abstract class Employee implements CallHandler{
	
	private final static Logger logger = Logger.getLogger(Employee.class);
	
	/**
	 * Means if the employee is busy answering a call or available.
	 */
	private boolean isAvailable;
	/**
	 * The name of the employee
	 */
	private String name;
	
	/**
	 * @param name of the employee
	 * By default all employees start being available
	 */
	public Employee(String name) {
		this.name = name;
		this.isAvailable = true;
	}

	/**
	 * @return the category of the employee
	 */
	public abstract EmployeeTypeEnum getType();
	
	/**
	 * Handles the call to be answered by the employee
	 * It simulates the time of the call that is on course. 
	 * ***Important: Due to very common behavior between all different handlers,
	 * I chose to implement this method in the abstract class to avoid duplicate code,
	 * rather than having it overriden in the respective subclasses making use of polymorphism.
	 * On the other hand, polymorphism can be seen on the getType() method.  
	 */
	public void handle(Call call) throws InterruptedException {
		
		synchronized (this) {
			logger.info("Call " + call.getCallId() + " siendo respondida por " + getType() + " " + this.name);
			Thread.sleep(call.getDuration());
			call.setResolutionDate(new Date());
			call.setObservations("Call " + call.getCallId() + " finalizada por " + getType() + " " + this.name);
			this.isAvailable = true;
			logger.info(call.getObservations());
		}
	}
	
	/** 
	 * @see com.almundo.callcenter.domain.concurrency.CallHandler#isAvailable()
	 */
	public boolean isAvailable() {
		return isAvailable;
	}

	/** 
	 * @see com.almundo.callcenter.domain.concurrency.CallHandler#setAvailable(boolean)
	 */
	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	/** 
	 * @see com.almundo.callcenter.domain.concurrency.CallHandler#getName()
	 */
	public String getName() {
		return name;
	}
	
}
