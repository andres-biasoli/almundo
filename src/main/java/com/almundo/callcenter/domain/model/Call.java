package com.almundo.callcenter.domain.model;


import java.util.Date;

/**
 * @author Andres Biasoli
 * This class represents a call domain entity. 
 */
public class Call{
	
	/**
	 * A constant which sets a maximum limit to the duration of a call
	 */
	public static final Long MAX_DURATION = 10000L;
	/**
	 * A constant which sets a minimum limit to the duration of a call
	 */
	public static final Long MIN_DURATION = 5000L;
	
	/**
	 * The id of the call
	 */
	private Long callId;
	/**
	 * The resulting duration of the call
	 */
	private Long duration;
	/**
	 * The resolution date of the call
	 */
	private Date resolutionDate;
	/**
	 * The observations of who answered the call
	 */
	private String observations;

	/**
	 * @param id the call id
	 * @param duration the duration of the call
	 */
	public Call(Long id, Long duration) {
		this.callId = id;
		this.duration = duration;
	}

	/**
	 * @return the id of the call
	 */
	public Long getCallId() {
		return callId;
	}
	
	/**
	 * @return the duration of the call
	 */
	public Long getDuration() {
		return duration;
	}

	/**
	 * @return the resolution date of the call
	 */
	public Date getResolutionDate() {
		return resolutionDate;
	}

	/**
	 * @param resolutionDate of the call
	 */
	public void setResolutionDate(Date resolutionDate) {
		this.resolutionDate = resolutionDate;
	}

	/**
	 * @return observations of the call
	 */
	public String getObservations() {
		return observations;
	}

	/**
	 * @param observations of the call
	 */
	public void setObservations(String observations) {
		this.observations = observations;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((callId == null) ? 0 : callId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Call other = (Call) obj;
		if (callId == null) {
			if (other.callId != null)
				return false;
		} else if (!callId.equals(other.callId))
			return false;
		return true;
	}
	
	
}
