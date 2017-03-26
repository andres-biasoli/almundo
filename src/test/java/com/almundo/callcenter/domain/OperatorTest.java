package com.almundo.callcenter.domain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Call;
import com.almundo.callcenter.domain.model.Operator;

public class OperatorTest {

	private Operator operator;
	
	@Before
	public void setup(){
		this.operator = new Operator("op1");
	}
	
	@Test
	public void whenHandleMethodIsInvokedByThread_CallMustHaveDateResolutionAndObservations() throws InterruptedException{
		Long callId = 1L;
		String observations = "Call " + callId + " finalizada por OPERATOR " + operator.getName();
		Call call = new Call(callId, 5000L);
		this.operator.handle(call);
		assertNotNull(call.getResolutionDate());
		assertEquals(observations, call.getObservations());
		assertTrue(operator.isAvailable());
	}
	
	@Test
	public void whenEmployeeTypeIsCalled_OPERATORisReturned(){
		String expected = "OPERATOR";
		String actual = this.operator.getType().name();
		assertEquals(expected, actual);
	}
}
