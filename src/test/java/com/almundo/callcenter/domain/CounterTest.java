package com.almundo.callcenter.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.almundo.callcenter.domain.model.Counter;

public class CounterTest {
	
	private Counter counter;
	
	@Before
	public void setup(){
		this.counter = new Counter();
	}
	
	@Test
	public void whenIncrementMethodIsInvoked_CounterIsIncrementedByOne(){
		long before = 0;
		counter.increment();
		long after = 1;
		assertNotEquals(before, counter.getCounter());
		assertEquals(after, counter.getCounter());
		assertEquals(before + 1, counter.getCounter());
	}
	
}
