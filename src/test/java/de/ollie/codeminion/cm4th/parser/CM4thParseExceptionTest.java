/*
 * CM4thParseExceptionTest.java
 *
 * 08.01.2013
 *
 * (c) by ollie
 *
 */

package de.ollie.codeminion.cm4th.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class "CM4thParseException".
 *
 * @author ollie
 *
 */

public class CM4thParseExceptionTest {

	private static final String MESSAGE = "message";

	private CM4thParseException unitUnderTest = null;

	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new CM4thParseException(MESSAGE);
	}

	@Test
	public void testConstructorSetsTheMessageCorrectly() throws Exception {
		assertEquals(MESSAGE, this.unitUnderTest.getMessage());
	}

}