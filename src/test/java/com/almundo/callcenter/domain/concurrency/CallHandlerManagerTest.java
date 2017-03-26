package com.almundo.callcenter.domain.concurrency;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.concurrency.CallHandler;
import com.almundo.callcenter.domain.concurrency.CallHandlerManager;
import com.almundo.callcenter.domain.model.Operator;

public class CallHandlerManagerTest {

	private CallHandlerManager callHandlerManager;
	
	@Before
	public void setup(){
		this.callHandlerManager = CallHandlerManager.getInstance();
		this.callHandlerManager.setHandlers(new ArrayList<CallHandler>());
	}
	
	@Test
	public void whenGetAvailableHandlerIsInvoked_ACallHandlerIsReturned(){
		populateWithOperatorHandler();
		CallHandler callHandler = callHandlerManager.getAvailableHandler();
		assertNotNull(callHandler);
		assertFalse(callHandler.isAvailable());
	}
	
	@Test
	public void whenGetAvailableHandlerIsInvoked_NullIsReturned(){
		populateWithUnavailableOperatorHandler();
		CallHandler callHandler = callHandlerManager.getAvailableHandler();
		assertNull(callHandler);
	}

	private void populateWithOperatorHandler() {
		this.callHandlerManager.getHandlers().add(new Operator("op1"));
	}
	
	private void populateWithUnavailableOperatorHandler() {
		CallHandler operator = new Operator("op1");
		operator.setAvailable(false);
		this.callHandlerManager.getHandlers().add(operator);
	}
}
