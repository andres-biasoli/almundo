package com.almundo.callcenter.domain.model;

import static com.almundo.callcenter.domain.model.EmployeeTypeEnum.DIRECTOR;

/**
 * @author Andres Biasoli
 * This class represents a Director
 */
public class Director extends Employee{

	/**
	 * @param name The name of the director
	 */
	public Director(String name){
		super(name);
	}

	/** 
	 * @see com.almundo.callcenter.domain.model.Employee#getType()
	 */
	@Override
	public EmployeeTypeEnum getType() {
		return DIRECTOR;
	}
}
