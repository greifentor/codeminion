/*
 * CM4thStackEventManager.java
 *
 * 07.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import static de.ollie.utils.Check.ensure;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * This class manages the listeners and events for a CM4thStack.
 *
 * @author ollie
 */

public class CM4thStackEventManager {

	static Logger log = Logger.getLogger(CM4thStackEventManager.class);

	private List<CM4thStackListener> listeners = new Vector<CM4thStackListener>();

	/**
	 * Creates a new event manager.
	 */
	public CM4thStackEventManager() {
		super();
	}

	/**
	 * Adds the passed listener to the list of listeners managed by the event managers.
	 *
	 * @param listener The listener which is to be managed.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void addCM4thStackListener(CM4thStackListener listener) throws IllegalArgumentException {
		ensure(listener != null, "listener cannot be null.");
		if (!this.listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	/**
	 * Fires a pop event to the registered listeners.
	 *
	 * @param value The value which is popped from the stack.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void firePopEvent(Object value) throws IllegalArgumentException {
		ensure(value != null, "value cannot be null.");
		for (CM4thStackListener listener : this.listeners) {
			try {
				listener.stackChanged(new CM4thStackEvent(CM4thStackEventType.POP, value));
			} catch (Exception e) {
				log.error("while executing CM4thStackListener.", e);
			}
		}
	}

	/**
	 * Fires a push event to the registered listeners.
	 *
	 * @param value The value which is pushed to the stack.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void firePushEvent(Object value) throws IllegalArgumentException {
		ensure(value != null, "value cannot be null.");
		for (CM4thStackListener listener : this.listeners) {
			try {
				listener.stackChanged(new CM4thStackEvent(CM4thStackEventType.PUSH, value));
			} catch (Exception e) {
				log.error("while executing CM4thListener.", e);
			}
		}
	}

	/**
	 * Removes the passed listener from the list of listeners managed by the event managers.
	 *
	 * @param listener The listener which is to remove from the list of listeners.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void removeCM4thStackListener(CM4thStackListener listener) throws IllegalArgumentException {
		ensure(listener != null, "listener cannot be null.");
		if (this.listeners.contains(listener)) {
			this.listeners.remove(listener);
		}
	}

}