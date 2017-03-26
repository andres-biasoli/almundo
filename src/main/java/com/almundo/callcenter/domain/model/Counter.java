package com.almundo.callcenter.domain.model;


/**
 * @author Andres Biasoli
 * This class represents the call id
 */
public class Counter{
	
	/**
	 * The number which will be used for a call id
	 */
	private long counter;
	
	/**
	 * Constructor which initializes the count in zero
	 */
	public Counter() {
		this.counter = 0L;
	}

	/**
	 * Adds by one to the counter
	 */
	public void increment(){
		counter++;
	}
	
	/**
	 * @return the id of the call
	 */
	public long getCounter(){
		return counter;
	}

}
