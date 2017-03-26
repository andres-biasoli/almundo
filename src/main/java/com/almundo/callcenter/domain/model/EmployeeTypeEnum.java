package com.almundo.callcenter.domain.model;

public enum EmployeeTypeEnum {

	OPERATOR("Operator") {
		@Override
		public Employee get(String name) {
			return new Operator(name);
		}
	
	}, SUPERVISOR("Supervisor") {
		@Override
		public Employee get(String name) {
			return new Supervisor(name);
		}
	
	}, DIRECTOR("Director") {
		@Override
		public Employee get(String name) {
			return new Director(name);
		}
	};
	
	private String type;
	
	private EmployeeTypeEnum(String type){
		this.type = type;
	}
	
	public static EmployeeTypeEnum getByType(String type){
		return EmployeeTypeEnum.valueOf(type);
	}
	
	public String getType() {
		return type;
	}
	
	public abstract Employee get(String name);
	
}
