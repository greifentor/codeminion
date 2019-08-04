/*
 * CM4thVirtualMachineEventManager.java
 *
 * 09.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static de.ollie.utils.Check.ensure;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * This class manages the listener and delivers fired events to all managed listeners.
 *
 * @author ollie
 */

public class CM4thVirtualMachineEventManager {

	static Logger log = Logger.getLogger(CM4thVirtualMachineEventManager.class);

	private List<CM4thVirtualMachineListener> listeners = new Vector<CM4thVirtualMachineListener>();

	/**
	 * Creates a new event manager with an empty list of listeners.
	 */
	public CM4thVirtualMachineEventManager() {
		super();
	}

	/**
	 * Adds the passed listener to the list of the listeners managed by the event manager, if it is not already added.
	 *
	 * @param listener The listener which is to add to the list of the managed listeners.
	 * @throws IllegalArgumentException In case of passing a null pointer.
	 */
	public void addCM4thVirtualMachineListener(CM4thVirtualMachineListener listener) throws IllegalArgumentException {
		ensure(listener != null, "listener cannot be null.");
		if (!listeners.contains(listener)) {
			this.listeners.add(listener);
		}
	}

	/**
	 * Notifies all managed listeners about a output to the standard I/O.
	 *
	 * @param s The string which is printed to the standard I/O.
	 * @throws IllegalArgumentException Passing a null pointer as output string.
	 */
	public void fireStandardIOOutput(String s) {
		for (CM4thVirtualMachineListener listener : this.listeners) {
			try {
				listener.printed(s);
			} catch (Exception e) {
				log.error("while executing CM4thVirtualMachineListener.", e);
			}
		}
	}

	/**
	 * Removes the passed listener from the list of the listeners managed by the event manager, if it is existing in the
	 * list of managed listeners.
	 *
	 * @param listener The listener which is to remove from the list of the managed listeners.
	 * @throws IllegalArgumentException In case of passing a null pointer.
	 */
	public void removeCM4thVirtualMachineListener(CM4thVirtualMachineListener listener)
			throws IllegalArgumentException {
		ensure(listener != null, "listener cannot be null.");
		if (listeners.contains(listener)) {
			this.listeners.remove(listener);
		}
	}

}