package com.almundo.callcenter.domain.model;

import static com.almundo.callcenter.domain.model.EmployeeTypeEnum.OPERATOR;

/**
 * @author Andres Biasoli
 * This class represents an operator
 */
public class Operator extends Employee{

	/**
	 * @param name The name of the operator
	 */
	public Operator(String name){
		super(name);
	}
	
	/** 
	 * @see com.almundo.callcenter.domain.model.Employee#getType()
	 */
	@Override
	public EmployeeTypeEnum getType() {
		return OPERATOR;
	}
	
}
