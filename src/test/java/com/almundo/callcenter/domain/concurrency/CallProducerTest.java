package com.almundo.callcenter.domain.concurrency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.LinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Counter;

public class CallProducerTest {
	
	private CallProducer producer;
	
	@Before
	public void setup(){
		this.producer = new CallProducer(new LinkedBlockingQueue<CallThread>(), new Counter());
	}
	
	@Test
	public void whenProducerIsCalled_TenCallsAreProducedToBeConsumed() throws InterruptedException{
		producer.start();
		Thread.sleep(500);
		assertFalse(producer.getCalls().isEmpty());
		assertEquals(10, producer.getCalls().size());
		assertEquals(producer.getCounter().getCounter(), producer.getCalls().size());
		producer.stop();
	}

}
