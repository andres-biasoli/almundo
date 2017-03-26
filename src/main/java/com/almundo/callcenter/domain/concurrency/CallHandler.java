package com.almundo.callcenter.domain.concurrency;

import com.almundo.callcenter.domain.model.Call;
import com.almundo.callcenter.domain.model.Employee;

/**
 * @author Andres Biasoli
 * An interface which is used by a {@link CallThread} to resolve a call. A CallHandler can be any {@link Employee}
 */
public interface CallHandler {
	
	/**
	 * @param call The call which will be handled.
	 * @throws InterruptedException
	 */
	void handle(Call call) throws InterruptedException;
	/**
	 * @return the name of the handler.
	 */
	String getName();
	/**
	 * @return True if the handler is available
	 */
	boolean isAvailable();
	/**
	 * @param isAvailable Sets the handler to busy if it´s at a call or available if the handler hangs out.
	 */
	void setAvailable(boolean isAvailable);
}
