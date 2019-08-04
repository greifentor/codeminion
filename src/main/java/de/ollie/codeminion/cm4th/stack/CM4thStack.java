/*
 * CM4thStack.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.stack;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * A stack which is able to store objects and to notify push and pop operations via a listener.
 *
 * @author ollie
 */

public class CM4thStack {

	private CM4thStackEventManager eventManager = new CM4thStackEventManager();
	private Stack<Object> stack = new Stack<Object>();

	/**
	 * Creates a new fantastic 4th stack.
	 */
	public CM4thStack() {
		super();
	}

	/**
	 * Adds the passed listener to the list of listeners which are to notify if a stack event occurs.
	 *
	 * @param listener The listener which is to notify.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void addCM4thStackListener(CM4thStackListener listener) throws IllegalArgumentException {
		this.eventManager.addCM4thStackListener(listener);
	}

	/**
	 * Returns the topmost object of the stack and pops it from there.
	 *
	 * @return The topmost object of the stack.
	 * @throws EmptyStackException If there is no object on the stack.
	 */
	public Object pop() throws EmptyStackException {
		Object value = this.stack.pop();
		this.eventManager.firePopEvent(value);
		return value;
	}

	/**
	 * Pushes the passed object to the stack.
	 *
	 * @param value The value to push on the top of the stack.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void push(Object value) throws IllegalArgumentException {
		this.stack.push(value);
		this.eventManager.firePushEvent(value);
	}

	/**
	 * Returns number of objects stored on the stack.
	 *
	 * @return The number of objects stored on the stack.
	 */
	public int size() throws EmptyStackException {
		return this.stack.size();
	}

	/**
	 * Returns the topmost object of the stack.
	 *
	 * @return The topmost object of the stack.
	 * @throws EmptyStackException If there is no object on the stack.
	 */
	public Object top() throws EmptyStackException {
		return this.stack.peek();
	}

	/**
	 * Removes the passed listener from the list of listeners which are to notify of the events of this stack.
	 *
	 * @param listener The listener which is to remove from the list of listeners.
	 * @throws IllegalArgumentException Passing a null pointer.
	 */
	public void removeCM4thStackListener(CM4thStackListener listener) throws IllegalArgumentException {
		this.eventManager.removeCM4thStackListener(listener);
	}

	@Override
	public String toString() {
		return this.stack.toString();
	}

}