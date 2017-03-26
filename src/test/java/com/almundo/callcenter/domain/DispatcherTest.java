package com.almundo.callcenter.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.almundo.callcenter.domain.concurrency.CallHandlerManager;
import com.almundo.callcenter.domain.concurrency.CallThread;
import com.almundo.callcenter.domain.concurrency.CallWorker;
import com.almundo.callcenter.domain.model.Dispatcher;

public class DispatcherTest {
	
	private Dispatcher dispatcher;
	private TestingUtils utils;
	
	@Before
	public void setup(){
		this.dispatcher = new Dispatcher();
		this.utils = new TestingUtils();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenDispatchCallIsInvoked_WithNoCalls_NothingHappens() throws InterruptedException{
		CallHandlerManager handlerManager = mock(CallHandlerManager.class);
		Map<Long, String> messages = mock(Map.class);
		Set<CallThread> callThreads = mock(Set.class);
		dispatcher.setCallHandlerManager(handlerManager);
		dispatcher.setMessages(messages);
		
		dispatcher.dispatchCall(new LinkedBlockingQueue<CallThread>());
		
		verify(handlerManager, never()).getAvailableHandler();
		verify(messages, never()).containsKey(Mockito.anyLong());
		verify(callThreads, never()).add(Mockito.any());
	}
	
	@Test
	public void whenDispatchCallsIsInvoked_WithTenCallsAndTenOperators_AllCallsAreDispatched() throws InterruptedException{
		Queue<CallThread> calls = new LinkedBlockingQueue<CallThread>();
		utils.createCallsByNumberOf(CallWorker.MAX, calls);
		utils.getSixOperatorsThreeSupervisorAndDirector(dispatcher.getCallHandlerManager());
		
		dispatcher.dispatchCall(calls);
		
		assertTrue(calls.isEmpty());
		assertTrue(dispatcher.getCallsOnHold().isEmpty());
		assertTrue(dispatcher.getMessages().isEmpty());
		assertEquals(dispatcher.getCallsOnHold().size(), dispatcher.getMessages().size());
	}
	
	@Test
	public void whenDispatchCallsIsInvoked_WithTenCallsAndSevenOperatorsAvailable_AllCallsAreAnsweredAndAtLeastTwoCallsAreKeptOnHold()
			throws InterruptedException{
		//Scenario: we consider a scenario where 10 calls are made, and the callcenter only has 7 employees. 
		//The expected result should be 7 calls immediately answered and at least 2 calls kept on hold but finally answered
		
		Queue<CallThread> calls = new LinkedBlockingQueue<CallThread>();
		utils.createCallsByNumberOf(CallWorker.MAX, calls);
		utils.getFourOperatorsTwoSupervisorAndDirector(dispatcher.getCallHandlerManager());
		
		dispatcher.dispatchCall(calls);
		
		assertTrue(calls.isEmpty());
		assertFalse(dispatcher.getCallsOnHold().isEmpty());
		assertTrue(dispatcher.getCallsOnHold().size() > 1);
		assertFalse(dispatcher.getMessages().isEmpty());
		assertTrue(dispatcher.getMessages().size() > 1);
		assertEquals(dispatcher.getCallsOnHold().size(), dispatcher.getMessages().size());
	}
	
	
	@Test
	public void whenDispatchCallsIsInvoked_WithTwentyCallsAndTenOperatorsAvailable_AllCallsAreAnsweredAndSomeCallsAreKeptOnHold() throws InterruptedException{
		//Scenario: we consider a scenario where 20 calls are made, and the callcenter only has 10 employees which are all busy. 
		//The result should be all calls answered but with some on hold
		
		Queue<CallThread> calls = new LinkedBlockingQueue<CallThread>();
		utils.createCallsByNumberOf(20, calls);
		utils.getSixOperatorsThreeSupervisorAndDirector(dispatcher.getCallHandlerManager());
		
		dispatcher.dispatchCall(calls);
		
		assertTrue(calls.isEmpty());
		assertFalse(dispatcher.getCallsOnHold().isEmpty());
		assertTrue(dispatcher.getCallsOnHold().size() > 3);
		assertFalse(dispatcher.getMessages().isEmpty());
		assertTrue(dispatcher.getMessages().size() > 3);
		assertEquals(dispatcher.getCallsOnHold().size(), dispatcher.getMessages().size());
	}
		
}
