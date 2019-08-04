/*
 * CM4thStackListener.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

/**
 * A listener which can be implemented to listen to the operations of a CM4thStack.
 *
 * @author ollie
 */

public interface CM4thStackListener {

	/**
	 * This method will be called if a state changing operations is called for the stack.
	 *
	 * @param event A CM4thStackEvent with the data of the event.
	 * @throws Exception If an error occurs.
	 */
	void stackChanged(CM4thStackEvent event) throws Exception;

}