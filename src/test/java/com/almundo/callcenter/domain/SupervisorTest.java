package com.almundo.callcenter.domain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Call;
import com.almundo.callcenter.domain.model.Supervisor;

public class SupervisorTest {
	
	private Supervisor supervisor;
	
	@Before
	public void setup(){
		this.supervisor = new Supervisor("su1");
	}
	
	@Test
	public void whenHandleMethodIsInvokedByThread_CallMustHaveDateResolutionAndObservations() throws InterruptedException{
		Long callId = 1L;
		String observations = "Call " + callId + " finalizada por SUPERVISOR " + supervisor.getName();
		Call call = new Call(callId, 5000L);
		this.supervisor.handle(call);
		assertNotNull(call.getResolutionDate());
		assertEquals(observations, call.getObservations());
		assertTrue(supervisor.isAvailable());
	}
	
	@Test
	public void whenEmployeeTypeIsCalled_SUPERVISORisReturned(){
		String expected = "SUPERVISOR";
		String actual = this.supervisor.getType().name();
		assertEquals(expected, actual);
	}

}
