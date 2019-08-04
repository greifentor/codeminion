/*
 * CM4thParseException.java
 *
 * 05.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.parser;

/**
 * This exception could be thrown until parsing CM4th program sources.
 *
 * @author ollie
 */

public class CM4thParseException extends RuntimeException {

	/**
	 * Creates a new parse exception with passed description.
	 *
	 * @param description The description of the problem occured while parsing.
	 */
	public CM4thParseException(String description) {
		super(description);
	}

}