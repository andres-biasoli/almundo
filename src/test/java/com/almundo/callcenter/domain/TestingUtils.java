package com.almundo.callcenter.domain;

import static com.almundo.callcenter.domain.model.Call.MAX_DURATION;
import static com.almundo.callcenter.domain.model.Call.MIN_DURATION;

import java.util.Queue;
import java.util.Random;

import com.almundo.callcenter.domain.concurrency.CallHandlerManager;
import com.almundo.callcenter.domain.concurrency.CallThread;
import com.almundo.callcenter.domain.model.Director;
import com.almundo.callcenter.domain.model.Operator;
import com.almundo.callcenter.domain.model.Supervisor;

public class TestingUtils {

	private Random random = new Random();
	
	private Long getRandomDuration() {
		return new Long((long)(random.nextDouble()*(MAX_DURATION - MIN_DURATION) + MIN_DURATION));
	}
	
	public void createCallsByNumberOf(int quantity, Queue<CallThread> calls){
		for (Long i = 1L; i <= quantity; i++) {
			calls.add(new CallThread(i, getRandomDuration()));
		}
	}

	public void getFourOperatorsTwoSupervisorAndDirector(CallHandlerManager callHandlerManager) {
		callHandlerManager.getHandlers().clear();
		callHandlerManager.getHandlers().add(new Operator("op0"));
		callHandlerManager.getHandlers().add(new Operator("op1"));
		callHandlerManager.getHandlers().add(new Operator("op2"));
		callHandlerManager.getHandlers().add(new Operator("op3"));
		callHandlerManager.getHandlers().add(new Supervisor("su0"));
		callHandlerManager.getHandlers().add(new Supervisor("su1"));
		callHandlerManager.getHandlers().add(new Director("dir"));
	}

	public void getSixOperatorsThreeSupervisorAndDirector(CallHandlerManager callHandlerManager) {
		callHandlerManager.getHandlers().clear();
		callHandlerManager.getHandlers().add(new Operator("op0"));
		callHandlerManager.getHandlers().add(new Operator("op1"));
		callHandlerManager.getHandlers().add(new Operator("op2"));
		callHandlerManager.getHandlers().add(new Operator("op3"));
		callHandlerManager.getHandlers().add(new Operator("op4"));
		callHandlerManager.getHandlers().add(new Operator("op5"));
		callHandlerManager.getHandlers().add(new Supervisor("su0"));
		callHandlerManager.getHandlers().add(new Supervisor("su1"));
		callHandlerManager.getHandlers().add(new Supervisor("su2"));
		callHandlerManager.getHandlers().add(new Director("dir"));
	}

}
