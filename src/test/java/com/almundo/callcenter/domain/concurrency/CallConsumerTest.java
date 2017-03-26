package com.almundo.callcenter.domain.concurrency;

import static com.almundo.callcenter.domain.model.Call.MAX_DURATION;
import static com.almundo.callcenter.domain.model.Call.MIN_DURATION;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.almundo.callcenter.domain.TestingUtils;
import com.almundo.callcenter.domain.model.Dispatcher;

public class CallConsumerTest {
	
	private CallConsumer consumer;
	private Dispatcher dispatcher;
	private TestingUtils utils;
	
	@Before
	public void setup(){
		this.consumer = new CallConsumer(new LinkedBlockingQueue<CallThread>());
		this.utils = new TestingUtils();
	}

	@After
	public void validate() {
	    validateMockitoUsage();
	}
	
	@Test
	public void whenProducerFinishesProducing_ConsumerConsumesTenCalls() throws InterruptedException{
		mockDispatcher();
		utils.createCallsByNumberOf(10, consumer.getCalls());
		for (CallThread call : consumer.getCalls()) {
			assertTrue(call.getDuration() >= MIN_DURATION);
			assertTrue(call.getDuration() < MAX_DURATION);
		}
		doAnswer(new Answer<Void>() {
	        @Override
	        public Void answer(InvocationOnMock invocation) throws Throwable {
	        	consumer.getCalls().clear();
	            return null;
	        }
	    }).when(dispatcher).dispatchCall(consumer.getCalls());
		consumer.start();
		Thread.sleep(500);
		verify(dispatcher, times(1)).dispatchCall(consumer.getCalls());
		consumer.stop();
		assertTrue(consumer.getCalls().isEmpty());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void whenProducerHasNoCallsProduced_ConsumerWaits() throws InterruptedException{
		mockDispatcher();
		Queue<CallThread> calls = mock(Queue.class);
		consumer.setCalls(calls);
		consumer.start();
		Thread.sleep(500);
		synchronized(calls){
			calls.notify();
			verify(dispatcher, Mockito.never()).dispatchCall(consumer.getCalls());
		}
		consumer.stop();
	}
	
	private void mockDispatcher() {
		this.dispatcher = mock(Dispatcher.class);
		this.consumer.setDispatcher(dispatcher);
	}

}
