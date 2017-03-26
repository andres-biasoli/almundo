package com.almundo.callcenter.domain;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Call;
import com.almundo.callcenter.domain.model.Director;

public class DirectorTest {

	private Director director;
	
	@Before
	public void setup(){
		this.director = new Director("dir");
	}
	
	@Test
	public void whenHandleMethodIsInvokedByThread_CallMustHaveDateResolutionAndObservations() throws InterruptedException{
		Long callId = 1L;
		String observations = "Call " + callId + " finalizada por DIRECTOR " + director.getName();
		Call call = new Call(callId, 5000L);
		this.director.handle(call);
		assertNotNull(call.getResolutionDate());
		assertEquals(observations, call.getObservations());
		assertTrue(director.isAvailable());
	}
	
	@Test
	public void whenEmployeeTypeIsCalled_DIRECTORisReturned(){
		String expected = "DIRECTOR";
		String actual = this.director.getType().name();
		assertEquals(expected, actual);
	}
	
}
