package com.almundo.callcenter.domain.concurrency;


import java.util.Date;

import com.almundo.callcenter.domain.model.Call;

/**
 * @author Andres Biasoli
 * This class represents a Thread to make the call concurrent and give a race condition to compete with the rest.
 */
public class CallThread extends Thread{
	
	/**
	 * The id of the call.
	 */
	private Long callId;
	/**
	 * The date in which the call is answered by a handler.
	 */
	private Date resolutionDate;
	/**
	 * The observations made by the handler during the call.
	 */
	private String observations;
	/**
	 * The handler that will manage the call. 
	 */
	private CallHandler callHandler;
	/**
	 * The duration of the call.
	 */
	private Long duration;
	
	/**
	 * @param callId The id of the call.
	 * @param duration the duration of the call.
	 * A CallThread is constructed by the callId and the duration
	 */
	public CallThread(Long callId, Long duration) {
		this.callId = callId;
		this.duration = duration;
	}

	/**
	 * The run implementation of this thread. 
	 */
	@Override
	public void run() {
		try {
			if(callHandler != null){
				Call call = new Call(callId, duration);
				callHandler.handle(call);
			}
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the duration of the call
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * @return the id of the call
	 */
	public Long getCallId() {
		return callId;
	}

	/**
	 * @return the resolution date of the call
	 */
	public Date getResolutionDate() {
		return resolutionDate;
	}

	/**
	 * @return the observations of the call
	 */
	public String getObservations() {
		return observations;
	}

	/**
	 * @param callId The id of the call
	 */
	public void setCallId(Long callId) {
		this.callId = callId;
	}

	/**
	 * @param resolutionDate The resolution date of the call
	 */
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	/**
	 * @param observations The observations of the call
	 */
	public void setObservations(String observations) {
		this.observations = observations;
	}

	/**
	 * @return A call handler to answer the call
	 */
	public CallHandler getCallHandler() {
		return callHandler;
	}

	/**
	 * @param callHandler Sets a call handler to answer the call
	 */
	public void setCallHandler(CallHandler callHandler) {
		this.callHandler = callHandler;
	}
	
}
