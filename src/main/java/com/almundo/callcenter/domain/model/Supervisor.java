package com.almundo.callcenter.domain.model;

import static com.almundo.callcenter.domain.model.EmployeeTypeEnum.SUPERVISOR;

/**
 * @author Andres Biasoli
 * This class represents a supervisor
 */
public class Supervisor extends Employee{

	/**
	 * @param name The name of the supervisor
	 */
	public Supervisor(String name){
		super(name);
	}
	
	/** 
	 * @see com.almundo.callcenter.domain.model.Employee#getType()
	 */
	@Override
	public EmployeeTypeEnum getType() {
		return SUPERVISOR;
	}
}
