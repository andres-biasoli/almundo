package com.almundo.callcenter.domain.concurrency;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Call;

public class CallThreadTest {
	
	private CallThread callThread;
	private CallHandler callHandler;
	
	@Before
	public void setup(){
		this.callThread = new CallThread(1L, 5000L);
		this.callHandler = mock(CallHandler.class);
	}
	
	@Test
	public void whenStartMethodIsInvoked_TheHandleMethodExecutesAndCallIsResolved(){
		try {
			Call call = new Call(1L, 5000L);
			callThread.setCallHandler(callHandler);
			doNothing().when(callHandler).handle(call);
			callThread.start();
			Thread.sleep(1000);
			verify(callHandler, times(1)).handle(call);
		} catch (InterruptedException e) {
			fail();
		}
	}

}
