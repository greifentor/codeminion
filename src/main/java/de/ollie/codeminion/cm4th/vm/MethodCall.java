/*
 * MethodCall.java
 *
 * 10.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.vm;

import static de.ollie.utils.Check.ensure;

/**
 * This class contains a symbolic method call while time of parsing.
 *
 * @author ollie
 */

public class MethodCall {

	private String name = null;

	/**
	 * Creates a new method call with the passed name.
	 *
	 * @param name The name of the method which is called.
	 * @throws IllegalArgumentExceptin Passing a null or an empty method name.
	 */
	public MethodCall(String name) throws IllegalArgumentException {
		super();
		ensure(name != null, "name cannot be null.");
		ensure(!name.isEmpty(), "name cannot be empty.");
		this.name = name;
	}

	/**
	 * Returns the name of the called method.
	 *
	 * @return The name of the called method.
	 */
	public String getMethodName() {
		return this.name;
	}

	@Override
	public String toString() {
		return "word=" + this.getMethodName();
	}

}